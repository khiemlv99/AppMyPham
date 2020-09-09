package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanmyphamonline.Class.GioHang;
import com.example.quanlybanmyphamonline.R;
import com.example.quanlybanmyphamonline.activity.GioHangActivity;
import com.example.quanlybanmyphamonline.activity.MainActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayListGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayListGioHang) {
        this.context = context;
        this.arrayListGioHang = arrayListGioHang;
    }


    @Override
    public int getCount() {
        return arrayListGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtTieuDe, txtGia;
        ImageView imgHinhanh;
        Button btnGiam, btnGiaTri,btnTang;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_dong_giohang,null);
            viewHolder.txtTieuDe  = convertView.findViewById(R.id.textViewTieuDeGioHang);
            viewHolder.txtGia = convertView.findViewById(R.id.textViewGiaGioHang);
            viewHolder.imgHinhanh = convertView.findViewById(R.id.imgViewHinhGioHang);
            viewHolder.btnGiam = convertView.findViewById(R.id.buttonGiam);
            viewHolder.btnGiaTri = convertView.findViewById(R.id.buttonGiatri);
            viewHolder.btnTang = convertView.findViewById(R.id.buttonTang);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtTieuDe.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGia.setText(decimalFormat.format(gioHang.getGiasp())+ " Đ");
        Picasso.get().load(gioHang.hinhanh).into(viewHolder.imgHinhanh);
        viewHolder.btnGiaTri.setText(gioHang.getSoluong()+"");

        //thiết lập cho nút tăng giảm của số lượng giỏ hàng
        int  sl = Integer.parseInt((viewHolder.btnGiaTri.getText().toString()));

        if(sl>=10)
        {
            viewHolder.btnTang.setEnabled(false);
            viewHolder.btnTang.setVisibility(View.VISIBLE);
            viewHolder.btnGiam.setVisibility(View.VISIBLE);
            viewHolder.btnGiam.setEnabled(true);
        }
        else if(sl<=1)
        {
            viewHolder.btnGiam.setVisibility(View.VISIBLE);
            viewHolder.btnGiam.setEnabled(false);
        }
        else if(sl>=1)
        {
            viewHolder.btnGiam.setEnabled(true);
            viewHolder.btnGiam.setVisibility(View.VISIBLE);
            viewHolder.btnTang.setVisibility(View.VISIBLE);
        }

        //bắt sự kiện cho nút button tăng
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  slmoinhat = Integer.parseInt((finalViewHolder.btnGiaTri.getText().toString())) +1;
                int slht = MainActivity.mangGioHang.get(position).getSoluong();
                int giaht = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluong(slmoinhat);
                int giamoi = (giaht * slmoinhat)/slht;
                MainActivity.mangGioHang.get(position).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGia.setText(decimalFormat.format(giamoi)+ " Đ");
                GioHangActivity.eventTinhTien();
                if(slmoinhat>9)
                {
                    finalViewHolder.btnTang.setEnabled(false);
                    finalViewHolder.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slmoinhat));
                }
                else
                {
                    finalViewHolder.btnTang.setEnabled(true);
                    finalViewHolder.btnGiam.setEnabled(true);
                    finalViewHolder.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slmoinhat));
                }
                SharedPreferences sharedPreferences = context.getSharedPreferences("savegiohang",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                addListInShareReferences();

            }
        });

        viewHolder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  slmoinhat = Integer.parseInt((finalViewHolder.btnGiaTri.getText().toString())) - 1;
                int slht = MainActivity.mangGioHang.get(position).getSoluong();
                int giaht = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluong(slmoinhat);
                int giamoi = (giaht * slmoinhat)/slht;
                MainActivity.mangGioHang.get(position).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGia.setText(decimalFormat.format(giamoi)+ " Đ");
                GioHangActivity.eventTinhTien();
                if(slmoinhat<2)
                {
                    finalViewHolder.btnTang.setEnabled(true);
                    finalViewHolder.btnGiam.setEnabled(false);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slmoinhat));
                }
                else
                {
                    finalViewHolder.btnGiam.setEnabled(true);
                    finalViewHolder.btnTang.setEnabled(true);
                    finalViewHolder.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiaTri.setText(String.valueOf(slmoinhat));
                }

                SharedPreferences sharedPreferences = context.getSharedPreferences("savegiohang",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                addListInShareReferences();
            }
        });

        return convertView;
    }

    public  void addListInShareReferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savegiohang",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.mangGioHang);
        editor.putString("list",json);
        editor.apply();
    }
}
