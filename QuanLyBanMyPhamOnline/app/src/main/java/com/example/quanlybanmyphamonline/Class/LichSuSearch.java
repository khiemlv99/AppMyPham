package com.example.quanlybanmyphamonline.Class;

import java.util.ArrayList;

public class LichSuSearch {
    public static ArrayList<HorizontalModel> getArrLichSu() {
        return arrLichSu;
    }

    public static void setArrLichSu(ArrayList<HorizontalModel> arrLichSu) {
        LichSuSearch.arrLichSu = arrLichSu;
    }

    public static ArrayList<HorizontalModel> arrLichSu =new ArrayList<>();



    public void addDanhSachSearch(HorizontalModel horizontalModel)
    {
         arrLichSu.add(horizontalModel);
    }
}
