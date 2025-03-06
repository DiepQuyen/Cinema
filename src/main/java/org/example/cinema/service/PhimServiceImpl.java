package org.example.cinema.service;

import org.example.cinema.model.Phim;
import org.example.cinema.repository.PhimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Đánh dấu đây là một service
// Class này chứa các phương thức thao tác với bảng phim trong database
public class PhimServiceImpl implements PhimService {
    @Autowired
    private PhimRepository phimRepository;

    // Tìm tất cả phim
    @Override
    public List<Phim> findAll() {
        return phimRepository.findAllByIsActiveTrue();
    }

    // Tìm phim theo id
    @Override
    public Optional<Phim> findById(Long id) {
        return phimRepository.findById(id);
    }

    // Lưu phim
    @Override
    public Phim save(Phim phim) {
        return phimRepository.save(phim);
    }

    // Xóa phim theo id
    @Override
    public void deleteById(Long id) {
        // Tìm phim theo id
        Optional<Phim> phim = findById(id);
        // Nếu phim tồn tại
        if (phim.isPresent()) {
            // Lấy phim
            Phim existingPhim = phim.get();
            existingPhim.setIsActive(false);
            // Lưu phim
            phimRepository.save(existingPhim);
        }
    }

}