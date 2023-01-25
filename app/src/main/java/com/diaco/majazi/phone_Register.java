package com.diaco.majazi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.mAnimation;

public class phone_Register extends CustomFragment {
    EditText enter_Phone;
    TextView alert;
    TextView btn;
    CheckBox check_Rule;
    boolean check_alert=false;
    @Override
    public int layout() {
        return R.layout.fragment_phone__register;
    }

    @Override
    public void onCreateMyView() {
        MainActivity.getGlobal().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        enter_Phone=parent.findViewById(R.id.editText_phone_enter);
        alert=parent.findViewById(R.id.textview_Alert);
        enter_Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alert.setVisibility(View.INVISIBLE);
                String temp=enter_Phone.getText().toString();
                if(temp.length()>2){
                    if(!temp.startsWith("09")){
                        alert.setText("شماره همراه باید با 09 شروع شود");
                        mAnimation.Viberation(enter_Phone,2);
                        alert.setVisibility(View.VISIBLE);
                        check_alert=true;
                    }

                    else check_alert=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        check_Rule=parent.findViewById(R.id.checkbox_rule);
        btn=parent.findViewById(R.id.btn_countinue_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_Rule.isChecked()){
                    if(!check_alert && enter_Phone.length()==11){
                        MainActivity.getGlobal().FinishFragStartFrag(new phone_Confirm(enter_Phone.getText().toString()));
                    }
                    else if(enter_Phone.length()<11){
                        alert.setText("شماره همراه باید یازده رقم باشد");
                        alert.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    mAnimation.Viberation(check_Rule,2);
                    if(enter_Phone.length()<11){
                        alert.setText("شماره همراه باید یازده رقم باشد");
                        alert.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }
}