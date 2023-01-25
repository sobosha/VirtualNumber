package com.diaco.majazi.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VirtualDao {
    @Query("SELECT * FROM VirtualBuy")
    List<VirtualPhone> getAllBuy();

    @Insert
    void insertAll(VirtualPhone... virtualPhones);

}
