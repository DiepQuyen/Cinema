package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "phim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phim {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Giá trị tự tăng
    private Long id;

    private String tenPhim;
    private String moTa;
    private String theLoai;
    private Integer thoiLuong;
    private String anhBia;
    private LocalDate ngayKhoiChieu;
    private Boolean isActive = true;

    @OneToMany(mappedBy = "phim", fetch = FetchType.LAZY) // Một phim có thể có nhiều lịch chiếu
    @ToString.Exclude // Không in ra lịch chiếu để tránh vòng lặp vô hạn
    @EqualsAndHashCode.Exclude // Không so sánh lịch chiếu để tránh vòng lặp vô hạn
    private Set<LichChieu> lichChieus = new HashSet<>();
}