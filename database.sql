DROP DATABASE IF exists cinema_db;
-- Create the database
CREATE DATABASE cinema_db;
USE cinema_db;

-- Create phim table
CREATE TABLE phim (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      ten_phim VARCHAR(255) NOT NULL,
                      mo_ta TEXT,
                      the_loai VARCHAR(100),
                      thoi_luong INT,
                      anh_bia VARCHAR(255),
                      ngay_khoi_chieu DATE
);

-- Create rap_chieu table
CREATE TABLE rap_chieu (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           ten_rap VARCHAR(100) NOT NULL,
                           dia_chi TEXT,
                           so_ghe INT,
                           loai_rap VARCHAR(50)
);

-- Create lich_chieu table
CREATE TABLE lich_chieu (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            phim_id BIGINT,
                            rap_chieu_id BIGINT,
                            thoi_gian_chieu DATETIME,
                            gia_ve DOUBLE,
                            so_cho_ngoi INT,
                            so_cho_da_dat INT DEFAULT 0,
                            trang_thai VARCHAR(50),
                            version INT DEFAULT 0,
                            FOREIGN KEY (phim_id) REFERENCES phim(id),
                            FOREIGN KEY (rap_chieu_id) REFERENCES rap_chieu(id)
);

-- Create nguoi_dung table
CREATE TABLE nguoi_dung (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            username VARCHAR(50) UNIQUE,
                            password VARCHAR(255),
                            ho_ten VARCHAR(100),
                            email VARCHAR(100) UNIQUE,
                            so_dien_thoai VARCHAR(15)
);

-- Create vai_tro table
CREATE TABLE vai_tro (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         ten VARCHAR(50) UNIQUE
);

-- Create nguoi_dung_vai_tro table (Many-to-Many)
CREATE TABLE nguoi_dung_vai_tro (
                                    nguoi_dung_id BIGINT,
                                    vai_tro_id BIGINT,
                                    PRIMARY KEY (nguoi_dung_id, vai_tro_id),
                                    FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id),
                                    FOREIGN KEY (vai_tro_id) REFERENCES vai_tro(id)
);

-- Create ghe_ngoi table
CREATE TABLE ghe_ngoi (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          rap_chieu_id BIGINT,
                          lich_chieu_id BIGINT,
                          ma_ghe VARCHAR(10),
                          loai_ghe VARCHAR(50),
                          gia_ghe DOUBLE,
                          da_dat BOOLEAN DEFAULT FALSE,
                          FOREIGN KEY (rap_chieu_id) REFERENCES rap_chieu(id),
                          FOREIGN KEY (lich_chieu_id) REFERENCES lich_chieu(id)
);

-- Create dat_ve table
CREATE TABLE dat_ve (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        lich_chieu_id BIGINT,
                        nguoi_dung_id BIGINT,
                        ho_ten VARCHAR(100),
                        email VARCHAR(100),
                        so_dien_thoai VARCHAR(15),
                        so_luong_ve INT,
                        thoi_gian_dat DATETIME,
                        ma_giao_dich VARCHAR(50) UNIQUE,
                        trang_thai VARCHAR(50),
                        FOREIGN KEY (lich_chieu_id) REFERENCES lich_chieu(id),
                        FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id)
);

-- Create dat_ve_ghe table (Many-to-Many)
CREATE TABLE dat_ve_ghe (
                            dat_ve_id BIGINT,
                            ghe_ngoi_id BIGINT,
                            PRIMARY KEY (dat_ve_id, ghe_ngoi_id),
                            FOREIGN KEY (dat_ve_id) REFERENCES dat_ve(id),
                            FOREIGN KEY (ghe_ngoi_id) REFERENCES ghe_ngoi(id)
);

-- Create hoa_don table
CREATE TABLE hoa_don (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         dat_ve_id BIGINT UNIQUE,
                         tong_tien DOUBLE,
                         phuong_thuc_thanh_toan VARCHAR(50),
                         trang_thai VARCHAR(50),
                         ma_giao_dich VARCHAR(50) UNIQUE,
                         thoi_gian_thanh_toan DATETIME,
                         FOREIGN KEY (dat_ve_id) REFERENCES dat_ve(id)
);

-- Insert default roles
INSERT INTO vai_tro (ten) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
ALTER TABLE phim ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;