package com.example.quanlybanmyphamonline.Class;

public class LoaiSP  {
    private int maLoai;
    private String tenLoai;
    private String hinhLoai;

    public LoaiSP(int maLoai, String tenLoai, String hinhLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.hinhLoai = hinhLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhLoai() {
        return hinhLoai;
    }

    public void setHinhLoai(String hinhLoai) {
        this.hinhLoai = hinhLoai;
    }
}
