package site.thanhtungle.paymentservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.paymentservice.service.StripeWebhookService;

@RestController
@Slf4j
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    @PostMapping("/stripe/webhook")
    public String handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return stripeWebhookService.handleStripeEvent(payload, sigHeader);
    }
}
