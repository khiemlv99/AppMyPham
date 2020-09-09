package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.DiaChiAdapter;
import com.example.quanlybanmyphamonline.Adapter.ListViewTimKiemAdapter;
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

public class SoDiaChi extends AppCompatActivity {

    ArrayList<DiaChi> arr;
    DiaChiAdapter adapter;
    ListView listView;
    TextView txtTen,txtSDT,txtDiaChi;
    Button btnThem;
    SharedPreferences sharedPreferences;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_dia_chi);
        anhXa();

        getSupportActionBar().setTitle("Thông tin thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));

        sharedPreferences = this.getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");
        arr = new ArrayList<>();
        adapter= new DiaChiAdapter(this,R.layout.item_diachi,arr);
        listView.setAdapter(adapter);
        TaoDuLieu();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=getIntent();
                int temp=intent1.getIntExtra("trangthai",0);
                if(temp != 0)
                {
                    Intent intent = new Intent(SoDiaChi.this,ThongTinKhachHangActivity.class);
                    intent.putExtra("trangthai",1);
                    startActivity(intent);

                }
                else {
                    Intent intent = new Intent(SoDiaChi.this, ThongTinKhachHangActivity.class);
                    startActivity(intent);

                }
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.macdinh:
                if(adapter.getArrayList().get(info.position).trangthai==1) {
                    Toast.makeText(this, "Đã là mặc định", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent intent1 = getIntent();
                    int temp = intent1.getIntExtra("trangthai", 0);
                    if (temp != 0) {
                        for (int i = 0; i < adapter.getArrayList().size(); i++) {
                            if (adapter.getArrayList().get(i).trangthai == 1) {
                                Capnhattrangthaimacdinh(adapter.getArrayList().get(i).madiachi, 0);
                            }
                        }
                        Capnhattrangthaimacdinh(adapter.getArrayList().get(info.position).madiachi, 1);
                        Intent intent = new Intent(SoDiaChi.this, XacNhanDonHangActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        for (int i = 0; i < adapter.getArrayList().size(); i++) {
                            if (adapter.getArrayList().get(i).trangthai == 1) {
                                Capnhattrangthaimacdinh(adapter.getArrayList().get(i).madiachi, 0);
                            }
                        }
                        Capnhattrangthaimacdinh(adapter.getArrayList().get(info.position).madiachi, 1);
                        finish();
                        startActivity(getIntent());
                    }
                }
                return true;
            case R.id.Xoa:
                if(adapter.getArrayList().get(info.position).trangthai==1) {
                    Toast.makeText(this, "Không được xóa địa chỉ mặc định", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    xoaDiaChi(adapter.getArrayList().get(info.position).madiachi);
                    arr.remove(info.position);
                    adapter.notifyDataSetChanged();
                }
                return true;
            case R.id.Sua:
                Intent intent = new Intent(SoDiaChi.this,ThongTinKhachHangActivity.class);
                intent.putExtra("madiachi",adapter.getArrayList().get(info.position).madiachi);
                intent.putExtra("tennguoinhan",adapter.getArrayList().get(info.position).tennguoinhan);
                intent.putExtra("sdt",adapter.getArrayList().get(info.position).sdt);
                intent.putExtra("diachi",adapter.getArrayList().get(info.position).diachi);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
    private void xoaDiaChi(final int madiachi)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdanxoadiachi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(SoDiaChi.this, "Xóa thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SoDiaChi.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",name);
                param.put("madc",String.valueOf(madiachi));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void Capnhattrangthaimacdinh(final int madiachi, final int trangthai)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdandatlaitrangthai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(SoDiaChi.this, "Đặt lại thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SoDiaChi.this, "Đặt lại thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",name);
                param.put("madc",String.valueOf(madiachi));
                param.put("trangthai",String.valueOf(trangthai));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void anhXa()
    {
        listView = findViewById(R.id.lstViewDiaChi);
        txtSDT=findViewById(R.id.txtSDT);
        txtTen=findViewById(R.id.txtTenNguoiNhan);
        txtDiaChi=findViewById(R.id.txtDiaChiNhanHang);
        btnThem=findViewById(R.id.btnThemThongTin);
    }
    public void TaoDuLieu()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getthongtin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int madiachi = jsonObject.getInt("MaDiaChi");
                        String tendangnhap = jsonObject.getString("TenDangNhap");

                        String hoten = jsonObject.getString("HoTen");
                        String diachi = jsonObject.getString("DiaChi");
                        String sdt = jsonObject.getString("SDT");
                        int trangthai=jsonObject.getInt("TrangThai");

                        DiaChi diaChi = new DiaChi(sdt,diachi,hoten,tendangnhap,madiachi,trangthai);
                        if(trangthai==0)
                        {
                            diaChi.setMacdinh("");
                        }
                        else
                        {
                            diaChi.setMacdinh("Địa chỉ mặc định");
                        }
                        arr.add(diaChi);
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