package com.example.myapplication1.server.model;

public class ScheduleDTO {
    int id;
    String address;
    String date;
    String suggest;
    int id_user;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String address, String date, int id_user) {
        this.address = address;
        this.date = date;
        this.id_user = id_user;
    }

    public ScheduleDTO(int id, String address, String date) {
        this.id = id;
        this.address = address;
        this.date = date;
    }

    public ScheduleDTO(int id, String address, String date, int id_user) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.id_user = id_user;
    }

    public ScheduleDTO(int id, String address, String date, String suggest, int id_user) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.suggest = suggest;
        this.id_user = id_user;
    }

    public ScheduleDTO(String address, String date) {
        this.address = address;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
