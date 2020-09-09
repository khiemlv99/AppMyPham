package com.example.quanlybanmyphamonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanmyphamonline.Adapter.HoaDonDaMuaAdapter;
import com.example.quanlybanmyphamonline.Class.HoaDon;
import com.example.quanlybanmyphamonline.Class.Server;
import com.example.quanlybanmyphamonline.Class.checkconnection;
import com.example.quanlybanmyphamonline.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class XacNhanDonHangActivity extends AppCompatActivity implements OnMapReadyCallback    {
    protected LocationManager locationManager;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;
    TextView txtTen,txtSDT,txtDiaChi,txtThayDoi;
    Button btnHuy,btnXacNhan;
    SharedPreferences sharedPreferences;
    SupportMapFragment mapFragment;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Xác nhận đơn hàng");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0ED7F1")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(XacNhanDonHangActivity.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(XacNhanDonHangActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            fetchLastLocation();
        }




        sharedPreferences = this.getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");

        AnhXa();

        TaoDuLieu();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        TaoHoaDon(txtTen.getText().toString(),txtSDT.getText().toString(),txtDiaChi.getText().toString());

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(XacNhanDonHangActivity.this,SoDiaChi.class);
                intent.putExtra("trangthai",1);
                startActivity(intent);
                finish();
            }
        });

    }


    public void AnhXa()
    {
        txtTen=findViewById(R.id.txttenxacnhan);
        txtSDT=findViewById(R.id.txtsdtxacnhan);
        txtDiaChi=findViewById(R.id.txtdiachixacnhan);
        btnHuy=findViewById(R.id.buttonHuyBoHoaDon);
        btnXacNhan=findViewById(R.id.button_XacNhanHoaDon);
        txtThayDoi=findViewById(R.id.txtThayDoi);
    }
    public  void addListInShareReferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.mangGioHang);
        editor.putString("list",json);
        editor.apply();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void TaoDuLieu()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.gettaikhoanmacdinh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String ten = jsonObject.getString("HoTen");
                    String diachi = jsonObject.getString("DiaChi");
                    String sdt = jsonObject.getString("SDT");

                    txtTen.setText(ten);
                    txtDiaChi.setText(diachi);
                    txtSDT.setText(sdt);


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


    public void TaoHoaDon(String hoten,String sodienthoai,String diachimacdinh)
    {
        final String ten = hoten;
        final String sdt = sodienthoai;
        final String diachi = diachimacdinh;
        if(ten.length() >0 && sdt.length() >0 && diachi.length()>0)
        {
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.insertdonhang, new Response.Listener<String>() {
                @Override
                public void onResponse(final String madonhang) {
                    //them thong tin san pham va thong tin khach hang vao hoa don
                    if(Integer.parseInt(madonhang)>0)
                    {
                        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.inserthoadon, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("hihi",response);
                                if(response.equals("1"))
                                {
                                    MainActivity.mangGioHang.clear();
                                    checkconnection.Toast_Short(getApplicationContext(),"Mua thành công!");
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    SharedPreferences sharedPreferences = getSharedPreferences("savegiohang",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    addListInShareReferences();
                                    checkconnection.Toast_Short(getApplicationContext(),"Tiếp tục mua hàng!");
                                }
                                else
                                {
                                    checkconnection.Toast_Short(getApplicationContext(),"Dữ liệu giỏ hàng bị lỗi!");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                JSONArray jsonArray = new JSONArray();
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy  h:mm a");
                                String date = df.format(Calendar.getInstance().getTime());
                                //get inten name tu CaNhanFragment

                                for(int i =0;i<MainActivity.mangGioHang.size();i++)
                                {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("madonhang",madonhang);
                                        jsonObject.put("MaSanPham",MainActivity.mangGioHang.get(i).getMasp());
                                        jsonObject.put("NgayGioMua",date);
                                        jsonObject.put("TenDangNhap",name);
                                        jsonObject.put("SoLuong",MainActivity.mangGioHang.get(i).getSoluong());
                                        jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGiasp());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("json",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("tenkhachhang",ten);
                    hashMap.put("sodienthoai",sdt);
                    hashMap.put("diachi",diachi);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        else
        {
            checkconnection.Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại dữ liệu");
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       if(currentLocation==null)
       {
           LatLng diachikhachhang = new LatLng(10.806141, 106.628711);
           mMap.animateCamera(CameraUpdateFactory.newLatLng(diachikhachhang));
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(diachikhachhang,80));
           mMap.addMarker(new MarkerOptions().position(diachikhachhang).title("Dia chi cua ban"));
       }
       else {
           LatLng diachikhachhang = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
           mMap.animateCamera(CameraUpdateFactory.newLatLng(diachikhachhang));
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(diachikhachhang,80));
           mMap.addMarker(new MarkerOptions().position(diachikhachhang).title("Dia chi cua ban"));
       }
        // Add a marker in Sydney and move the camera

    }
    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    currentLocation=location;

                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(XacNhanDonHangActivity.this);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE:
                if(grantResults.length>0&& grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                {

                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}