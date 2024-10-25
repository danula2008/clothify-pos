package icet.edu.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Integer id;
    private Integer productId;
    private Integer supplierId;
    private Double sellingPrice;
    private Double inventoryPrice;
    private Integer qtyOnHand;
}
