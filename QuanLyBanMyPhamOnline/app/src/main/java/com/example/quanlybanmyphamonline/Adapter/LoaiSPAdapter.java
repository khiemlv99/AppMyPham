package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanmyphamonline.Class.LoaiSP;
import com.example.quanlybanmyphamonline.Class.MyOnClickListener;
import com.example.quanlybanmyphamonline.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class LoaiSPAdapter extends RecyclerView.Adapter<LoaiSPAdapter.ViewHolder> {

    ArrayList<LoaiSP> arrayList;
    Context context;
    ChiTietSPAdapter adapter;


    public static int getIdtoangcuc() {
        return idtoangcuc;
    }

    public static void setIdtoangcuc(int idtoangcuc) {
        LoaiSPAdapter.idtoangcuc = idtoangcuc;
    }

    static int idtoangcuc;

    private MyOnClickListener myOnClickListener;

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayList, Context context) {
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
        holder.txtTen.setText(arrayList.get(position).getTenLoai());
        Picasso.get().load(arrayList.get(position).getHinhLoai()).into(holder.imgHinhLoai);
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.onClick(arrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen;
        ImageView imgHinhLoai;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.recyclerView1_id);
            imgHinhLoai = itemView.findViewById(R.id.imageview_ry1);
            relativeLayout = itemView.findViewById(R.id.relative_re1);
        }
    }
}
