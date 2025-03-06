package org.example.cinema.repository;

import org.example.cinema.model.RapChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//Đánh dấu đây là một repository
//interface này để thao tác với bảng rap_chieu trong database
public interface RapChieuRepository extends JpaRepository<RapChieu, Long> {
    // Tìm rap chieu theo ten
    List<RapChieu> findAllByOrderByTenRapAsc();
}
