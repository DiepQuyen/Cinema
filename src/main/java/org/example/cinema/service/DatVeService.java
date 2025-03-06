package org.example.cinema.service;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.example.cinema.dto.DatVeDTO;
import org.example.cinema.model.DatVe;
import org.example.cinema.model.HoaDon;
import org.example.cinema.model.LichChieu;
import org.example.cinema.model.NguoiDung;
import org.example.cinema.repository.DatVeRepository;
import org.example.cinema.repository.HoaDonRepository;
import org.example.cinema.repository.LichChieuRepository;
import org.example.cinema.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // đánh dấu class này là 1 service
@Transactional // đảm bảo rằng tất cả các phương thức trong class này đều được thực thi trong 1 transaction
public class DatVeService { // class này để thực hiện việc đặt vé
    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    // hàm này để đặt vé
    @Lock(LockModeType.PESSIMISTIC_WRITE) // đảm bảo chỉ có 1 luồng được phép thực thi hàm này tại 1 thời điểm
    public DatVe datVe(DatVeDTO datVeDTO) {
        // tìm lịch chiếu theo id nếu không thấy trả ra lỗi
        LichChieu lichChieu = lichChieuRepository.findById(datVeDTO.getLichChieuId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu"));

        // Tìm người dùng theo id nếu không tìm thấy trả ra lỗi
        NguoiDung nguoiDung = nguoiDungRepository.findById(datVeDTO.getNguoiDungId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // kiểm tra xem số chỗ đã đặt cộng với số lượng vé đặt có lớn hơn số chỗ ngồi không
        if (lichChieu.getSoChoDaDat() + datVeDTO.getSoLuongVe() > lichChieu.getSoChoNgoi()) {
            throw new RuntimeException("Không đủ chỗ ngồi");// nếu lớn hơn thì trả ra lỗi
        }

        // lưu thông tin đặt vé nếu đủ chỗ ngồi
        DatVe datVe = new DatVe();
        datVe.setLichChieu(lichChieu);
        datVe.setHoTen(datVeDTO.getHoTen());
        datVe.setEmail(datVeDTO.getEmail());
        datVe.setSoDienThoai(datVeDTO.getSoDienThoai());
        datVe.setSoLuongVe(datVeDTO.getSoLuongVe());
        datVe.setThoiGianDat(LocalDateTime.now());
        datVe.setMaGiaoDich(generateMaGiaoDich());
        datVe.setNguoiDung(nguoiDung);
        //lưu vào db
        datVeRepository.save(datVe);

        // cập nhật số chỗ đã đặt vào lịch chiếu
        lichChieu.setSoChoDaDat(lichChieu.getSoChoDaDat() + datVeDTO.getSoLuongVe());
        lichChieuRepository.save(lichChieu);

        // tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setDatVe(datVe);
        hoaDon.setTongTien(lichChieu.getGiaVe() * datVeDTO.getSoLuongVe());
        hoaDon.setTrangThai("Đã thanh toán");
        hoaDon.setMaGiaoDich(generateMaGiaoDich());
        hoaDon.setPhuongThucThanhToan("Thanh toán qua thẻ");
        hoaDon.setThoiGianThanhToan(LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        return datVe;
    }

    // hàm này để tạo mã giao dịch
    private String generateMaGiaoDich() {
        return "VE" + System.currentTimeMillis();
    }
}