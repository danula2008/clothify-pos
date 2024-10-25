package icet.edu.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderProduct")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductEntity {
    @Id
    private Integer orderId;
    @Id
    private Integer itemId;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
    private Double subTotal;
}
