package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDung {
    @Id// Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Giá trị tự tăng
    private Long id;

    // unique = true: không cho phép trùng dữ liệu
    @Column(unique = true)
    private String username;

    private String password;
    private String hoTen;
    private String email;

    // FetchType.EAGER: khi lấy dữ liệu người dùng, lấy cả vai trò
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( // Tạo bảng trung gian nguoi_dung_vai_tro
            name = "nguoi_dung_vai_tro", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "nguoi_dung_id"), // Khóa ngoại của bảng nguoi_dung
            inverseJoinColumns = @JoinColumn(name = "vai_tro_id") // Khóa ngoại của bảng vai_tro
    )
    private Set<VaiTro> vaiTro;
}