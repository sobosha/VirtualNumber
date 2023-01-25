package com.diaco.majazi.Dialog;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Setting.CustomClasses.CustomRel;
import com.diaco.majazi.Setting.mAnimation;

public class Help extends CustomRel {
    TextView helpDesc;
    public Help(Context context) {
        super(context, R.layout.dialog_help);
        //helpDesc=findViewById(R.id.TextviewDesc);
        /*String Text="سرویس ها: \n شما می توانید شماره مجازی خود را از این بخش خریداری نمایید. \n لیست خرید: \n لیست خرید های قبلی شما در این قسمت نمایش داده می شود. \n شارژ حساب: \n با کلیک بر روی قسمت شارژ حساب (بالا راست) می توانید حساب خودتان را شارژ نمایید";
        helpDesc.setText(Text);*/
        findViewById(R.id.btn_cancle_dialog).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                MainActivity.getGlobal().HideMyDialog();
            }
        });
    }
}
