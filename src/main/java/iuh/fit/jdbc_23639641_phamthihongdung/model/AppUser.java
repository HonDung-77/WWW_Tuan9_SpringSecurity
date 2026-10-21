package iuh.fit.jdbc_23639641_phamthihongdung.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class AppUser {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String role;
}