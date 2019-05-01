package com.example.appthoitiet.map;

import java.io.Serializable;

public class ThoiTiet implements Serializable {
    private String diaDiem;
    private String thoiGian;
    private String trangThai;
    private String nhietDo;
    private String doAm;
    private String sucGio;
    private String trangThaiMay;
    private String matTroiMoc;
    private String matTroiLan;
    private String tamNhin;

    public ThoiTiet(String diaDiem, String thoiGian, String trangThai, String nhietDo, String doAm, String sucGio, String trangThaiMay, String matTroiMoc, String matTroiLan, String tamNhin) {
        this.diaDiem = diaDiem;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.nhietDo = nhietDo;
        this.doAm = doAm;
        this.sucGio = sucGio;
        this.trangThaiMay = trangThaiMay;
        this.matTroiMoc = matTroiMoc;
        this.matTroiLan = matTroiLan;
        this.tamNhin = tamNhin;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(String nhietDo) {
        this.nhietDo = nhietDo;
    }

    public String getDoAm() {
        return doAm;
    }

    public void setDoAm(String doAm) {
        this.doAm = doAm;
    }

    public String getSucGio() {
        return sucGio;
    }

    public void setSucGio(String sucGio) {
        this.sucGio = sucGio;
    }

    public String getTrangThaiMay() {
        return trangThaiMay;
    }

    public void setTrangThaiMay(String trangThaiMay) {
        this.trangThaiMay = trangThaiMay;
    }

    public String getMatTroiMoc() {
        return matTroiMoc;
    }

    public void setMatTroiMoc(String matTroiMoc) {
        this.matTroiMoc = matTroiMoc;
    }

    public String getMatTroiLan() {
        return matTroiLan;
    }

    public void setMatTroiLan(String matTroiLan) {
        this.matTroiLan = matTroiLan;
    }

    public String getTamNhin() {
        return tamNhin;
    }

    public void setTamNhin(String tamNhin) {
        this.tamNhin = tamNhin;
    }

    public ThoiTiet(String thoiGian, String trangThai, String nhietDo, String doAm, String sucGio, String trangThaiMay, String matTroiMoc, String matTroiLan, String tamNhin) {
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.nhietDo = nhietDo;
        this.doAm = doAm;
        this.sucGio = sucGio;
        this.trangThaiMay = trangThaiMay;
        this.matTroiMoc = matTroiMoc;
        this.matTroiLan = matTroiLan;
        this.tamNhin = tamNhin;
    }
}
