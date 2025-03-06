package org.example.cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "rap_chieu")
@Data
public class RapChieu {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Giá trị tự tăng
    private Long id;

    // Validation không cho phép rỗng
    @NotBlank(message = "Tên rạp không được để trống")
    private String tenRap;

    // Validation không cho phép rỗng
    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    // Validation số ghế phải lớn hơn 0
    @Min(value = 1, message = "Số ghế phải lớn hơn 0")
    private Integer soGhe;

    // Validation không cho phép rỗng
    @NotBlank(message = "Loại rạp không được để trống")
    private String loaiRap; // 2D, 3D, IMAX
}