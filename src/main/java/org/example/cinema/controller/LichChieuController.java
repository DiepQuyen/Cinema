package org.example.cinema.controller;

import jakarta.validation.Valid;
import org.example.cinema.model.LichChieu;
import org.example.cinema.model.Phim;
import org.example.cinema.repository.LichChieuRepository;
import org.example.cinema.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller// đánh dấu class này là 1 controller
@RequestMapping("/lich-chieu")
// class này để xử lý việc quản lý lịch chiếu
public class LichChieuController {

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private PhimService phimService;

    // Hiển thị danh sách lịch chiếu
    @GetMapping
    public String danhSachLichChieu(Model model) {
        // Lấy tất cả lịch chiếu từ database tìm theo thời gian chiếu sau thời gian hiện tại
        List<LichChieu> lichChieus = lichChieuRepository.findByThoiGianChieuAfterOrderByThoiGianChieu(
                LocalDateTime.now());

        // Hiển thị thông báo nếu không có lịch chiếu nào
        if (lichChieus.isEmpty()) {
            model.addAttribute("message", "Hiện tại không có lịch chiếu nào");
        }
//         // Truyền danh sách lịch chiếu vào giao diện
        model.addAttribute("lichChieus", lichChieus);
        return "lich-chieu";
    }
}