package org.example.cinema.controller;

import org.example.cinema.model.Phim;
import org.example.cinema.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// class này để xử lý việc quản lý phim bên admin
@Controller // đánh dấu class này là 1 controller
@RequestMapping("/admin/phim") // đường dẫn URL mà controller này xử lý
// class này để xử lý việc quản lý phim bên admin
public class AdminPhimController {

    @Autowired
    private PhimService phimService;

    // hàm này để hiển thị danh sách phim
    @GetMapping
    // trả về giao diện danh sách phim
    public String danhSachPhim(Model model) {
        // lấy tất cả phim từ database
        model.addAttribute("phims", phimService.findAll());
        // trả về giao diện danh sách phim
        return "admin/phim/danh-sach";
    }

    // hàm này để hiển thị form thêm phim
    @GetMapping("/them")
    public String showThemPhimForm(Model model) {
        // truyền 1 đối tượng phim mới vào giao diện
        model.addAttribute("phim", new Phim());
        // trả về giao diện thêm phim
        return "admin/phim/them";
    }

    // hàm này để thêm phim
    @PostMapping("/them")
    public String themPhim(@ModelAttribute Phim phim) {
        // lưu phim vào database
        phimService.save(phim);
        return "redirect:/admin/phim";
    }

    // hàm này để hiển thị form sửa phim
    @GetMapping("/{id}/sua")
    public String showSuaPhimForm(@PathVariable Long id, Model model) {
        // tìm phim theo id nếu không tìm thấy trả ra lỗi
        Phim phim = phimService.findById(id).orElseThrow(() -> new RuntimeException("Phim not found"));
        // truyền phim vào giao diện
        model.addAttribute("phim", phim);
        // trả về giao diện sửa phim
        return "admin/phim/sua";
    }

    @PostMapping("/{id}/sua")
    public String suaPhim(@PathVariable Long id, @ModelAttribute Phim phim) {
        phim.setId(id);
        phimService.save(phim);
        return "redirect:/admin/phim";
    }

    @PostMapping("/{id}/xoa")
    public String xoaPhim(@PathVariable Long id) {
        phimService.deleteById(id);
        return "redirect:/admin/phim";
    }

}