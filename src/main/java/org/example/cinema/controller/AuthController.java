package org.example.cinema.controller;

import org.example.cinema.dto.DangKyDTO;
import org.example.cinema.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private NguoiDungService nguoiDungService;

    // Hiển thị trang đăng nhập
    @GetMapping("/dang-nhap")
    public String dangNhap() {
        return "auth/dang-nhap";
    }

    // Hiển thị trang đăng ký
    @GetMapping("/dang-ky")
    public String showDangKyForm(Model model) {
        model.addAttribute("dangKyDTO", new DangKyDTO());
        return "auth/dang-ky";
    }

    // Xử lý đăng ký
    @PostMapping("/dang-ky")
    public String dangKy(@Valid @ModelAttribute DangKyDTO dangKyDTO,
                         BindingResult result,
                         Model model) {
        // Kiểm tra xem dữ liệu nhập vào có hợp lệ không
        if (result.hasErrors()) {
            // Nếu không hợp lệ thì trả về giao diện đăng ký
            return "dang-ky";
        }

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (nguoiDungService.existsByUsername(dangKyDTO.getUsername())) {
            // Nếu đã tồn tại thì trả về giao diện đăng ký với thông báo lỗi
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            // Trả về giao diện đăng ký
            return "auth/dang-ky";
        }

        // Kiểm tra xem email đã tồn tại chưa
        nguoiDungService.dangKyNguoiDung(dangKyDTO);
        // Chuyển hướng về trang đăng nhập với thông báo đăng ký thành công
        return "redirect:/dang-nhap?dangky=success";
    }
}