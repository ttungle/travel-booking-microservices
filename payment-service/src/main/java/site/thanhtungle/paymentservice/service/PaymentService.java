package site.thanhtungle.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import site.thanhtungle.paymentservice.model.dto.PaymentRequestDTO;

public interface PaymentService {

    PaymentIntent createPaymentIntent(PaymentRequestDTO paymentRequestDTO) throws StripeException;
}
