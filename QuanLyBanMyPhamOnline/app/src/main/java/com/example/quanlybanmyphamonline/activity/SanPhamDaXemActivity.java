package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.SanPhamDaXemAdapter;
import com.example.quanlybanmyphamonline.Class.DiaChi;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamDaXemActivity extends AppCompatActivity {

    ListView listView;
    SanPhamDaXemAdapter sanPhamDaXemAdapter;
    ArrayList<HorizontalModel> arrayList;
    String name;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_da_xem);
        sharedPreferences = this.getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");
        AnhXa();
        getSupportActionBar().setTitle("Sản phẩm đã xem");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        arrayList=new ArrayList<>();
        sanPhamDaXemAdapter=new SanPhamDaXemAdapter(this,R.layout.item_sanphamdaxem,arrayList);
        listView.setAdapter(sanPhamDaXemAdapter);
        TaoDuLieu();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SanPhamDaXemActivity.this, DetailActivity.class);
                intent.putExtra("masp",arrayList.get(position).getMasp());
                intent.putExtra("tensp",arrayList.get(position).getTen());
                intent.putExtra("giasp",arrayList.get(position).getGia());
                intent.putExtra("motasp",arrayList.get(position).getMota());
                intent.putExtra("hinh",arrayList.get(position).getHinh());
                intent.putExtra("maloaisp",arrayList.get(position).getMaloaisp());
                startActivity(intent);
            }
        });
    }

    public void AnhXa()
    {
        listView=findViewById(R.id.lstViewSanPhamDaXem);
    }


    public void TaoDuLieu()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getsanphamdaxem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int masanpham = jsonObject.getInt("masp");
                        String tensp = jsonObject.getString("tensp");

                        String mota = jsonObject.getString("mota");
                        int gia = jsonObject.getInt("gia");
                        int maloaisanpham = jsonObject.getInt("MaLoaiSanPham");
                        String hinh = jsonObject.getString("hinh");


                        HorizontalModel horizontalModel = new HorizontalModel();
                        horizontalModel.setGia(gia);
                        horizontalModel.setMota(mota);
                        horizontalModel.setTen(tensp);
                        horizontalModel.setHinh(hinh);
                        horizontalModel.setMaloaisp(maloaisanpham);
                        horizontalModel.setMasp(masanpham);

                        arrayList.add(horizontalModel);
                        sanPhamDaXemAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("username",name);
                return  param;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}