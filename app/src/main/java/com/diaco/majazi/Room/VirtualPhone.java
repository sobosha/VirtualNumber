package com.diaco.majazi.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VirtualBuy")
public class VirtualPhone {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="virtualPhone")
    private String image;

    @ColumnInfo(name="Servicetype")
    private String type;

    @ColumnInfo(name="Country")
    private String country;

    @ColumnInfo(name="amount")
    private String amount;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
