package org.example.cinema.controller;

import jakarta.validation.Valid;
import org.example.cinema.dto.DatVeDTO;
import org.example.cinema.model.DatVe;
import org.example.cinema.model.LichChieu;
import org.example.cinema.model.NguoiDung;
import org.example.cinema.repository.LichChieuRepository;
import org.example.cinema.repository.NguoiDungRepository;
import org.example.cinema.service.DatVeService;
import org.example.cinema.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller// đánh dấu class này là 1 controller
@RequestMapping("/dat-ve") // đường dẫn URL mà controller này xử lý
public class DatVeController { // class này để xử lý việc đặt vé
    // khai báo logger để ghi log khi có lỗi
    private static final Logger logger = LoggerFactory.getLogger(DatVeController.class);

    @Autowired
    private DatVeService datVeService;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    // hiển thị form đặt vé
    @GetMapping("/{lichChieuId}")
    public String showDatVeForm(@PathVariable Long lichChieuId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // tìm lịch chiếu theo id nếu không tìm thấy trả ra lỗi
        LichChieu lichChieu = lichChieuRepository.findById(lichChieuId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu"));

        // kiểm tra xem số chỗ đã đặt cộng với số lượng vé đặt có lớn hơn số chỗ ngồi không
        if (lichChieu.getSoChoDaDat() >= lichChieu.getSoChoNgoi()) {
            throw new RuntimeException("Đã hết vé cho suất chiếu này");
        }

        // tìm người dùng theo username
        NguoiDung nguoiDung = nguoiDungService.findByUsername(userDetails.getUsername());

        // tạo đối tượng DatVeDTO để truyền dữ liệu từ controller sang giao diện
        DatVeDTO datVeDTO = new DatVeDTO();
        datVeDTO.setLichChieuId(lichChieuId);
        datVeDTO.setHoTen(nguoiDung.getHoTen());
        datVeDTO.setEmail(nguoiDung.getEmail());
        datVeDTO.setNguoiDungId(nguoiDung.getId());

        // thêm đối tượng DatVeDTO vào model để truyền sang giao diện
        model.addAttribute("datVeDTO", datVeDTO);
        // trả về view form
        return "dat-ve/form";
    }


    // hàm này để đặt vé
    @PostMapping("mua-ve")
    public String datVe(@Valid @ModelAttribute DatVeDTO datVeDTO,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        // kiểm tra xem dữ liệu nhập vào có hợp lệ không
        if (result.hasErrors()) {
            // nếu không hợp lệ thì trả về view form
            logger.error("Form validation errors: {}", result.getAllErrors());
            // trả về view form
            return "dat-ve/form";
        }

        try {
            // gọi hàm datVe từ service để thực hiện đặt vé
            DatVe datVe = datVeService.datVe(datVeDTO);
            //Nếu không có lỗi, thông báo đặt vé thành công
            redirectAttributes.addFlashAttribute("success", "Đặt vé thành công!");
            // chuyển hướng về trang lịch sử mua vé
            return "redirect:/lich-su-mua-ve";
        } catch (Exception e) {
            //Nếu có lỗi, thông báo lỗi
            logger.error("Error during ticket booking", e);
            // chuyển hướng về trang đặt vé
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            // chuyển hướng về trang đặt vé
            return "redirect:/dat-ve/" + datVeDTO.getLichChieuId();
        }
    }
}