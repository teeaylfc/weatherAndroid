package com.example.myapplication1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.R;
import com.example.myapplication1.model.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {
    Context context;
    ArrayList<Weather> weatherList;

    public AdapterList(Context context, ArrayList<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.line_daylater,null);
        Weather thoiTiet = weatherList.get(position);
        TextView txtNgaylist = (TextView) convertView.findViewById(R.id.txtDayList);
        TextView txtTrangthailist = (TextView) convertView.findViewById(R.id.txtTrangThaiList);
        ImageView imgIconlist = (ImageView) convertView.findViewById(R.id.imgIconList);
        TextView txtMinList = (TextView) convertView.findViewById(R.id.txtMinList);
        TextView txtMaxList = (TextView) convertView.findViewById(R.id.txtMaxList);
        txtNgaylist.setText(thoiTiet.date);
        txtTrangthailist.setText(thoiTiet.status);
        txtMinList.setText(thoiTiet.tempMin+"°C");
        txtMaxList.setText(thoiTiet.tempMax+"°C");
        Picasso.with(context).load("https://developer.accuweather.com/sites/default/files/"+thoiTiet.imgicon+"-s.png ").into(imgIconlist);
        return convertView ;
    }
}
