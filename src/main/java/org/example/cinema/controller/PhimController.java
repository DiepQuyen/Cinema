package org.example.cinema.controller;

import org.example.cinema.model.Phim;
import org.example.cinema.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/phim")
public class PhimController {

    @Autowired
    private PhimService phimService;

    @GetMapping
    // Hiển thị danh sách phim
    public String danhSachPhim(Model model) {
        System.out.println(phimService.findAll());
        model.addAttribute("phims", phimService.findAll());
        return "phim/danh-sach";
    }

    // Hiển thị chi tiết phim
    @GetMapping("/{id}")
    public String chiTietPhim(@PathVariable Long id, Model model) {
        // Tìm phim theo id
        Phim phim = phimService.findById(id).orElseThrow(() -> new RuntimeException("Phim not found"));
        // Truyền phim vào giao diện
        model.addAttribute("phim", phim);
        // Trả về giao diện chi tiết phim
        return "phim/chi-tiet";
    }
}