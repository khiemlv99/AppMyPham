package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlybanmyphamonline.Class.DiaChi;
import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamDaXemAdapter extends ArrayAdapter<HorizontalModel> {
    private Context context;
    private int layout;


    public SanPhamDaXemAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public ArrayList<HorizontalModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HorizontalModel> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<HorizontalModel> arrayList;

    public SanPhamDaXemAdapter(@NonNull Context context, int resource, ArrayList<HorizontalModel> arrayList) {
        super(context, resource);
        this.context = context;
        this.layout = resource;
        this.arrayList = arrayList;

    }

    private class ViewHolder
    {
        TextView txtName;
        ImageView imageView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SanPhamDaXemAdapter.ViewHolder holder;
        if(convertView ==null)

        {
            holder = new SanPhamDaXemAdapter.ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtName=convertView.findViewById(R.id.txtNameSPDaXem);
            holder.imageView=convertView.findViewById(R.id.imageViewSPDaXem);
            convertView.setTag(holder);
        }
        else
        {
            holder = (SanPhamDaXemAdapter.ViewHolder) convertView.getTag();
        }
        final HorizontalModel sp=arrayList.get(position);
        holder.txtName.setText(sp.getTen());
        Picasso.get().load(sp.getHinh()).placeholder(R.drawable.bill32).error(R.drawable.cart32).into(holder.imageView);
        return convertView;
    }
}
