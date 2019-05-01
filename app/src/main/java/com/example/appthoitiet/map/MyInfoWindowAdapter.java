package com.example.appthoitiet.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appthoitiet.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//    private final View view;
    private Context context;

    public MyInfoWindowAdapter(Context context) {
        this.context = context;
//        view = LayoutInflater.from(context).inflate(R.layout.map_custom_infowindows, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.map_custom_infowindows, null);

//        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.map_custom_infowindows, null);
        ImageView imgThoiTiet = view.findViewById(R.id.imageViewThoiTiet);
        TextView txtDiaDiem = view.findViewById(R.id.textViewDiaDiem);
        TextView txtNhietDo = view.findViewById(R.id.textViewTemp);
        TextView txtTrangThai = view.findViewById(R.id.TextViewTrangThai);
        TextView txtNhietDoRe = view.findViewById(R.id.TextViewNhietDo);
        TextView txtDoAm = view.findViewById(R.id.TextViewDoAm);
        TextView txtSucGio = view.findViewById(R.id.TextViewSucGio);
        TextView txtMay = view.findViewById(R.id.TextViewMay);
        TextView txtTamNhin = view.findViewById(R.id.TextViewTamNhin);
        TextView txtMatTroiMoc = view.findViewById(R.id.TextViewMTMoc);
        TextView txtMatTroiLan = view.findViewById(R.id.TextViewMTLan);

        ThoiTiet thoiTiet = (ThoiTiet) marker.getTag();
        txtDiaDiem.setText(thoiTiet.getDiaDiem());
        txtNhietDo.setText(thoiTiet.getNhietDo() + "°C");
        txtNhietDoRe.setText(thoiTiet.getNhietDo() + "°C");
        txtDoAm.setText(thoiTiet.getDoAm() + " %");
        txtSucGio.setText(thoiTiet.getSucGio() + "m/s");
        txtMay.setText(thoiTiet.getTrangThaiMay() + " %");
        txtTamNhin.setText(thoiTiet.getTamNhin() + " m");
        txtMatTroiMoc.setText(thoiTiet.getMatTroiMoc());
        txtMatTroiLan.setText(thoiTiet.getMatTroiLan());


        switch (thoiTiet.getTrangThai()){
            case "Clouds":
                txtTrangThai.setText("Trời nhiều mây");
                imgThoiTiet.setImageResource(R.drawable.day_partial_cloud);
                break;
            case "Rain":
                txtTrangThai.setText("Trời mưa");
                imgThoiTiet.setImageResource(R.drawable.rain);
                break;
            case "Snow":
                txtTrangThai.setText("Có tuyết rơi");
                imgThoiTiet.setImageResource(R.drawable.snow);
                break;
            case "Haze":
                txtTrangThai.setText("Có sương mù");
                imgThoiTiet.setImageResource(R.drawable.fog);
                break;
            case "Clear":
                txtTrangThai.setText("Trời quang đãng");
                imgThoiTiet.setImageResource(R.drawable.day_clear);
                break;
            case "Mist":
                txtTrangThai.setText("Có sương muối");
                imgThoiTiet.setImageResource(R.drawable.mist);
                break;
            case "Drizzle":
                txtTrangThai.setText("Mưa phùn");
                imgThoiTiet.setImageResource(R.drawable.day_rain_thunder);
                break;
        }

        return view;
    }
}
