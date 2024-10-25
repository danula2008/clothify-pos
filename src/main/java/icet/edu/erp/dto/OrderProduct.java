package icet.edu.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    private Integer orderId;
    private Integer itemId;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
    private Double subTotal;
}
