package com.example.quanlybanmyphamonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {

    RelativeLayout rlayout;;
    Animation animation;
    EditText txtTaiKhoan,txtMatKhau,txtNhapLaiMK;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtNhapLaiMK=findViewById(R.id.txtNhapLaiMK);
        txtTaiKhoan=findViewById(R.id.txtUsernameDK);
        txtMatKhau=findViewById(R.id.txtMatKhau);
        btnDangKy=findViewById(R.id.buttonDangKy);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(txtMatKhau.getText().toString().equals(txtNhapLaiMK.getText().toString())) {
                   ThemTaiKhoan();
               }
               else
                   Toast.makeText(DangKyActivity.this, "Không khớp mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    private void ThemTaiKhoan()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdanthemtaikhoan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(DangKyActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(DangKyActivity.this,DangNhapActivity.class);
                    intent.putExtra("username",txtTaiKhoan.getText().toString());
                    intent.putExtra("password",txtMatKhau.getText().toString());
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(DangKyActivity.this, " Đã tồn tại username ", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(DangKyActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",txtTaiKhoan.getText().toString());
                param.put("password",txtMatKhau.getText().toString());
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {

        finish();
        return;
    }
}