package org.example.cinema.repository;

import org.example.cinema.model.DatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Đánh dấu đây là một repository
//interface này để thao tác với bảng dat_ve trong database
public interface DatVeRepository extends JpaRepository<DatVe, Long> {
}
