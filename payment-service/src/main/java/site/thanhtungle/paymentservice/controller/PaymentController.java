package site.thanhtungle.paymentservice.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.thanhtungle.paymentservice.model.dto.PaymentRequestDTO;
import site.thanhtungle.paymentservice.model.dto.PaymentResponseDTO;
import site.thanhtungle.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) throws StripeException {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequestDTO);
            return new PaymentResponseDTO(paymentIntent.getId(), paymentIntent.getClientSecret());
    }
}
