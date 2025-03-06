package org.example.cinema.repository;

import org.example.cinema.model.LichChieu;
import org.example.cinema.model.RapChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//interface này để thao tác với bảng lich_chieu trong database
@Repository//Đánh dấu đây là một repository
public interface LichChieuRepository extends JpaRepository<LichChieu, Long> {
    List<LichChieu> findByThoiGianChieuAfterOrderByThoiGianChieu(LocalDateTime thoiGian);

    List<LichChieu> findByPhimIdOrderByThoiGianChieuAsc(Long phimId);

    boolean existsByRapChieuAndThoiGianChieuBetween(
            RapChieu rapChieu,
            LocalDateTime start,
            LocalDateTime end
    );
}
