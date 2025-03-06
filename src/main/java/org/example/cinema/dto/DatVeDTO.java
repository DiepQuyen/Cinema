package org.example.cinema.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.cinema.model.NguoiDung;

@Data
//DTO để truyền dữ liệu từ client lên server
public class DatVeDTO {
    private Long lichChieuId;

    private Long nguoiDungId;

    //Validate không được để trống
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    //Validate không được để trống
    @NotBlank(message = "Email không được để trống")
    //Validate email
    @Email(message = "Email không hợp lệ")
    private String email;

    //Validate không được để trống
    @NotBlank(message = "Số điện thoại không được để trống")
    //Validate số điện thoại
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    private String soDienThoai;

    //Validate số lượng vé
    @Min(value = 1, message = "Số lượng vé phải ít nhất là 1")
    //Validate số lượng vé
    @Max(value = 10, message = "Số lượng vé tối đa là 10")
    private Integer soLuongVe;
}