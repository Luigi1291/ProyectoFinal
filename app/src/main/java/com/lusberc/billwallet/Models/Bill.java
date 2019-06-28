package com.lusberc.billwallet.Models;

import android.location.Address;
import android.net.Uri;

import java.io.Serializable;

public class Bill implements Serializable{

    private String imageName;
    private String userID;
    private String imageText;
    private int Monto;
    private long x;
    private long y;
    private String fechaVencimiento;
    private Address mAdreess;

    public Address getmAdreess() {
        return mAdreess;
    }

    public void setmAdreess(Address mAdreess) {
        this.mAdreess = mAdreess;
    }

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

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
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
