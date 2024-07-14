package site.thanhtungle.paymentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.paymentservice.service.StripeWebhookService;

@RestController
@Slf4j
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    @Operation(summary = "Handle Stripe event")
    @PostMapping("/stripe/webhook")
    public String handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return stripeWebhookService.handleStripeEvent(payload, sigHeader);
    }
}
