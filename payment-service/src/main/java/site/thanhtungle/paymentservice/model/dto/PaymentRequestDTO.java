package site.thanhtungle.paymentservice.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequestDTO {

    @SerializedName("amount")
    private BigDecimal amount;

    @SerializedName("bookingId")
    private Long bookingId;
}
