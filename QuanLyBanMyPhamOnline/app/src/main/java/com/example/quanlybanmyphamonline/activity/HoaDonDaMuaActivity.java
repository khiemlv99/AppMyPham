package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.HoaDonDaMuaAdapter;
import com.example.quanlybanmyphamonline.Class.HoaDon;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoaDonDaMuaActivity extends AppCompatActivity {

    ArrayList<HoaDon> arrayList;
    HoaDonDaMuaAdapter adapter;
    ListView listView;
    String name;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_da_mua);
        sharedPreferences = this.getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");

        getSupportActionBar().setTitle("Sản phẩm đã mua");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=findViewById(R.id.lstHoaDonDaMua);
        arrayList=new ArrayList<>();
        adapter=new HoaDonDaMuaAdapter(this,R.layout.item_donhangdamua,arrayList);
        listView.setAdapter(adapter);
        TaoDuLieu();
    }

    public void TaoDuLieu()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.gethoadon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ten = jsonObject.getString("TenSanPham");

                        int soluong = jsonObject.getInt("SoLuong");
                        int gia = jsonObject.getInt("giasanpham");
                        String hinhanh=jsonObject.getString("HinhAnh");
                        String thoigian = jsonObject.getString("NgayGioMua");


                        HoaDon hoaDon=new HoaDon(ten,thoigian,hinhanh,soluong,gia);


                        arrayList.add(hoaDon);
                        adapter.notifyDataSetChanged();
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