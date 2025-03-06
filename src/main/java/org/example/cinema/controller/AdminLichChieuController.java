package org.example.cinema.controller;

import jakarta.validation.Valid;
import org.example.cinema.model.LichChieu;
import org.example.cinema.repository.LichChieuRepository;
import org.example.cinema.repository.RapChieuRepository;
import org.example.cinema.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/lich-chieu")
@PreAuthorize("hasRole('ADMIN')") //Chỉ có quyền admin mới gọi đến đây được
// class này để xử lý việc quản lý lịch chiếu bên admin
public class AdminLichChieuController {
    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private PhimService phimService;

    @Autowired
    private RapChieuRepository rapChieuRepository;

    // hàm này để hiển thị danh sách lịch chiếu
    @GetMapping
    public String danhSach(Model model) {
        // lấy tất cả lịch chiếu từ database
        model.addAttribute("lichChieus", lichChieuRepository.findAll());
        // trả về giao diện danh sách lịch chiếu
        return "admin/lich-chieu/danh-sach";
    }

    // hàm này để hiển thị form thêm lịch chiếu
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        // truyền 1 đối tượng lịch chiếu mới vào giao diện
        model.addAttribute("lichChieu", new LichChieu());
        // lấy tất cả phim từ database
        model.addAttribute("phims", phimService.findAll());
        // lấy tất cả rạp chiếu từ database
        model.addAttribute("rapChieus", rapChieuRepository.findAll());
        // trả về giao diện thêm lịch chiếu
        return "admin/lich-chieu/them";
    }

    // hàm này để thêm lịch chiếu
    @PostMapping("/them")
    public String them(@Valid @ModelAttribute LichChieu lichChieu,
                       BindingResult result,
                       RedirectAttributes redirect) {
        // kiểm tra xem dữ liệu nhập vào có hợp lệ không
        if (result.hasErrors()) {
            // nếu không hợp lệ thì trả về giao diện thêm lịch chiếu
            return "admin/lich-chieu/them";
        }

        // kiểm tra xem thời gian chiếu có bị trùng không
        if (isTrungLichChieu(lichChieu)) {
            // nếu bị trùng thì trả về giao diện thêm lịch chiếu và thông báo lỗi
            redirect.addFlashAttribute("error", "Thời gian chiếu đã bị trùng");
            // trả về giao diện thêm lịch chiếu
            return "redirect:/admin/lich-chieu/them";
        }

        // lưu lịch chiếu vào database
        lichChieuRepository.save(lichChieu);
        // thông báo thêm lịch chiếu thành công
        redirect.addFlashAttribute("message", "Thêm lịch chiếu thành công");
        // chuyển hướng về trang quản lý lịch chiếu
        return "redirect:/admin/lich-chieu";
    }

    // hàm này để hiển thị form sửa lịch chiếu
    @GetMapping("/{id}/sua")
    public String hienThiFormSua(@PathVariable Long id, Model model) {
        // tìm lịch chiếu theo id nếu không tìm thấy trả ra lỗi
        LichChieu lichChieu = lichChieuRepository.findById(id)
                // nếu không tìm thấy thì trả ra lỗi
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu"));

        // truyền lịch chiếu vào giao diện
        model.addAttribute("lichChieu", lichChieu);
        // lấy tất cả phim từ database
        model.addAttribute("phims", phimService.findAll());
        // lấy tất cả rạp chiếu từ database
        model.addAttribute("rapChieus", rapChieuRepository.findAll());
        // trả về giao diện sửa lịch chiếu
        return "admin/lich-chieu/sua";
    }

    @PostMapping("/{id}/sua")
    public String sua(@PathVariable Long id,
                      @Valid @ModelAttribute LichChieu lichChieu,
                      BindingResult result,
                      RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "admin/lich-chieu/sua";
        }

        if (isTrungLichChieu(lichChieu)) {
            redirect.addFlashAttribute("error", "Thời gian chiếu đã bị trùng");
            return "redirect:/admin/lich-chieu/" + id + "/sua";
        }

        lichChieuRepository.save(lichChieu);
        redirect.addFlashAttribute("message", "Cập nhật lịch chiếu thành công");
        return "redirect:/admin/lich-chieu";
    }

    @GetMapping("/phim/{phimId}")
    public String xemLichChieuTheoPhim(@PathVariable Long phimId, Model model) {
        model.addAttribute("phim", phimService.findById(phimId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim")));

        List<LichChieu> lichChieus = lichChieuRepository.findByPhimIdOrderByThoiGianChieuAsc(phimId);
        model.addAttribute("lichChieus", lichChieus);

        return "admin/lich-chieu/phim";
    }

    @PostMapping("/{id}/xoa")
    public String xoa(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            lichChieuRepository.deleteById(id);
            redirect.addFlashAttribute("message", "Xóa lịch chiếu thành công");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể xóa lịch chiếu này");
        }
        return "redirect:/admin/lich-chieu";
    }

    private boolean isTrungLichChieu(LichChieu lichChieu) {
        LocalDateTime start = lichChieu.getThoiGianChieu();
        LocalDateTime end = start.plusMinutes(lichChieu.getPhim().getThoiLuong());

        return lichChieuRepository.existsByRapChieuAndThoiGianChieuBetween(
                lichChieu.getRapChieu(),
                start.minusMinutes(1),
                end.plusMinutes(1)
        );
    }
}