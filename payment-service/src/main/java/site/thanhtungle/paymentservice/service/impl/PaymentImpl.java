package site.thanhtungle.paymentservice.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.paymentservice.model.dto.Booking;
import site.thanhtungle.paymentservice.model.dto.PaymentRequestDTO;
import site.thanhtungle.paymentservice.service.PaymentService;
import site.thanhtungle.paymentservice.service.rest.BookingApiClient;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentImpl implements PaymentService {

    private final BookingApiClient bookingApiClient;

    @Override
    public PaymentIntent createPaymentIntent(PaymentRequestDTO paymentRequestDTO) throws StripeException {

        ResponseEntity<BaseApiResponse<Booking>> response = bookingApiClient.getBooking(paymentRequestDTO.getBookingId());
        if (Objects.isNull(response) || Objects.isNull(response.getBody().getData())) {
            throw new CustomBadRequestException("Cannot create payment because booking cannot be founded with id: " +
                    paymentRequestDTO.getBookingId());
        }

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(calculateAmount(paymentRequestDTO.getAmount()))
                        .setCurrency("usd")
                        .putMetadata("bookingId", paymentRequestDTO.getBookingId().toString())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        return PaymentIntent.create(params);
    }

    private Long calculateAmount(BigDecimal amount) {
        if (Objects.isNull(amount)) return 0L;
        if (amount.equals(new BigDecimal(0))) return 0L;
        return amount.multiply(new BigDecimal(100)).longValue();
    }
}
