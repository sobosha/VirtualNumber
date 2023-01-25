package com.diaco.majazi;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.TimerEvent;

import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import static com.diaco.majazi.Setting.Setting.GetTimerString;

public class phone_Confirm extends CustomFragment {
    String number;
    TextView textView_phonesend;
    TextView textView_timer;
    TextView btn_next;
    TextView btn_resendCode;
    long TIME;
    TimerEvent timerEvent;
    final Handler handler = new Handler();

    @Override
    public int layout() {
        return R.layout.fragment_phone_confirm;
    }

    @Override
    public void onCreateMyView() {
        textView_phonesend=parent.findViewById(R.id.textview_PhonesendCode);
        textView_timer=parent.findViewById(R.id.textview_Timer);
        btn_next=parent.findViewById(R.id.btn_countinue_Confirm);
        btn_resendCode=parent.findViewById(R.id.btn_ResendCode);


        textView_phonesend.setText("پیامکی حاوی کد فعالسازی به شماره:"+number+" ارسال شد");
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().FinishFragStartFrag(new menu(true));
            }
        });
        timerEvent = new TimerEvent() {
            @Override
            public void onTick(String time) {
                textView_timer.setText(time);
            }
            @Override
            public void onFinish() {
                    textView_timer.setText("0");
                    btn_resendCode.setClickable(true);
                    btn_resendCode.setTextColor(Color.parseColor("#000000"));
                }
        };
        ShowTimer(5);




    }

    public phone_Confirm(String phone){
        number=phone;
    }

    public void ShowTimer(long sec) {
        TIME = sec;

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (TIME > 0) {
                    timerEvent.onTick(GetTimerString(TIME));
                    TIME--;
                    handler.postDelayed(this, 1000);
                } else
                    timerEvent.onFinish();
            }
        });
    }

    public static String GetTimerString(long sec) {
        sec = sec * 1000;
        String hour = "";

        if (TimeUnit.MILLISECONDS.toHours(sec) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(sec)) > 0)
            hour = TimeUnit.MILLISECONDS.toHours(sec) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(sec)) + ":";

        String min = "";
        if (TimeUnit.MILLISECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(sec)) > 0)
            min = (TimeUnit.MILLISECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(sec))) + ":";

        String secend = TimeUnit.MILLISECONDS.toSeconds(sec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sec)) + "";

        return hour + min + secend;
    }


}
