package site.thanhtungle.paymentservice.service.impl;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.constant.enums.EBookingStatus;
import site.thanhtungle.paymentservice.model.dto.BookingUpdateRequestDTO;
import site.thanhtungle.paymentservice.service.StripeWebhookService;
import site.thanhtungle.paymentservice.service.rest.BookingApiClient;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeWebhookServiceImpl implements StripeWebhookService {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;
    private final BookingApiClient bookingApiClient;

    @Override
    public String handleStripeEvent(String payload, String sigHeader) {
        if (Objects.isNull(sigHeader)) return "";

        Event event;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            // Invalid signature
            log.info("⚠️  Webhook error while validating signature.");
            return "";
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                log.info("Payment for {} succeeded.", paymentIntent.getAmount());
                handlePaymentIntentSucceeded(paymentIntent);
                break;
            default:
                log.warn("Unhandled event type: {}", event.getType());
                break;
        }
        return "";
    }

    private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        BookingUpdateRequestDTO bookingUpdateRequestDTO = new BookingUpdateRequestDTO();
        bookingUpdateRequestDTO.setPaid(true);
        bookingUpdateRequestDTO.setStatus(EBookingStatus.PAID);
        bookingApiClient.updateBooking(Long.parseLong(paymentIntent.getMetadata().get("bookingId")),
                bookingUpdateRequestDTO);
    }
}
