package com.diaco.majazi.Setting.CustomClasses;

import java.util.List;

public interface IAdapter <T , P> {
    void onBind (int position , List<T> list , P rel , int selectItem , CustomAdapter<T , P> customAdapter);
}
