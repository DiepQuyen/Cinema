package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Data
public class HoaDon {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Giá trị tự tăng
    private Long id;

    @OneToOne// Một hóa đơn chỉ thuộc về một đặt vé
    @JoinColumn(name = "dat_ve_id")
    private DatVe datVe;

    private Double tongTien;
    private String phuongThucThanhToan;
    private String trangThai;
    private String maGiaoDich;
    private LocalDateTime thoiGianThanhToan;
}