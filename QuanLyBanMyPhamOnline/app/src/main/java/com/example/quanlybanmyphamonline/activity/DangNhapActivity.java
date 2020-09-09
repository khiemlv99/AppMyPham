package com.example.quanlybanmyphamonline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.Fragment.TimKiemFragment;
import com.example.quanlybanmyphamonline.R;
import com.facebook.CallbackManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {

    ImageButton btnDangky;
    Button btnDangNhap,btnFB;
    TextView tvLogin;
    EditText txtTaiKhoan,txtMatKhau;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap_activity);
//        callbackManager = CallbackManager.Factory.create();
        ActionBar actionBar = getSupportActionBar();
//        Drawable drawable= getResources().getDrawable(R.drawable.delete36);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(drawable);
        actionBar.hide();

        AnhXa();

        Intent intent=getIntent();

        if(intent!=null)
        {
            txtTaiKhoan.setText(intent.getStringExtra("username"));
            txtMatKhau.setText(intent.getStringExtra("password"));
        }
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraDangNhap();
            }
        });

    }

    private void AnhXa() {
        btnDangky = findViewById(R.id.imageButtonDangKy);
        tvLogin = findViewById(R.id.tvLogin);
        txtTaiKhoan=findViewById(R.id.editTextUser);
        txtMatKhau=findViewById(R.id.editTextPassword);
        btnDangNhap=findViewById(R.id.buttonDangNhap);
        btnFB=findViewById(R.id.btnFacebook);
    }

    public void kiemTraDangNhap()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.kiemtradangnhap, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject!=null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("ThongTin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", txtTaiKhoan.getText().toString());
                            editor.putString("pass", txtMatKhau.getText().toString());
                            editor.commit();

                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(DangNhapActivity.this, "Không có username", Toast.LENGTH_SHORT).show();
                        }
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
                param.put("username",txtTaiKhoan.getText().toString());
                param.put("password",txtMatKhau.getText().toString());
                return  param;
            }
        };

        requestQueue.add(stringRequest);

    }



}