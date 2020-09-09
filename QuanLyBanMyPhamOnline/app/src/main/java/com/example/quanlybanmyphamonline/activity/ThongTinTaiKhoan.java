package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.quanlybanmyphamonline.Fragment.CaNhanFragment;
import com.example.quanlybanmyphamonline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThongTinTaiKhoan extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "TAG_FRAGMENT";
    Button btnLuuThongTin;
    EditText edtHoTen, edtSDT, edtNgaySinh;
    SharedPreferences sharedPreferences;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        AnhXa();
        sharedPreferences = this.getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin tài khoản");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TaoDuLieu();

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }

        });

        if(edtHoTen.getText().equals("") ||edtSDT.getText().equals("") ||edtNgaySinh.getText().equals("") )
        {
            Toast.makeText(this, "Mời nhập đầy đủ", Toast.LENGTH_SHORT).show();
        }
        {
            btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Capnhattrangthaimacdinh();
                }
            });
        }
    }

    private void AnhXa() {
        btnLuuThongTin = findViewById(R.id.canhan_Luu);
        edtHoTen = findViewById(R.id.canhan_HoTen);
        edtSDT = findViewById(R.id.canhan_SoDienThoai);
        edtNgaySinh = findViewById(R.id.canhan_NgaySinh);
    }

    private void ChonNgay()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year,month,day);
        datePickerDialog.show();
    }
    private void Capnhattrangthaimacdinh()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdanupdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1"))
                {
                    Toast.makeText(ThongTinTaiKhoan.this, "Đặt lại thành công", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ThongTinTaiKhoan.this, "Đặt lại thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("tendangnhap",name);
                param.put("HoTen",edtHoTen.getText().toString());
                param.put("SoDienThoai",edtSDT.getText().toString());
                param.put("NgaySinh",edtNgaySinh.getText().toString());
                return param;
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

    public void TaoDuLieu()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getTaiKhoan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                        JSONObject jsonObject = jsonArray.getJSONObject(0);


                        edtHoTen.setText(jsonObject.getString("HoTen"));

                       edtSDT.setText(jsonObject.getString("SoDienThoai"));
                    edtNgaySinh.setText(jsonObject.getString("NgaySinh"));





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
}