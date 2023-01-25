package com.diaco.majazi.Dialog;

import android.content.Context;
import android.view.View;

import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Setting.CustomClasses.CustomRel;

public class Exit extends CustomRel {

    public Exit(Context context) {
        super(context, R.layout.dialog_exit);
        findViewById(R.id.btn_ok_dialog).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().finish();
            }
        });
        findViewById(R.id.btn_cancle_dialog).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().HideMyDialog();
            }
        });
    }
}
