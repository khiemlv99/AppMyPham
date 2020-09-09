package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlybanmyphamonline.Class.DiaChi;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.R;

import java.util.ArrayList;
import java.util.Locale;

public class DiaChiAdapter extends ArrayAdapter<DiaChi> {
    private Context context;
    private int layout;


    public DiaChiAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public ArrayList<DiaChi> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<DiaChi> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<DiaChi> arrayList;


    public DiaChiAdapter(@NonNull Context context, int resource, ArrayList<DiaChi> arrayList) {
        super(context, resource);
        this.context = context;
        this.layout = resource;
        this.arrayList = arrayList;

    }

    private class ViewHolder
    {
        TextView txtName,txtSDT,txtDiachi,txtDiaChiMacDinh;

    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null)

        {
            holder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtName=convertView.findViewById(R.id.txtTenNguoiNhan);
            holder.txtDiachi=convertView.findViewById(R.id.txtDiaChiNhanHang);
            holder.txtSDT=convertView.findViewById(R.id.txtSDT);
            holder.txtDiaChiMacDinh=convertView.findViewById(R.id.txtDiaChiMacDinh);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final DiaChi sp=arrayList.get(position);
        holder.txtName.setText(sp.tennguoinhan);
        holder.txtSDT.setText(sp.sdt);
        holder.txtDiachi.setText(sp.diachi);
        holder.txtDiaChiMacDinh.setText(sp.macdinh);

        return convertView;
    }
}
