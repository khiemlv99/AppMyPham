package com.example.quanlybanmyphamonline.Class;

public class HorizontalModel {
    public  int masp,maloaisp;
    public String ten;
    public String mota;
    public String hinh;
    public int gia;
    public int soluong;

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGia() {
        return gia;
    }


    public HorizontalModel(int masp, int maloaisp, String ten, String mota, String hinh, int gia, int soluong) {
        this.masp = masp;
        this.maloaisp = maloaisp;
        this.ten = ten;
        this.mota = mota;
        this.hinh = hinh;
        this.gia = gia;
        this.soluong =soluong;
    }

    public HorizontalModel()
    {
          this.maloaisp=0;
          this.masp=0;
          this.ten="";
          this.mota="";
          this.hinh="";
          this.gia=0;
          this.soluong =0;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMaloaisp() {
        return maloaisp;
    }

    public void setMaloaisp(int maloaisp) {
        this.maloaisp = maloaisp;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
