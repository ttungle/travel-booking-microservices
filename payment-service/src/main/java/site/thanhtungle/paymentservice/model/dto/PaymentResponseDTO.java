package site.thanhtungle.paymentservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponseDTO {

    private String id;
    private String clientSecret;
}
