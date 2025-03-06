package org.example.cinema.repository;

import jakarta.persistence.Id;
import org.example.cinema.model.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository//Đánh dấu đây là một repository
//interface này để thao tác với bảng phim trong database
public interface PhimRepository extends JpaRepository<Phim, Long> {
    // Tìm phim theo tên
    List<Phim> findAllByIsActiveTrue();

    // Tìm phim theo tên
    Optional<Phim> findByIdAndIsActiveTrue(Long id);
}