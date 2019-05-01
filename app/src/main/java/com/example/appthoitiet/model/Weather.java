package com.example.appthoitiet.model;

public class Weather {
    public String date;
    public String status;
    public String imgicon;
    public String tempMin;
    public String tempMax;

    public Weather(String ngayThang, String trangThai, String imgicon, String tempMin, String tempMax) {
        this.date = ngayThang;
        this.status = trangThai;
        this.imgicon = imgicon;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }
}
