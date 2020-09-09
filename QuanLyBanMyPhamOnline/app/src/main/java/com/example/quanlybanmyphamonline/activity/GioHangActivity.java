package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.example.quanlybanmyphamonline.Adapter.GioHangAdapter;
import com.example.quanlybanmyphamonline.Class.GioHang;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {



    ListView lstgiohang;
    static TextView txtTongTien;
    LottieAnimationView lottieAnimationView;
    Button btnTienHanhThanhToan;
    SharedPreferences sharedPreferences;
    String name = "";
    GioHangAdapter gioHangAdapter;

    LinearLayout linearLayout;
    SwipeMenuListView listView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();



        sharedPreferences = this.getSharedPreferences("ThongTin", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "false");
        //setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        initSwipeMenu();

        checkData();
        eventTinhTien();


            btnTienHanhThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("ThongTin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String name = sharedPreferences.getString("name", "false");
                    if(!btnTienHanhThanhToan.getText().equals("Tiếp tục mua sắm")) {
                        if (!name.equals("false")) {
                            if (MainActivity.mangGioHang.size() > 0) {
                                SharedPreferences sharedPreferences1 = getSharedPreferences("savegiohang", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                editor1.clear();
                                addListInShareReferences();
                                TaoDuLieu();


                            } else {
                                Toast.makeText(GioHangActivity.this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(GioHangActivity.this, DangNhapActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else
                    { finish();

                    }
                }
            });
        }





    private void loadMoreData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list",null);
        Type type =new TypeToken<ArrayList<GioHang>>(){}.getType();
        MainActivity.mangGioHang = gson.fromJson(json,type);
        if(MainActivity.mangGioHang == null)
        {
            MainActivity.mangGioHang = new ArrayList<>();
        }
    }

    private void initSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width+
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                 //add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index)
                {
                    case 0:
                        xoaMotSanPham(position);
                        break;
                    case 1:

                        break;
                }
                return false;
            }


        });
    }

    private void xoaMotSanPham(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn chắc chắn muốn xóa sản phẩm này!");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(MainActivity.mangGioHang.size()<=0)
                {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    startAnimationLip();
                }
                else
                {
                    MainActivity.mangGioHang.remove(position);
                    gioHangAdapter.notifyDataSetChanged();
                    eventTinhTien();

                    if(MainActivity.mangGioHang.size() <=0)
                    {
                        lottieAnimationView.setVisibility(View.VISIBLE);

                        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        addListInShareReferences();
                    }
                    else
                    {
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        gioHangAdapter.notifyDataSetChanged();
                        eventTinhTien();
                        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        addListInShareReferences();
                    }
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gioHangAdapter.notifyDataSetChanged();
                eventTinhTien();
            }
        });
        builder.show();
    }

    public static void eventTinhTien() {
        int tongtien = 0;
        for(int i =0;i<MainActivity.mangGioHang.size();i++)
        {
            int sl = MainActivity.mangGioHang.get(i).getSoluong();
            int dongia = MainActivity.mangGioHang.get(i).getGiasp();
            tongtien += sl*dongia;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien)+" Đ");
    }

    private void checkData() {
        if(MainActivity.mangGioHang.size() <=0)
        {
            linearLayout.setVisibility(View.GONE);
            //lstgiohang.deferNotifyDataSetChanged();
            listView.deferNotifyDataSetChanged();
            //txtThongBao.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);

            //lstgiohang.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.INVISIBLE);
            btnTienHanhThanhToan.setText("Tiếp tục mua sắm");
        }
        else
        {
            //lstgiohang.deferNotifyDataSetChanged();
            listView.deferNotifyDataSetChanged();
            //txtThongBao.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.INVISIBLE);
            //lstgiohang.setVisibility(View.VISIBLE);
            lstgiohang.setVisibility(View.VISIBLE);
        }
    }


    private void AnhXa() {
        listView = findViewById(R.id.listViewGioHang);
        lstgiohang = findViewById(R.id.listViewGioHang);
        //txtThongBao = findViewById(R.id.textViewThongBao);
        txtTongTien = findViewById(R.id.textViewTongTien);
        btnTienHanhThanhToan = findViewById(R.id.buttonTienHanhDatHang);
        //toolbarGioHang = findViewById(R.id.toolbarGioHang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,MainActivity.mangGioHang);
        //lstgiohang.setAdapter(gioHangAdapter);
        listView.setAdapter(gioHangAdapter);

        linearLayout = findViewById(R.id.linearTien);
        lottieAnimationView = findViewById(R.id.animation_noItem);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    private void startAnimationLip() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((Float) animation.getAnimatedValue());

            }
        });
        if (lottieAnimationView.getProgress() == 0f) {
            animator.start();
        } else {
            lottieAnimationView.setProgress(0f);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    public  void addListInShareReferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.mangGioHang);
        editor.putString("list",json);
        editor.apply();
    }

    public void TaoDuLieu() {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getthongtin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("[]")) {
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHangActivity.class);
                    intent.putExtra("kiemtra",1);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),XacNhanDonHangActivity.class);
                    startActivity(intent);
                    finish();
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


}