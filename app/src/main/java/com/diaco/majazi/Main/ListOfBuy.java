package com.diaco.majazi.Main;

public class ListOfBuy {
    String Name,VirtualPhone,Cost;
    int ImageCountry,Service;

    public ListOfBuy( int imageCountry,String name, String virtualPhone, String cost, int service) {
        Name = name;
        VirtualPhone = virtualPhone;
        Cost = cost;
        ImageCountry = imageCountry;
        Service = service;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVirtualPhone() {
        return VirtualPhone;
    }

    public void setVirtualPhone(String virtualPhone) {
        VirtualPhone = virtualPhone;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public int getImageCountry() {
        return ImageCountry;
    }

    public void setImageCountry(int imageCountry) {
        ImageCountry = imageCountry;
    }

    public int getService() {
        return Service;
    }

    public void setService(int service) {
        Service = service;
    }
}
