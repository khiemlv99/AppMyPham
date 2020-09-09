package com.example.quanlybanmyphamonline.Class;

public class HoaDon {
    public String tensp,thoigian,hinhanh;
    public int soluong,tongtien;

    public HoaDon(String tensp, String thoigian, String hinhanh, int soluong, int tongtien) {
        this.tensp = tensp;
        this.thoigian = thoigian;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
        this.tongtien = tongtien;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
