package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.quanlybanmyphamonline.Class.GioHang;
import com.example.quanlybanmyphamonline.Class.SendData;
import com.example.quanlybanmyphamonline.Fragment.CaNhanFragment;
import com.example.quanlybanmyphamonline.Fragment.DanhMucFragment;
import com.example.quanlybanmyphamonline.Fragment.ThongBaoFragment;
import com.example.quanlybanmyphamonline.Fragment.TimKiemFragment;
import com.example.quanlybanmyphamonline.Fragment.TrangChuFragment;
import com.example.quanlybanmyphamonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nex3z.notificationbadge.NotificationBadge;

import org.w3c.dom.Comment;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.sql.CommonDataSource;

public class MainActivity extends AppCompatActivity implements SendData {

    BottomNavigationView bottomNavigationView;
    EditText searchview;
    ImageView imageView;
    LinearLayout layout;
    boolean status =false;
    NotificationBadge badge;
    TrangChuFragment trangChuFragment;
    MenuItem menuItem;
    public static ArrayList<GioHang> mangGioHang;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        loadMoreData();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4BCEDF")));

        bottomNavigationView = findViewById(R.id.nav_main);
        searchview = findViewById(R.id.search_view);
        imageView = findViewById(R.id.btnGioHang);
        badge = findViewById(R.id.badge_cart_Main);
        updateSoLuongGioHang();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                finish();

            }
        });


        layout=findViewById(R.id.search_container);
//        tao mang toan cuc chua danh sach hang da mua
        if(mangGioHang !=null)
        {
            Toast.makeText(this, mangGioHang.size()+"", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mangGioHang = new ArrayList<>();
        }
        Intent intent =getIntent();
        if(intent.getIntExtra("trangthai",0)==2)
        {
            bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
            bottomNavigationView.setSelectedItemId(R.id.navigation_Danhmuc);
        }
        else {
            bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
            bottomNavigationView.setSelectedItemId(R.id.navigation_trangchu);
        }
       searchview.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               if(MotionEvent.ACTION_UP == event.getAction())
               {
                   TimKiemFragment fragment1= new TimKiemFragment();
                   layout.setVisibility(View.GONE);
                   loadFragment(fragment1);
               }

               return false;
           }
       });
//        searchview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            });

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

    private void updateSoLuongGioHang() {
        if(badge == null)
        {
            return;
        }
        if(mangGioHang == null) return;
        if(mangGioHang.size() == 0)
        {
            badge.setVisibility(View.INVISIBLE);
        }
        else
        {
            badge.setVisibility(View.VISIBLE);
            badge.setText(mangGioHang.size()+"");
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_trangchu:
                    getSupportActionBar().setTitle("Trang chủ");
                    TrangChuFragment fragment = new TrangChuFragment();
                    layout.setVisibility(View.VISIBLE);
//                    updateSoLuongGioHang();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_TimKiem:
                    TimKiemFragment fragment1= new TimKiemFragment();
                    layout.setVisibility(View.GONE);
                    loadFragment(fragment1);
                    return true;
                case R.id.navigation_Danhmuc:
                    getSupportActionBar().setTitle("Danh mục");
                    DanhMucFragment fragment2 = new DanhMucFragment();
                    layout.setVisibility(View.VISIBLE);
                    loadFragment(fragment2);
                    return true;
                case R.id.navigation_ThongBao:
                    getSupportActionBar().setTitle("Thông báo");
                    ThongBaoFragment fragment3 = new ThongBaoFragment();
                    layout.setVisibility(View.VISIBLE);
                    loadFragment(fragment3);
                    return true;
                case R.id.navigation_CaNhan:
                    getSupportActionBar().setTitle("Cá nhân");
                    CaNhanFragment fragment4 = new CaNhanFragment();
                    layout.setVisibility(View.GONE);
                    loadFragment(fragment4);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void sendData(int name) {
        DanhMucFragment demo1Fragment =new DanhMucFragment();
        if (demo1Fragment != null || demo1Fragment.isInLayout()) { // kiem tra fragment can truyen data den co thuc su ton tai va dang hien
            {
                this.setIdloai(name);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Fragment is not exist", Toast.LENGTH_LONG).show();
        }
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public  static int idloai=0;

    @Override
    protected void onResume() {
        super.onResume();
        updateSoLuongGioHang();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
