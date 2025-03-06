package org.example.cinema.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vai_tro")
@Data
public class VaiTro {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Giá trị tự tăng
    private Long id;

    private String ten;
}