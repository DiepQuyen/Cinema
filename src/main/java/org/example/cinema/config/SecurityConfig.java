package org.example.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Đánh dấu đây là một class cấu hình
@EnableMethodSecurity //Bật tính năng hỗ trợ @PreAuthorize
public class SecurityConfig {
    //Cấu hình bảo mật
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //cấu hình cho các request
                        .requestMatchers("/", "/phim/**", "/lich-chieu/**", "/dang-ky", "/dang-nhap",
                                "/css/**", "/js/**", "/images/**").permitAll() //các request này không cần xác thực
                        .requestMatchers("/admin/**").hasRole("ADMIN") //các request bắt đầu bằng /admin/ cần có vai trò ADMIN
                        .requestMatchers("/dat-ve/**").authenticated() //các request bắt đầu bằng /dat-ve/ cần xác thực
                        .requestMatchers("/thanh-toan/**").authenticated() //các request bắt đầu bằng /thanh-toan/ cần xác thực
                        .requestMatchers("/lich-su-mua-ve/**").authenticated() //các request bắt đầu bằng /lich-su-mua-ve/ cần xác thực
                        .anyRequest().authenticated()//tất cả các request khác cần xác thực
                )
                .formLogin(form -> form
                        .loginPage("/dang-nhap") //đường dẫn đến trang đăng nhập
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean //Tạo Bean BCryptPasswordEncoder
    //hàm này sẽ sử dụng để mã hóa mật khẩu
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}