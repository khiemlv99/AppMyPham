package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Class.ChiTietSanPham;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.R;
import com.example.quanlybanmyphamonline.activity.DetailActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ChiTietSPAdapter extends RecyclerView.Adapter<ChiTietSPAdapter.ViewHolder>{

    ArrayList<HorizontalModel> arrayList;
    Context context;

    public ChiTietSPAdapter(ArrayList<HorizontalModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.customlayout_recycler1,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtTieuDe.setText(arrayList.get(position).getTen());
        Picasso.get().load(arrayList.get(position).getHinh()).into(holder.imghinh);
        int id = arrayList.get(position).getMasp();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("masp",arrayList.get(position).getMasp());
                intent.putExtra("tensp",arrayList.get(position).getTen());
                intent.putExtra("giasp",arrayList.get(position).getGia());
                intent.putExtra("hinh",arrayList.get(position).getHinh());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTieuDe;
        ImageView imghinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe =itemView.findViewById(R.id.recyclerView1_id);
            imghinh  = itemView.findViewById(R.id.imageview_ry1);
        }
    }
}
