package org.example.cinema.service;

import org.example.cinema.dto.DangKyDTO;
import org.example.cinema.model.NguoiDung;
import org.example.cinema.model.VaiTro;
import org.example.cinema.repository.NguoiDungRepository;
import org.example.cinema.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
//implements NguoiDungService
// Đây là một service, nơi chứa các phương thức xử lý logic
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional// đảm bảo rằng tất cả các phương thức trong class này đều được thực thi trong 1 transaction
    // hàm này để đăng ký người dùng
    public NguoiDung dangKyNguoiDung(DangKyDTO dangKyDTO) {
        // tạo một người dùng mới
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setUsername(dangKyDTO.getUsername());
        nguoiDung.setPassword(passwordEncoder.encode(dangKyDTO.getPassword()));
        nguoiDung.setHoTen(dangKyDTO.getHoTen());
        nguoiDung.setEmail(dangKyDTO.getEmail());

        // tìm vai trò user trong database
        VaiTro vaiTroUser = vaiTroRepository.findByTen("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Vai trò không tồn tại"));
        // set vai trò cho người dùng
        nguoiDung.setVaiTro(Collections.singleton(vaiTroUser));

        // lưu người dùng vào database
        return nguoiDungRepository.save(nguoiDung);
    }

    @Override // hàm này để kiểm tra xem username đã tồn tại chưa
    public boolean existsByUsername(String username) {
        return nguoiDungRepository.existsByUsername(username);
    }

    @Override // hàm này để kiểm tra xem email đã tồn tại chưa
    public boolean existsByEmail(String email) {
        return nguoiDungRepository.existsByEmail(email);
    }

    @Override // hàm này để tìm người dùng theo username
    public NguoiDung findByUsername(String username) {
        // tìm người dùng theo username nếu không tìm thấy trả ra lỗi
        return nguoiDungRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
    }
}