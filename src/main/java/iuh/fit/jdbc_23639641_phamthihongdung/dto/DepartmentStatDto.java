package iuh.fit.jdbc_23639641_phamthihongdung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentStatDto {
    private String departmentName;
    private Long employeeCount;
    private Double avgSalary;
    private Double totalSalary;
}