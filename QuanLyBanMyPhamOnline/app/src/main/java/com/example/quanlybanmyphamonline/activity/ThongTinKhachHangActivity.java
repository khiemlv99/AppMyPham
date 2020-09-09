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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Class.DiaChi;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    EditText txtTen, txtDiaChi, txtDienThoai;
    Button btnThem;
    SharedPreferences sharedPreferences;
    String name = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        getSupportActionBar().setTitle("Thông tin địa chỉ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));

        AnhXa();
        intent = getIntent();
        txtTen.setText(intent.getStringExtra("tennguoinhan"));
        txtDienThoai.setText(intent.getStringExtra("sdt"));
        txtDiaChi.setText(intent.getStringExtra("diachi"));


        sharedPreferences = this.getSharedPreferences("ThongTin", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "false");

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtDiaChi.getText().length()==0 ||txtDienThoai.getText().length()==0||txtTen.getText().length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Mời nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        TaoDuLieu();
                    }
                }
            });

    }

    public void AnhXa() {
        txtTen = findViewById(R.id.textview_tennguoinhan);
        txtDiaChi = findViewById(R.id.textview_diachi);
        txtDienThoai = findViewById(R.id.textview_sodienthoai);
        btnThem = findViewById(R.id.btnThem);
    }

    private void themDiaChi(final int trangthai) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanthemdiachi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(ThongTinKhachHangActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ThongTinKhachHangActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("username", name);
                param.put("hoten", txtTen.getText().toString());
                param.put("diachi", txtDiaChi.getText().toString());
                param.put("sdt", txtDienThoai.getText().toString());
                param.put("trangthai", String.valueOf(trangthai));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


    public void TaoDuLieu() {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getthongtin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("[]")) {
                    themDiaChi(1);
                    Intent intent = new Intent(ThongTinKhachHangActivity.this,SoDiaChi.class);
                    startActivity(intent);
                    finish();
                    Intent intent1 = getIntent();

                    if( intent1.getIntExtra("kiemtra",0)==1)
                    {
                        Intent intent3 = new Intent(ThongTinKhachHangActivity.this, XacNhanDonHangActivity.class);
                        startActivity(intent3);
                    }

                } else {
                    if (intent.getIntExtra("madiachi",0) != 0) {
                        suadiachi(intent.getIntExtra("madiachi", 0));
                        Intent intent = new Intent(ThongTinKhachHangActivity.this,SoDiaChi.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent1=getIntent();
                        int temp=intent1.getIntExtra("trangthai",0);
                        if(temp!=0)
                        {
                            themDiaChi(0);
                            Intent intent = new Intent(ThongTinKhachHangActivity.this, SoDiaChi.class);
                            intent.putExtra("trangthai",1);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            themDiaChi(0);
                            Intent intent = new Intent(ThongTinKhachHangActivity.this, SoDiaChi.class);
                            startActivity(intent);
                            finish();
                        }
                    }


                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("username", name);
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
                    Toast.makeText(ThongTinKhachHangActivity.this, "Đặt lại thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ThongTinKhachHangActivity.this, "Đặt lại thất bại", Toast.LENGTH_SHORT).show();

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

    private void suadiachi(final int madiachi) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdancapnhatdiachi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(ThongTinKhachHangActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ThongTinKhachHangActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("username", name);
                param.put("madc", String.valueOf(madiachi));
                param.put("sdt", txtDienThoai.getText().toString().trim());
                param.put("hoten", txtTen.getText().toString().trim());
                param.put("diachi", txtDiaChi.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}