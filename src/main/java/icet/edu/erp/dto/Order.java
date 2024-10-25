package icet.edu.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer CustId;
    private Integer EmpId;
    private Timestamp dateTime;
    private String paymentType;
    private Double netTotal;
    private Double totalDiscount;
    private Double totalCost;
}
