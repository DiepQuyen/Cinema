package org.example.cinema.controller;

import org.example.cinema.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private PhimService phimService;

    // Hiển thị trang chủ
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("phimMoiNhat", phimService.findAll());
        return "index";
    }
}