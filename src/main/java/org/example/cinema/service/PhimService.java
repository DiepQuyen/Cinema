package org.example.cinema.service;

import org.example.cinema.model.Phim;

import java.util.List;
import java.util.Optional;

//Interface này để thao tác với bảng phim trong database
public interface PhimService {
    //Tìm tất cả phim
    List<Phim> findAll();

    //Tìm phim theo id
    Optional<Phim> findById(Long id);

    //Lưu phim
    Phim save(Phim phim);

    //Xóa phim theo id
    void deleteById(Long id);
}