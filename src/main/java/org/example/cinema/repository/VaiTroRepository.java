package org.example.cinema.repository;

import org.example.cinema.model.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository//Đánh dấu đây là một repository
//interface này để thao tác với bảng vai_tro trong database
public interface VaiTroRepository extends JpaRepository<VaiTro, Long> {
    // Tìm vai tro theo ten
    Optional<VaiTro> findByTen(String ten);
}