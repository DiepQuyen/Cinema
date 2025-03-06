package org.example.cinema.config;

import org.example.cinema.model.NguoiDung;
import org.example.cinema.model.VaiTro;
import org.example.cinema.repository.NguoiDungRepository;
import org.example.cinema.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component //Đánh dấu đây là một Spring Bean
//Class này sẽ chạy khi ứng dụng được khởi động
//Class này sẽ khởi tạo dữ liệu mẫu cho ứng dụng
//Tạo sẵn tài khoản admin khi ứng dụng chưa có tài khoản nào
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialize roles
        // Khởi tạo các vai trò
        VaiTro userRole = createRoleIfNotExists("ROLE_USER");
        VaiTro adminRole = createRoleIfNotExists("ROLE_ADMIN");


        // Tạo tài khoản admin nếu chưa tồn tại
        if (!nguoiDungRepository.existsByUsername("admin")) {
            NguoiDung admin = new NguoiDung();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setHoTen("Administrator");
            admin.setEmail("admin@cinema.com");
            admin.setVaiTro(Set.of(adminRole));
            nguoiDungRepository.save(admin);
        }
    }

    //Tạo vai trò nếu chưa tồn tại
    private VaiTro createRoleIfNotExists(String roleName) {
        //Tìm vai trò theo tên, nếu không tìm thấy thì tạo mới
        return vaiTroRepository.findByTen(roleName)
                .orElseGet(() -> {
                    VaiTro role = new VaiTro();
                    role.setTen(roleName);
                    return vaiTroRepository.save(role);
                });
    }
}