package com.diaco.majazi.Setting.CustomClasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.diaco.majazi.Setting.mFragment;

import java.text.ParseException;


public abstract class CustomFragment extends mFragment {
    public View parent ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = inflater.inflate(layout() , container,  false);
        try {
            onCreateMyView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parent ;
    }
    public abstract int layout ();

    public abstract void onCreateMyView () throws ParseException;
}
