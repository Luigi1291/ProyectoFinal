package com.lusberc.billwallet.Models;

import android.location.Address;
import android.net.Uri;

import java.io.Serializable;

public class Bill implements Serializable{

    private String imageName;
    private String userID;
    private String imageText;
    private int Monto;

    public Bill() {
    }

    private double x;
    private double y;
    private String fechaVencimiento;

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    private String Store;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public int getMonto() {
        return Monto;
    }

    public void setMonto(int monto) {
        Monto = monto;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
