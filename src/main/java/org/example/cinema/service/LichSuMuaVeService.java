package org.example.cinema.service;

import org.example.cinema.model.HoaDon;
import org.example.cinema.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuMuaVeService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    // Lấy lịch sử mua vé của người dùng
    public List<HoaDon> getLichSuMuaVe(Long nguoiDungId) {
        // Tìm hóa đơn theo người dùng id và sắp xếp theo thời gian đặt vé giảm dần
        return hoaDonRepository.findByDatVe_NguoiDungIdOrderByDatVe_ThoiGianDatDesc(nguoiDungId);
    }
}