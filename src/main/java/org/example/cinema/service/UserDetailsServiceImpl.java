package org.example.cinema.service;

import org.example.cinema.model.NguoiDung;
import org.example.cinema.model.VaiTro;
import org.example.cinema.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Đánh dấu đây là một service
// Class này cung cấp dịch vụ liên quan đến UserDetails để bảo mật
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    // Phương thức này sẽ được gọi khi người dùng đăng nhập
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm người dùng theo username
        NguoiDung nguoiDung = nguoiDungRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));

        // Trả về UserDetails dùng để xác thực
        return org.springframework.security.core.userdetails.User
                .withUsername(nguoiDung.getUsername())
                .password(nguoiDung.getPassword())
                .roles(nguoiDung.getVaiTro().stream()
                        .map(VaiTro::getTen)
                        .map(ten -> ten.replace("ROLE_", ""))
                        .toArray(String[]::new))
                .build();
    }
}