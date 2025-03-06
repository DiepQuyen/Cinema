package org.example.cinema.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
//DTO để truyền dữ liệu từ client lên server
public class DangKyDTO {
    //Validate không được để trống
    @NotBlank(message = "Tên đăng nhập không được để trống")
    //Validate độ dài tên đăng nhập từ 3-50 ký tự
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    private String username;

    //Validate không được để trống
    @NotBlank(message = "Mật khẩu không được để trống")
    //Validate độ dài mật khẩu ít nhất 6 ký tự
    @Size(min = 6, message = "Mật khẩu phải ít nhất 6 ký tự")
    private String password;

    //Validate không được để trống
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    //Validate không được để trống
    @NotBlank(message = "Email không được để trống")
    //Validate email
    @Email(message = "Email không hợp lệ")
    private String email;
}