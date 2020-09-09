package com.example.quanlybanmyphamonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlybanmyphamonline.Class.HorizontalModel;
import com.example.quanlybanmyphamonline.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListViewTimKiemAdapter extends ArrayAdapter<HorizontalModel> {
    private Context context;
    private int layout;

    public ArrayList<HorizontalModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HorizontalModel> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<HorizontalModel> arrayList;

    public ArrayList<HorizontalModel> getList() {
        return list;
    }

    public void setList(ArrayList<HorizontalModel> list) {
        this.list = list;
    }

    ArrayList<HorizontalModel> list;

    public ListViewTimKiemAdapter(@NonNull Context context, int resource, ArrayList<HorizontalModel> arrayList) {
        super(context, resource);
        this.context = context;
        this.layout = resource;
        this.arrayList = arrayList;

        this.list=new ArrayList<HorizontalModel>();
        this.list.addAll(arrayList);
    }

    private class ViewHolder
    {
        TextView txtName;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null)

        {
            holder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtName=convertView.findViewById(R.id.textViewTimKiem);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final HorizontalModel sp=arrayList.get(position);
        holder.txtName.setText(sp.getTen());

        return convertView;
    }

    public void filter(String chartext)
    {
        chartext=chartext.toLowerCase(Locale.getDefault());
        if(arrayList!=null)
        {
            arrayList.clear();
        }
        if(chartext.length()==0)
        {
            arrayList.addAll(list);

        }
        else
        {
            for(HorizontalModel sp :list)
            {
                if(sp.getTen().toLowerCase(Locale.getDefault()).contains(chartext) )
                {
                    arrayList.add(sp);
                }
            }
        }

        notifyDataSetChanged();
    }
}
