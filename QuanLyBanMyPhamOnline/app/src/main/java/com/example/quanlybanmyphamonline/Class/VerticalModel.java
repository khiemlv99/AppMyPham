package com.example.quanlybanmyphamonline.Class;

import java.util.ArrayList;

public class VerticalModel {

    public String tilte;
    public  ArrayList<HorizontalModel> arrayList;

        public VerticalModel()
          {
              this.tilte="";
              this.arrayList=null;
          }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public ArrayList<HorizontalModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HorizontalModel> arrayList) {
        this.arrayList = arrayList;
    }
}
