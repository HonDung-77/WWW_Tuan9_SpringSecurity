package iuh.fit.jdbc_23639641_phamthihongdung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        // Chỉ dùng cho demo vì mật khẩu trong DB là chữ thuần; thực tế phải dùng BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/webjars/**").permitAll()

                        // --- Rule cụ thể đặt TRƯỚC ---
                        // Dữ liệu lương và thống kê: ADMIN, USER
                        .requestMatchers(HttpMethod.GET, "/employees/search/salary").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/employees/top-salary").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/employees/reports").hasAnyRole("ADMIN", "USER")

                        // Thêm, sửa: ADMIN, USER
                        .requestMatchers(HttpMethod.GET, "/employees/new").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employees/save").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/employees/edit/**").hasAnyRole("ADMIN", "USER")

                        // Xóa: chỉ ADMIN
                        .requestMatchers(HttpMethod.POST, "/employees/delete/**").hasRole("ADMIN")

                        // --- Rule chung đặt SAU ---
                        // Xem danh sách, tìm kiếm còn lại (id, name, age, position): cả 3 role
                        .requestMatchers(HttpMethod.GET, "/employees").hasAnyRole("ADMIN", "USER", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/employees/search/**").hasAnyRole("ADMIN", "USER", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/employees/{id:\\d+}").hasAnyRole("ADMIN", "USER", "GUEST")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/employees", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}