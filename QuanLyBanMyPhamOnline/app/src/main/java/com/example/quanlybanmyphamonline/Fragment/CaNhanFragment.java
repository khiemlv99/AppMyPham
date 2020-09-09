package com.example.quanlybanmyphamonline.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlybanmyphamonline.Class.checkconnection;
import com.example.quanlybanmyphamonline.activity.DangNhapActivity;
import com.example.quanlybanmyphamonline.R;
import com.example.quanlybanmyphamonline.activity.HoaDonDaMuaActivity;
import com.example.quanlybanmyphamonline.activity.MainActivity;
import com.example.quanlybanmyphamonline.activity.SanPhamDaXemActivity;
import com.example.quanlybanmyphamonline.activity.SoDiaChi;
import com.example.quanlybanmyphamonline.activity.ThongTinTaiKhoan;

import static android.content.Context.MODE_PRIVATE;


public class CaNhanFragment extends Fragment {

    Button btnDangNhapDangKy,btnDangXuat,btnThongTinTT,btnSPDaXem,btnHoaDonDaMua;
    String name;
    SharedPreferences sharedPreferences;
    public CaNhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ca_nhan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        btnDangNhapDangKy = view.findViewById(R.id.buttonDangNhapDangKy);
        btnDangXuat=view.findViewById(R.id.btnDangXuat);
        btnThongTinTT=view.findViewById(R.id.btnThongTinTT);
        btnSPDaXem=view.findViewById(R.id.buttonSanPhamDaXem);
        btnHoaDonDaMua=view.findViewById(R.id.buttonSanPhamDaMua);
        sharedPreferences = getActivity().getSharedPreferences("ThongTin",MODE_PRIVATE);
        name=sharedPreferences.getString("name","false");
        String hoa= name.toUpperCase();
        if(checkconnection.haveNetworkConnection(getContext())) {
            btnDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = getContext().getSharedPreferences("ThongTin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    loadFragment(new CaNhanFragment());
                }
            });

            if (!name.equals("false")) {
                btnDangXuat.setVisibility(View.VISIBLE);
                btnThongTinTT.setVisibility(View.VISIBLE);
                btnDangNhapDangKy.setText("Chào mừng " + hoa);
                btnDangNhapDangKy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ThongTinTaiKhoan.class);
                        startActivity(intent);
//                        ((Activity)getContext()).finish();
                    }
                });
            } else {
                btnDangXuat.setVisibility(View.GONE);
                btnThongTinTT.setVisibility(View.GONE);
                btnDangNhapDangKy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), DangNhapActivity.class);
                        startActivity(intent);
                        ((Activity)getContext()).finish();
                    }
                });
            }

            btnThongTinTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SoDiaChi.class);
                    startActivity(intent);
//                    ((Activity)getContext()).finish();
                }
            });
            btnSPDaXem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SanPhamDaXemActivity.class);
                    startActivity(intent);
//                    ((Activity)getContext()).finish();
                }
            });
            btnHoaDonDaMua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), HoaDonDaMuaActivity.class);
                    startActivity(intent);
//                    ((Activity)getContext()).finish();
                }
            });

        }
        else
        {
            checkconnection.Toast_Short(getContext(), "Hãy kiểm tra lại kết nối ");
        }

    }
    public void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}