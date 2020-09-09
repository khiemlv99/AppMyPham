package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlybanmyphamonline.Class.HoaDon;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HoaDonDaMuaAdapter extends ArrayAdapter<HoaDon> {
    private Context context;
    private int layout;


    public HoaDonDaMuaAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public ArrayList<HoaDon> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HoaDon> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<HoaDon> arrayList;

    public HoaDonDaMuaAdapter(@NonNull Context context, int resource, ArrayList<HoaDon> arrayList) {
        super(context, resource);
        this.context = context;
        this.layout = resource;
        this.arrayList = arrayList;

    }

    private class ViewHolder
    {
        TextView txtName,txtThoiGian,txtSoLuong,txttongtien;
        ImageView imageView;

    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HoaDonDaMuaAdapter.ViewHolder holder;
        if(convertView ==null)

        {
            holder = new HoaDonDaMuaAdapter.ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtName=convertView.findViewById(R.id.txthotenmuahang);
            holder.txtThoiGian=convertView.findViewById(R.id.txtthoigianmua);
            holder.txttongtien=convertView.findViewById(R.id.txtgiasanpham);
            holder.txtSoLuong=convertView.findViewById(R.id.txtsoluongmuahang);
            holder.imageView=convertView.findViewById(R.id.imageViewDonHang);
            convertView.setTag(holder);
        }
        else
        {
            holder = (HoaDonDaMuaAdapter.ViewHolder) convertView.getTag();
        }
        final HoaDon sp=arrayList.get(position);
        holder.txtName.setText("Tên sản phẩm: "+sp.tensp);
        holder.txtSoLuong.setText("Số lượng "+sp.soluong+"");
        holder.txttongtien.setText("Tổng tiền: "+sp.tongtien+"");
        holder.txtThoiGian.setText("Thời gian: "+sp.thoigian);
        Picasso.get().load(sp.getHinhanh()).placeholder(R.drawable.bill32).error(R.drawable.cart32).into(holder.imageView);
        return convertView;
    }
}
