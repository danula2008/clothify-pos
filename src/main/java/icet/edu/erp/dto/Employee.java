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
public class Employee {
    private Integer id;
    private Integer userId;
    private String gender;
    private String phoneNo;
    private LocalDate hireDate;
    private LocalDate dob;
    private Double salary;
}
