package com.diaco.majazi.Main;

import android.hardware.Camera;

public class ListViewCountry implements Comparable<ListViewCountry> {
    int image;
    int CountryCode;
    String name;
    String ServerCode;
    String AreaCode;
    String stats;
    String amount;

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getServerCode() {
        return ServerCode;
    }

    public void setServerCode(String serverCode) {
        ServerCode = serverCode;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(int countryCode) {
        CountryCode = countryCode;
    }

    public ListViewCountry(int image, int countryCode,String name,String serverCode,String amount,String stats,String AreaCode) {
        this.image = image;
        CountryCode = countryCode;
        this.name=name;
        ServerCode=serverCode;
        this.amount=amount;
        this.stats=stats;
        this.AreaCode= AreaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(ListViewCountry o) {
        if(Integer.parseInt(this.amount)>Integer.parseInt(o.amount)){
            return 1;
        }
        else if(Integer.parseInt(this.amount)<Integer.parseInt(o.amount)){
            return -1;
        }
        else {
            return 0;
        }
    }
}
