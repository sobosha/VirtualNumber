package com.diaco.majazi.Setting;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diaco.majazi.R;

public class CustomSnackBar extends RelativeLayout {
    String type, desc;

    public CustomSnackBar(Context context, String type, String desc) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rel_custom_snack_bar, this, true);
        this.type = type;
        this.desc = desc;
        if(type.equals("accept") || type.equals("pending")  || type.equals("reject") || type.equals("warning") || type.equals("normal"))
          onStart();
        new Setting().setTypeFace(findViewById(R.id.txtDesc));
    }

    private void onStart() {
        switch (type) {
            case "accept":
                setColors(desc, mLocalData.getDarkThemeStatus(getContext()) ? "#ffffff" : "#17bd79",
                        R.drawable.happy, mLocalData.getDarkThemeStatus(getContext()) ? R.drawable.shape_green_snack : R.drawable.shape_best,
                        mLocalData.getDarkThemeStatus(getContext()) ? R.color.colorWhite : R.color.colorLightGreen,true);
                break;
            case "pending":
                setColors(desc, mLocalData.getDarkThemeStatus(getContext()) ? "#ffffff" : "#0671f2",
                        R.drawable.emoji_relx, mLocalData.getDarkThemeStatus(getContext()) ? R.drawable.shape_blue_snack : R.drawable.shape_survay,
                        mLocalData.getDarkThemeStatus(getContext()) ? R.color.colorWhite : R.color.newBlue,true);
                break;
            case "reject":
                setColors(desc, mLocalData.getDarkThemeStatus(getContext()) ? "#ffffff" : "#f60057",
                        R.drawable.sad, mLocalData.getDarkThemeStatus(getContext()) ? R.drawable.shape_red_snack : R.drawable.shape_disapproval,
                        mLocalData.getDarkThemeStatus(getContext()) ? R.color.colorWhite : R.color.newRed,true);
                break;
            case "warning":
                setColors(desc, mLocalData.getDarkThemeStatus(getContext()) ? "#ffb726" : "#ffb726",
                        R.drawable.ic_alarm, R.drawable.shape_warning, R.color.newyellow,true);
                break;
            case "normal":
                setColors(desc, mLocalData.getDarkThemeStatus(getContext()) ? "#ffffff" : "#ffffff",
                        R.drawable.zang, R.drawable.shape_normal,R.color.newyellow,false);
                break;
        }
        findViewById(R.id.linBackTop).setVisibility(GONE);
    }

    private void setColors(String desc, String lineColor, int imgImojee, int icon, int changeColor,boolean needChangeColor) {
        ((TextView) findViewById(R.id.txtDesc)).setText(desc);
        ((TextView) findViewById(R.id.txtDesc)).setTextColor(Color.parseColor(lineColor));
        ((ImageView) findViewById(R.id.imojii)).setImageResource(imgImojee);
        findViewById(R.id.line).setBackgroundColor(Color.parseColor(lineColor));
        findViewById(R.id.linBack).setBackgroundResource(icon);
        if (needChangeColor)
        new Setting().changeSvgColor(getContext(), findViewById(R.id.imojii), changeColor);
    }
}
