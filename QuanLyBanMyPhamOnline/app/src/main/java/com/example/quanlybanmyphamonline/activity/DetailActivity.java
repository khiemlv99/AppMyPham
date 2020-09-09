package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Session2CommandGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.VerticalRecyclerViewAdapter;
import com.example.quanlybanmyphamonline.Class.GioHang;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.Class.VerticalModel;
import com.example.quanlybanmyphamonline.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView txtGiaSP,txtTenSP,txtMoTaSP;
    Spinner spinner;
    Button btnThemGH;
    ArrayList<VerticalModel> arrayListVertical;
    ArrayList<HorizontalModel> horizontalModelArrayList;
    VerticalModel verticalModel;
    RecyclerView verticalRecyclerView;
    VerticalRecyclerViewAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết sản phẩm");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AnhXa();
        DoDuLieu();
        ThietLapSpiner();
        Recyclerview();
        intent = getIntent();
        final int dongia = intent.getIntExtra("giasp",0);

        final int masp  = intent.getIntExtra("masp",0);
        final String ten = intent.getStringExtra("tensp");
        final String hinh = intent.getStringExtra("hinh");

        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGioHang.size() >0)
                {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean existt =false;


                    for(int i =0;i<MainActivity.mangGioHang.size();i++)
                    {
                        if(MainActivity.mangGioHang.get(i).getMasp() == masp)
                        {
                            MainActivity.mangGioHang.get(i).setSoluong(MainActivity.mangGioHang.get(i).getSoluong()+sl);
                            if(MainActivity.mangGioHang.get(i).getSoluong() >=10)
                            {
                                MainActivity.mangGioHang.get(i).setSoluong(10);
                            }
//                            MainActivity.mangGioHang.get(i).setGiasp(dongia * MainActivity.mangGioHang.get(i).getSoluong());
                            existt= true;
                        }
                    }
                    if(existt == false)
                    {
                        int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                        int giamoi = soluong * dongia;

                        MainActivity.mangGioHang.add(new GioHang(masp,ten,dongia,soluong,hinh));
                    }

                    //update san pham len phpmysql table gio hang
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updategiohang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1"))
                            {

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            int soluongmoi =0;
                            for(int i=0;i<MainActivity.mangGioHang.size();i++)
                            {
                                if(MainActivity.mangGioHang.get(i).getMasp() == masp){
                                    soluongmoi = MainActivity.mangGioHang.get(i).getSoluong();

                                }

                            }

                            hashMap.put("soluong", String.valueOf(soluongmoi));
                            hashMap.put("MaSanPham", String.valueOf(masp));
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                    int giamoi = soluong * dongia;

                    MainActivity.mangGioHang.add(new GioHang(masp,ten,giamoi,soluong,hinh));

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.trangthaigiohang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1"))
                            {
                                Log.d("vl",response);
                                Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            int soluong =Integer.parseInt(spinner.getSelectedItem().toString()) ;

                            HashMap<String,String > hashMap = new HashMap<String,String>();

                            hashMap.put("MaSanPham", String.valueOf(masp));
                            hashMap.put("soluong", String.valueOf(soluong));

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
//                Intent intent = new Intent(DetailActivity.this, GioHangActivity.class);
//                startActivity(intent);
                addListInShareReferences();

                showDiaLogThongBao();
            }


        });

    }

    private void showDiaLogThongBao() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_design_muasanpham);

        ImageView imgHinh = dialog.findViewById(R.id.dialog_hinh);
        TextView txtTieuDe = dialog.findViewById(R.id.dialog_tieude);
        TextView txtGia = dialog.findViewById(R.id.dialog_gia);
        Button btnXemGio = dialog.findViewById(R.id.dialog_xemgiohanghi);
        txtTieuDe.setText(txtTenSP.getText());
        txtGia.setText(txtGiaSP.getText());
        Picasso.get().load(intent.getStringExtra("hinh"))
                .placeholder(R.drawable.bill32).error(R.drawable.cart32).into(imgHinh);

        btnXemGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, GioHangActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogScale;
        dialog.show();
    }

    private void ThietLapSpiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_dropdown_item_1line,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    public void AnhXa()
    {
       imageView = findViewById(R.id.imageviewctsp);
       btnThemGH=findViewById(R.id.btnThemGH);
       txtGiaSP=findViewById(R.id.txtGiaCTSP);
       txtTenSP=findViewById(R.id.txtTenCTSP);
       txtMoTaSP=findViewById(R.id.txtMoTaCTSP);
       verticalRecyclerView=findViewById(R.id.recyclerViewDetail);
       spinner =findViewById(R.id.spinner);
    }

    public void DoDuLieu()
    {
        intent =getIntent();
        txtTenSP.setText(intent.getStringExtra("tensp"));
        txtMoTaSP.setText(intent.getStringExtra("motasp"));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSP.setText(decimalFormat.format(intent.getIntExtra("giasp",0))+ " Đ");

        Picasso.get().load(intent.getStringExtra("hinh")).placeholder(R.drawable.bill32).error(R.drawable.cart32).into(imageView);
    }

    private void setData() {
        verticalModel = new VerticalModel();
        verticalModel.setTilte("Sản phẩm liên quan");

        horizontalModelArrayList = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdansanphamcungloai, new Response.Listener<String>() {
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

                        horizontalModelArrayList.add(horizontalModel);
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
                String ma= String.valueOf(intent.getIntExtra("maloaisp",0));
                param.put("LoaiSanPham",String.valueOf(ma));
                return  param;
            }
        };

        requestQueue.add(stringRequest);


        verticalModel.setArrayList(horizontalModelArrayList);
        arrayListVertical.add(verticalModel);
        adapter.notifyDataSetChanged();
    }

    public void Recyclerview() {
        arrayListVertical = new ArrayList<>();
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new VerticalRecyclerViewAdapter(this, arrayListVertical);
        verticalRecyclerView.setAdapter(adapter);
        setData();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public  void addListInShareReferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.mangGioHang);
        editor.putString("list",json);
        editor.apply();
    }
}