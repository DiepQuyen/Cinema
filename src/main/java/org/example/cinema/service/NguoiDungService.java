package org.example.cinema.service;

import org.example.cinema.dto.DangKyDTO;
import org.example.cinema.model.NguoiDung;

//Service này dùng để thao tác với bảng nguoi_dung trong database
public interface NguoiDungService {
    //Đăng ký người dùng
    NguoiDung dangKyNguoiDung(DangKyDTO dangKyDTO);

    //Kiểm tra username đã tồn tại chưa
    boolean existsByUsername(String username);

    //Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);

    //Tìm người dùng theo username
    NguoiDung findByUsername(String username);
}