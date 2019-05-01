package com.example.appthoitiet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appthoitiet.R;
import com.example.appthoitiet.model.Hourly;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHourly extends BaseAdapter {

    Context context;
    ArrayList<Hourly> arrayListHourly;

    public AdapterHourly(Context context, ArrayList<Hourly> arrayListHourly) {
        this.context = context;
        this.arrayListHourly = arrayListHourly;
    }
    @Override
    public int getCount() {
        return arrayListHourly.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListHourly.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.line_hourly,null);
        Hourly hourly = arrayListHourly.get(position);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeHourly);
        TextView txtTemp = (TextView) convertView.findViewById(R.id.txtTempHourly);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIconHourly);
        txtTime.setText(hourly.Time);
        txtTemp.setText(hourly.Temp+"°C");
        Picasso.with(context).load("https://developer.accuweather.com/sites/default/files/"+hourly.Icon+"-s.png ").into(imgIcon);
        return convertView;

    }
}
