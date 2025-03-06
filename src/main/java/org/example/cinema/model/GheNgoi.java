package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ghe_ngoi")
@Data
public class GheNgoi {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Giá trị tự tăng
    private Long id;

    @ManyToOne // Nhiều ghế ngồi thuộc một phòng chiếu
    @JoinColumn(name = "rap_chieu_id")
    private RapChieu rapChieu;

    @ManyToOne // Nhiều ghế ngồi thuộc một đặt vé
    @JoinColumn(name = "dat_ve_id")
    private DatVe datVe;  // Thêm field này

    private String maGhe;
    private String loaiGhe;
    private Double giaGhe;
    private Boolean daDat;

    @ManyToOne
    @JoinColumn(name = "lich_chieu_id")
    private LichChieu lichChieu;
}