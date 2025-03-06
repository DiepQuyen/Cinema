package org.example.cinema.controller;

import jakarta.validation.Valid;
import org.example.cinema.model.RapChieu;
import org.example.cinema.repository.RapChieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/rap-chieu")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRapChieuController {
    @Autowired
    private RapChieuRepository rapChieuRepository;

    // Hiển thị danh sách rạp
    @GetMapping
    public String danhSachRapChieu(Model model) {
        // Lấy tất cả rạp chiếu từ database
        model.addAttribute("rapChieus", rapChieuRepository.findAll());
        return "admin/rap-chieu/danh-sach";
    }

    // Hiển thị form thêm rạp mới
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        // Truyền 1 đối tượng rạp mới vào giao diện
        model.addAttribute("rapChieu", new RapChieu());
        return "admin/rap-chieu/them";
    }

    // Xử lý thêm rạp mới
    @PostMapping("/them")
    public String themRapChieu(@Valid @ModelAttribute RapChieu rapChieu,
                               BindingResult result,
                               RedirectAttributes redirect) {
        // Kiểm tra xem dữ liệu nhập vào có hợp lệ không
        if (result.hasErrors()) {
            // Nếu không hợp lệ thì trả về giao diện thêm rạp
            return "admin/rap-chieu/them";
        }

        try {
            // Lưu rạp chiếu vào database
            rapChieuRepository.save(rapChieu);
            // Thông báo thêm rạp thành công
            redirect.addFlashAttribute("message", "Thêm rạp chiếu thành công");
        } catch (Exception e) {
            // Thông báo lỗi nếu có lỗi
            redirect.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        // Chuyển hướng về trang danh sách rạp
        return "redirect:/admin/rap-chieu";
    }

    // Hiển thị form sửa rạp
    @GetMapping("/{id}/sua")
    public String hienThiFormSua(@PathVariable Long id, Model model) {
        // Tìm rạp chiếu theo id
        RapChieu rapChieu = rapChieuRepository.findById(id)
                // Nếu không tìm thấy thì ném ra lỗi
                .orElseThrow(() -> new RuntimeException("Không tìm thấy rạp chiếu"));

        // Truyền rạp chiếu vào giao diện
        model.addAttribute("rapChieu", rapChieu);
        // Trả về giao diện sửa rạp
        return "admin/rap-chieu/sua";
    }

    // Xử lý sửa rạp
    @PostMapping("/{id}/sua")
    public String suaRapChieu(@PathVariable Long id,
                              @Valid @ModelAttribute RapChieu rapChieu,
                              BindingResult result,
                              RedirectAttributes redirect) {
        // Kiểm tra xem dữ liệu nhập vào có hợp lệ không
        if (result.hasErrors()) {
            // Nếu không hợp lệ thì trả về giao diện sửa rạp
            return "admin/rap-chieu/sua";
        }

        // Lưu rạp chiếu vào database
        rapChieuRepository.save(rapChieu);
        // Thông báo cập nhật rạp thành công
        redirect.addFlashAttribute("message", "Cập nhật rạp chiếu thành công");
        // Chuyển hướng về trang danh sách rạp
        return "redirect:/admin/rap-chieu";
    }

    // Xử lý xóa rạp
    @PostMapping("/{id}/xoa")
    public String xoaRapChieu(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            // gọi hàm xóa rạp chiếu theo id
            rapChieuRepository.deleteById(id);
            // thông báo xóa rạp thành công
            redirect.addFlashAttribute("message", "Xóa rạp chiếu thành công");
        } catch (Exception e) {
            // thông báo lỗi nếu có lỗi
            redirect.addFlashAttribute("error",
                    "Không thể xóa rạp chiếu này vì đã có lịch chiếu liên quan");
        }
        // chuyển hướng về trang danh sách rạp
        return "redirect:/admin/rap-chieu";
    }
}