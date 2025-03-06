package org.example.cinema.repository;

import org.example.cinema.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //Đánh dấu đây là một repository
//interface này để thao tác với bảng nguoi_dung trong database
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    // Tìm người dùng theo username
    Optional<NguoiDung> findByUsername(String username);

    // Tìm người dùng theo email
    boolean existsByUsername(String username);

    // Tìm người dùng theo email
    boolean existsByEmail(String email);
}