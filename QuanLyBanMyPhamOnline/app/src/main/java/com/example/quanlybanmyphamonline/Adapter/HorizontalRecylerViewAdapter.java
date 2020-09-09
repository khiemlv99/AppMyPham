package com.example.quanlybanmyphamonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;
import com.example.quanlybanmyphamonline.activity.DetailActivity;
import com.example.quanlybanmyphamonline.activity.MainActivity;
import com.example.quanlybanmyphamonline.activity.ThongTinKhachHangActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HorizontalRecylerViewAdapter extends RecyclerView.Adapter<HorizontalRecylerViewAdapter.HorizontalViewHolder> {

    public Context context;
    public ArrayList<HorizontalModel> arrayList;
    SharedPreferences sharedPreferences;
    String name;


    public  HorizontalRecylerViewAdapter(Context context , ArrayList<HorizontalModel>arrayList)
    {
        this.arrayList=arrayList;
        this.context=context;

    }
    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal,parent,false);
        return new HorizontalViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalViewHolder holder, final int position) {
        final HorizontalModel horizontalModel=arrayList.get(position);
        holder.txtTen.setText(horizontalModel.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText(decimalFormat.format(horizontalModel.getGia())+" Đ");
        holder.txtMoTa.setText(horizontalModel.getMota());
        Picasso.get().load(horizontalModel.getHinh()).placeholder(R.drawable.bill32).error(R.drawable.cart32).into(holder.imageView);

        holder.txtTen.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.txtMoTa.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.txtGia.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences("ThongTin",MODE_PRIVATE);
                name=sharedPreferences.getString("name","false");
                themSanPhamDaXem(arrayList.get(position).masp,name);



                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("masp",arrayList.get(position).getMasp());
                intent.putExtra("tensp",arrayList.get(position).getTen());
                intent.putExtra("giasp",arrayList.get(position).getGia());
                intent.putExtra("motasp",arrayList.get(position).getMota());
                intent.putExtra("hinh",arrayList.get(position).getHinh());
                intent.putExtra("maloaisp",arrayList.get(position).getMaloaisp());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class  HorizontalViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTen,txtGia,txtMoTa;
        ImageView imageView;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTen =(TextView)itemView.findViewById(R.id.txtTextTilteHorizontal);
            txtGia =(TextView)itemView.findViewById(R.id.txtGiaBan);
            txtMoTa =(TextView)itemView.findViewById(R.id.txtMoTa);
            imageView=(ImageView)itemView.findViewById(R.id.Image);

        }
    }

    private void themSanPhamDaXem(final int masp, final String ten) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanthemsanphamdaxem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();


                param.put("username",ten);
                param.put("masp", String.valueOf(masp));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

}
