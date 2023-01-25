package com.diaco.majazi.Main;

import android.graphics.drawable.Drawable;

import java.util.List;

public class ListviewServices{
    int image;
    String ServerCode;
    String ServiceNumberCode;
    int BackgroundColor;

    public int getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getServiceNumberCode() {
        return ServiceNumberCode;
    }

    public void setServiceNumberCode(String serviceNumberCode) {
        ServiceNumberCode = serviceNumberCode;
    }

    public int getImage() {
        return image;
    }

    public String getServiceCode() {
        return ServerCode;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setServiceCode(String serviceCode) {
        this.ServerCode = serviceCode;
    }

    public ListviewServices(int image, String serviceCode,String numbercode,int backgroundColor) {
        this.image = image;
        this.ServerCode = serviceCode;
        ServiceNumberCode=numbercode;
        BackgroundColor=backgroundColor;
    }
}
