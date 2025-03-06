package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "dat_ve")
@Data
public class DatVe {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Giá trị tự tăng
    private Long id;

    @ManyToOne // Nhiều vé có thể thuộc về một lịch chiếu
    @JoinColumn(name = "lich_chieu_id")// Khóa ngoại
    private LichChieu lichChieu;

    @ManyToOne // Nhiều vé có thể thuộc về một người dùng
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "datVe")// Một vé có thể có nhiều ghế ngồi
    private Set<GheNgoi> gheNgois;

    // thông tin người đặt vé
    private String hoTen;
    // thông tin email người đặt vé
    private String email;
    private String soDienThoai;
    private Integer soLuongVe;
    private LocalDateTime thoiGianDat;
    private String maGiaoDich;
    private String trangThai; // Chờ thanh toán, Đã thanh toán, Đã hủy
}