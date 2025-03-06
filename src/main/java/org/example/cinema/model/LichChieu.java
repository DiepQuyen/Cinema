package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_chieu")
@Data
public class LichChieu {
    @Id// Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Giá trị tự tăng
    private Long id;

    @ManyToOne// Nhiều lịch chiếu thuộc về một phim
    @JoinColumn(name = "phim_id")
    private Phim phim;

    @ManyToOne// Nhiều lịch chiếu thuộc về một phòng chiếu
    @JoinColumn(name = "rap_chieu_id")
    private RapChieu rapChieu;

    private LocalDateTime thoiGianChieu;
    private Double giaVe;
    @Column(nullable = false)
    private Integer soChoNgoi;

    @Column(nullable = false)
    private Integer soChoDaDat = 0;
    private String trangThai; // Đang bán, Đã bán hết, Đã chiếu

    @Version
    private Integer version;
}