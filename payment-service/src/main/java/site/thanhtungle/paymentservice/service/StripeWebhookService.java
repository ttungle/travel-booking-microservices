package site.thanhtungle.paymentservice.service;

public interface StripeWebhookService {

    String handleStripeEvent(String payload, String sigHeader);
}
