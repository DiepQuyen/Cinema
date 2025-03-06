package org.example.cinema.controller;

import org.example.cinema.model.HoaDon;
import org.example.cinema.model.NguoiDung;
import org.example.cinema.service.LichSuMuaVeService;
import org.example.cinema.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lich-su-mua-ve")
public class LichSuMuaVeController {

    @Autowired
    private LichSuMuaVeService lichSuMuaVeService;

    @Autowired
    private NguoiDungService nguoiDungService;

    // Hiển thị lịch sử mua vé
    @GetMapping
    public String getLichSuMuaVe(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (userDetails != null) {
            // Lấy thông tin người dùng từ UserDetails
            String username = userDetails.getUsername();
            // Tìm người dùng theo username
            NguoiDung nguoiDung = nguoiDungService.findByUsername(username);
            // Lấy lịch sử mua vé của người dùng
            List<HoaDon> lichSuMuaVe = lichSuMuaVeService.getLichSuMuaVe(nguoiDung.getId());
            // Truyền danh sách hóa đơn vào giao diện
            model.addAttribute("hoaDons", lichSuMuaVe);
        }
        return "lich-su-mua-ve";
    }
}