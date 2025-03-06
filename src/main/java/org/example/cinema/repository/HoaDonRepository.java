package org.example.cinema.repository;

import org.example.cinema.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //Đánh dấu đây là một repository
//interface này để thao tác với bảng hoa_don trong database
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    List<HoaDon> findByDatVe_NguoiDungIdOrderByDatVe_ThoiGianDatDesc(Long nguoiDungId);
}