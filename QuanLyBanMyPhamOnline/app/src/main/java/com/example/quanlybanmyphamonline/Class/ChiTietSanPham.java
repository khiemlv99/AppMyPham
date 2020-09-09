package com.example.quanlybanmyphamonline.Class;

public class ChiTietSanPham {
    private int masanpham;
    private String tenSanPham;
    private String hinhanh;
    private String mota;
    private int giasp;
    private int maLoai;
    private int soluong;

    public ChiTietSanPham(int masanpham, String tenSanPham,
                          String hinhanh, String mota, int giasp, int maLoai, int soluong) {
        this.masanpham = masanpham;
        this.tenSanPham = tenSanPham;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.giasp = giasp;
        this.maLoai = maLoai;
        this.soluong = soluong;
    }

    public ChiTietSanPham(int masanpham, String tenSanPham, String hinhanh, int giasp) {
        this.masanpham = masanpham;
        this.tenSanPham = tenSanPham;
        this.hinhanh = hinhanh;
        this.giasp = giasp;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
