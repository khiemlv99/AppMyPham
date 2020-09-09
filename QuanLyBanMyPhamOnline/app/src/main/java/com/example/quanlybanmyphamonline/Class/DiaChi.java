package com.example.quanlybanmyphamonline.Class;

public class DiaChi {

    public String sdt;
    public String diachi;
    public String tennguoinhan;
    public String tendangnhap;

    public String getMacdinh() {
        return macdinh;
    }

    public void setMacdinh(String macdinh) {
        this.macdinh = macdinh;
    }

    public String macdinh;
    public int madiachi,trangthai;

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public int getMadiachi() {
        return madiachi;
    }

    public void setMadiachi(int madiachi) {
        this.madiachi = madiachi;
    }

    public DiaChi(String sdt, String diachi, String tennguoinhan, String tendangnhap, int madiachi,int trangthai) {
        this.sdt = sdt;
        this.diachi = diachi;
        this.tennguoinhan = tennguoinhan;
        this.tendangnhap = tendangnhap;
        this.madiachi = madiachi;
        this.trangthai=trangthai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTennguoinhan() {
        return tennguoinhan;
    }

    public void setTennguoinhan(String tennguoinhan) {
        this.tennguoinhan = tennguoinhan;
    }
}
