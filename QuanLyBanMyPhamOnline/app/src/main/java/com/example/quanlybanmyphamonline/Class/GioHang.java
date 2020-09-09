package com.example.quanlybanmyphamonline.Class;

public class GioHang {
    public int masp;
    public String tenDangNhap;
    public String tenSP;
    public int giasp;
    public int soluong;
    public String hinhanh;

    public GioHang(int masp,  String tenSP, int giasp, int soluong, String hinhanh) {
        this.masp = masp;
        this.tenSP = tenSP;
        this.giasp = giasp;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
