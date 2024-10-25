package icet.edu.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phoneNo;
    private LocalDate dob;
    private LocalDate joinedDate;
    private String loyaltyTier;
}
