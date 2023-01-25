package com.diaco.majazi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.majazi.Core.DataModelResponse;
import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.MarketResult;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.Core.bzResponce;
import com.diaco.majazi.Core.shopResult2;
import com.diaco.majazi.Core.shop_item;
import com.diaco.majazi.Core.shoping;
import com.diaco.majazi.Dialog.Exit;
import com.diaco.majazi.Dialog.Help;
import com.diaco.majazi.JsonClass.Token;
import com.diaco.majazi.JsonClass.getCountryResult;
import com.diaco.majazi.JsonClass.getNumberResult;
import com.diaco.majazi.Main.FragServices;
import com.diaco.majazi.Main.FragmentListBuy;
import com.diaco.majazi.Main.IAfterSucc;
import com.diaco.majazi.Main.ListviewServices;
import com.diaco.majazi.Main.Main.APIResult;
import com.diaco.majazi.Main.Main.RelItemService;
import com.diaco.majazi.Setting.CustomClasses.CustomAdapter;
import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.DateMiladi;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.SplitText;
import com.diaco.majazi.Setting.TimerEvent;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mLocalData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class menu extends CustomFragment {
    int otherpage=0;
    Context context;
    RelativeLayout rel_choice_money;
    RelativeLayout rel_money_cardview;
    RelativeLayout rel_choice_mainmenu;
    RelativeLayout btn_services,btn_ListBuy,BtnHelp;
    TextView afzayeshmojodi;
    TextView textview_above_cardview;
    TextView shomaremajazi;
    RelativeLayout virtualPhone,ReciveCode;
    TextView minutes,TextReciveCode;
    RelativeLayout plus_money_carview;
    TextView text_VirtualPhone,BtnShop,WarningCost,Money,T10000,T50000,T20000;
    APIResult result;
    RelativeLayout btn_rules,ServiceType;
    ImageView ServiceTypeImage;
    RelativeLayout Toman50000;
    RelativeLayout Toman10000;
    RelativeLayout Toman20000;
    EditText AddCoin;
    ImageView PlusCost,MinusCost;
    ProgressBar progressBarReciveCode;
    TextView TextToman10000,TextToman20000,TextToman50000;
    boolean flag_wallet=false;
    long TIME;
    TimerEvent timerEvent;
    final Handler handler = new Handler();

    @Override
    public int layout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void onCreateMyView() {
        context=getContext();
        Toman50000=parent.findViewById(R.id.cardview_right_50000);
        Toman20000=parent.findViewById(R.id.cardview_right_20000);
        Toman10000=parent.findViewById(R.id.cardview_right_10000);
        btn_rules=parent.findViewById(R.id.relative_rules_click);
        btn_services=parent.findViewById(R.id.ralative_btn_services);
        AddCoin=parent.findViewById(R.id.EditTextAddCoin);
        PlusCost=parent.findViewById(R.id.plusCost);
        MinusCost=parent.findViewById(R.id.minusCost);
        BtnShop=parent.findViewById(R.id.Shop);
        ServiceType=parent.findViewById(R.id.carview_TypeService);
        WarningCost=parent.findViewById(R.id.WarningCost);
        AddCoin.setText("5000");
        ServiceTypeImage=parent.findViewById(R.id.imageView_ServiceType);
        Money=parent.findViewById(R.id.textview_Money);
        T10000=parent.findViewById(R.id.T10000);
        T20000=parent.findViewById(R.id.T20000);
        T50000=parent.findViewById(R.id.T50000);
        TextToman10000=parent.findViewById(R.id.TextToman10000);
        TextToman50000=parent.findViewById(R.id.TextToman50000);
        TextToman20000=parent.findViewById(R.id.TextToman20000);
        T10000.setText(SplitText.GetOKPrice(T10000.getText().toString()));
        T20000.setText(SplitText.GetOKPrice(T20000.getText().toString()));
        T50000.setText(SplitText.GetOKPrice(T50000.getText().toString()));
        AddCoin.setText(SplitText.GetOKPrice(AddCoin.getText().toString()));
        btn_ListBuy=parent.findViewById(R.id.BtnListBuy);
        BtnHelp=parent.findViewById(R.id.Help);
        ReciveCode=parent.findViewById(R.id.ReciveCode);
        TextReciveCode=parent.findViewById(R.id.TextReciveCode);
        progressBarReciveCode=parent.findViewById(R.id.ProgressReciveCode);
        btn_ListBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if(new Setting().isNetworkConnect()) {
                    MainActivity.getGlobal().FinishFragStartFrag(new FragmentListBuy());
                }else{
                    MainActivity.getGlobal().showSnackBar("warning","اینترنت خود را متصل کنید",1000);
                }
            }
        });
        BtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                MainActivity.getGlobal().FinishRelStartRel(new Help(getContext()));
            }
        });
        mLocalData.setAvatarTallar(getContext(),": ✅ گزارش #خرید #موفق\n" +
                "    ⏰ در تاریخ یازده آبان و  در ساعت ۱۴:۵۵:۰۰\n" +
                "    \n" +
                "    ℹ️ نحوه خرید شماره مجازی در ربات :\n" +
                "    \n" +
                "    1️⃣ وارد ربات @shomare_maajaazii_bot شوید .\n" +
                "    2️⃣ اعتبار خود را به مبلغ 16050 افزایش دهید .\n" +
                "    3️⃣ وارد بخش خرید شماره مجازی شوید و شماره کشور 'اکراین' دریافت کنید .\n" +
                "    \n" +
                "    ⤵️ اطلاعات خرید انجام شده&✅ کد با موفقیت دریافت شد\n" +
                "    \uD83D\uDCAD کد ورود شما به برنامه : 943335\n" +
                "    \n" +
                "    \uD83D\uDCB0 موجودی جدید حساب شما : 22200 تومان\n" +
                "    \uD83D\uDECD با تشکر از خرید شما ! گزارش خرید به کانال ما @shomare_maajaazii ارسال شد .\n" +
                "    \uD83D\uDC6E\uD83C\uDFFB درصورت وجود هرگونه مشکل کافیست با پشتیبانی در تماس باشید");
        try {
            if(!mLocalData.getReciveCode(getContext()).equals("")){
                TextReciveCode.setText(mLocalData.getReciveCode(getContext()));
                ReciveCode.setClickable(false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReciveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new Setting().isNetworkConnect()){
                    progressBarReciveCode.setVisibility(View.VISIBLE);
                    TextReciveCode.setVisibility(View.GONE);
                    Presenter.get_global().PostAction(new IView<getCountryResult>() {
                        @Override
                        public void SendRequest() {

                        }

                        @Override
                        public void OnSucceed(getCountryResult object) {
                            progressBarReciveCode.setVisibility(View.GONE);
                            TextReciveCode.setVisibility(View.VISIBLE);
                            String temp = object.getText();
                            //temp=mLocalData.getAvatarTallar(getContext());
                            String temp2=object.getText();//=mLocalData.getAvatarTallar(getContext());
                            Log.e("Code Reciver",object.getText());
                            if(temp.contains("هنوز ارسال نشده است")){
                                MainActivity.getGlobal().showSnackBar("warning","کد فعالسازی دریافت نشده است",1000);
                            }
                            else if(temp.contains("کد ورود شما")){
                                temp=temp.substring(temp.indexOf("به برنامه : "),temp.indexOf("موجودی جدید"));
                                temp=temp.replaceAll("به برنامه : ","");
                                temp=temp.replaceAll("\n","");
                                temp=temp.replaceAll(" ","");
                                temp=temp.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]","");
                                TextReciveCode.setText(temp);
                                ReciveCode.setClickable(false);
                                temp2=temp2.substring(temp2.indexOf("حساب شما : "),temp2.indexOf("تومان"));
                                temp2=temp2.replaceAll("حساب شما : ","");
                                temp2=temp2.replaceAll(" ","");
                                try {
                                    mLocalData.setCoin(getContext(),Integer.parseInt(temp2));
                                    mLocalData.setReciveCode(getContext(),temp);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Money.setText(SplitText.GetOKPrice(temp2));
                                Log.e("tagReccive",temp2);
                            }

                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            progressBarReciveCode.setVisibility(View.GONE);
                            TextReciveCode.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDCAC دریافت کد"), getCountryResult.class);

                }
                else{
                    MainActivity.getGlobal().showSnackBar("warning","اینترنت خود را متصل کنید",1000);
                }
            }
        });

        if(new Setting().isNetworkConnect()){
            if(!mLocalData.getToken(getContext()).equals("")) {
                Presenter.get_global().PostAction(new IView<getNumberResult>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(getNumberResult object) {
                        String text = object.getText();
                        //موجودی شما : 50000 تومان
                        //\n🆔 شناسه : 5430112
                        String UserId = text.substring(text.indexOf("نام :"), text.indexOf("یوزرنیم شما "));
                        UserId = UserId.replaceAll("شناسه : ", "");
                        UserId = UserId.replaceAll("نام : ", "");
                        UserId = UserId.replaceAll("\n", "");
                        UserId = UserId.replaceAll("\uD83C\uDD94", "");
                        UserId = UserId.replaceAll("\uD83D\uDCA1", "");
                        UserId = UserId.replaceAll(" ", "");
                        MainActivity.getGlobal().UserId = UserId;
                        text = text.substring(text.indexOf("موجودی شما : "), text.indexOf(" تومان"));
                        text = text.replaceAll("موجودی شما : ", "");
                        Money.setText(SplitText.GetOKPrice(text));
                        try {
                            mLocalData.setCoin(getContext(), Integer.parseInt(text));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                    }
                }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);
            }
            else{
                Presenter.get_global().GetAction(new IView<Token>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(Token object) {
                        Toast.makeText(MainActivity.getGlobal(), object.getToken(), Toast.LENGTH_SHORT).show();
                        mLocalData.SetToken(MainActivity.getGlobal(),object.getToken());
                        Log.d("Token",object.getToken());
                        Presenter.get_global().PostAction(new IView<getNumberResult>() {
                            @Override
                            public void SendRequest() {

                            }

                            @Override
                            public void OnSucceed(getNumberResult object) {
                                String text = object.getText();
                                //موجودی شما : 50000 تومان
                                //\n🆔 شناسه : 5430112
                                String UserId = text.substring(text.indexOf("نام :"), text.indexOf("یوزرنیم شما "));
                                UserId = UserId.replaceAll("شناسه : ", "");
                                UserId = UserId.replaceAll("نام : ", "");
                                UserId = UserId.replaceAll("\n", "");
                                UserId = UserId.replaceAll("\uD83C\uDD94", "");
                                UserId = UserId.replaceAll("\uD83D\uDCA1", "");
                                UserId = UserId.replaceAll(" ", "");
                                MainActivity.getGlobal().UserId = UserId;
                                text = text.substring(text.indexOf("موجودی شما : "), text.indexOf(" تومان"));
                                text = text.replaceAll("موجودی شما : ", "");
                                Money.setText(SplitText.GetOKPrice(text));
                                try {
                                    mLocalData.setCoin(getContext(), Integer.parseInt(text));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void OnError(String error, int statusCode) {
                                Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                            }
                        }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);



                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), "not Token", Toast.LENGTH_SHORT).show();
                    }
                },"","","",Token.class);
            }

        }
        else{
            try {
                Money.setText(SplitText.GetOKPrice(mLocalData.getCoin(getContext())+""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!mLocalData.isFirstReminder(context)) {
            RelativeLayout relDialog = parent.findViewById(R.id.relMenuDialog);
            relDialog.addView(new Rule_dialog(getContext(), R.layout.dialog_confirm_rules));
            relDialog.setVisibility(View.VISIBLE);
            TextView btn_ok=parent.findViewById(R.id.btn_ok_dialog);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnimation.PressClick(v);
                    relDialog.removeAllViews();
                    mLocalData.setFirstReminder(context,true);
                }
            });
        }
        text_VirtualPhone=parent.findViewById(R.id.textview_Virtualphone);
        BtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if(Integer.parseInt(AddCoin.getText().toString().replace(",",""))<5000){
                    mAnimation.Viberation(WarningCost);
                }
                else{
                    if(new Setting().isNetworkConnect()){
                        if(mLocalData.getToken(MainActivity.getGlobal()).equals("")) {
                            Presenter.get_global().GetAction(new IView<Token>() {
                                @Override
                                public void SendRequest() {

                                }

                                @Override
                                public void OnSucceed(Token object) {
                                    mLocalData.SetToken(MainActivity.getGlobal(), object.getToken());
                                    CostPay(AddCoin.getText().toString());

                                }

                                @Override
                                public void OnError(String error, int statusCode) {

                                }
                            }, "", "", "", Token.class);
                        }
                        else{
                            CostPay(AddCoin.getText().toString());
                        }
                    }
                    else{
                        MainActivity.getGlobal().showSnackBar("warning","اینترنت خود را متصل کنید",1000);
                    }
                }
            }
        });

        plus_money_carview=parent.findViewById(R.id.btn_Plus_money);
        plus_money_carview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                btn_ShowWallet();

            }
        });
        minutes=parent.findViewById(R.id.textview_minute);
        btn_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Page 0=Rules 1=Services
                mAnimation.PressClick(v);
                MainActivity.getGlobal().FinishFragStartFrag(new FragServices(0,"",""));
            }
        });
        btn_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Page 0=Rules 1=Services
                mAnimation.PressClick(v);
                if(new Setting().isNetworkConnect()) {
                    MainActivity.getGlobal().FinishFragStartFrag(new FragServices(1, "",""));
                }
                else{
                    MainActivity.getGlobal().showSnackBar("warning","اینترنت خود را متصل کنید ",1000);
                }
            }
        });
        CheckVirtualExiste();
        Toman50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                TomanHandler(50);
                CostPlus(50000,true);
            }
        });
        Toman20000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                TomanHandler(20);
                CostPlus(20000,true);
            }
        });
        Toman10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                TomanHandler(10);
                CostPlus(10000,true);
            }
        });
        if(otherpage==1){
            btn_ShowWalletFromotherFrag();
        }

    }

    private void TomanHandler(int i) {
        if(i==50){
            T50000.setTextColor(Color.WHITE);
            T20000.setTextColor(Color.BLACK);
            T10000.setTextColor(Color.BLACK);
            TextToman10000.setTextColor(Color.BLACK);
            TextToman20000.setTextColor(Color.BLACK);
            TextToman50000.setTextColor(Color.WHITE);
            Toman50000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_blue);
            Toman10000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
            Toman20000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
        }
        else if(i==20){
            T20000.setTextColor(Color.WHITE);
            T50000.setTextColor(Color.BLACK);
            T10000.setTextColor(Color.BLACK);
            TextToman10000.setTextColor(Color.BLACK);
            TextToman50000.setTextColor(Color.BLACK);
            TextToman20000.setTextColor(Color.WHITE);
            Toman20000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_blue);
            Toman50000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
            Toman10000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
        }
        else if(i==10){
            T10000.setTextColor(Color.WHITE);
            T20000.setTextColor(Color.BLACK);
            T50000.setTextColor(Color.BLACK);
            TextToman50000.setTextColor(Color.BLACK);
            TextToman20000.setTextColor(Color.BLACK);
            TextToman10000.setTextColor(Color.WHITE);
            Toman10000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_blue);
            Toman50000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
            Toman20000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
        }

    }


    private void btn_ShowWallet(){
        plus_money_carview.setClickable(false);
        flag_wallet=true;
        rel_choice_money=parent.findViewById(R.id.rel_choice_money);
        rel_money_cardview=parent.findViewById(R.id.rel_money_cardview);
        afzayeshmojodi=parent.findViewById(R.id.textview_afzayeshmojodi);

        rel_choice_mainmenu=parent.findViewById(R.id.rel_choice_mainmenu_rules);
        textview_above_cardview=parent.findViewById(R.id.textview_above_cardview_15min);
        shomaremajazi=parent.findViewById(R.id.textview_above_cardview_shomaremajazi);
        virtualPhone=parent.findViewById(R.id.carview_VirtualPhone);
        minutes=parent.findViewById(R.id.textview_minute);
        AddCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /*if(AddCoin.getText().toString().equals("")){
                    AddCoin.setText("5000");
                }*/
                if(!AddCoin.getText().toString().equals("")) {
                    if (AddCoin.getText().toString().charAt(0) == '0') {
                        AddCoin.setText("");
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                AddCoin.setSelection(AddCoin.getText().toString().length());
                //AddCoin.setText(SplitText.GetOKPrice(AddCoin.getText().toString()));
                if(!AddCoin.getText().toString().equals(T10000.getText().toString()) && !AddCoin.getText().toString().equals(T50000.getText().toString()) && !AddCoin.getText().toString().equals(T20000.getText().toString())){
                    T50000.setTextColor(Color.BLACK);
                    T20000.setTextColor(Color.BLACK);
                    T10000.setTextColor(Color.BLACK);
                    TextToman10000.setTextColor(Color.BLACK);
                    TextToman20000.setTextColor(Color.BLACK);
                    TextToman50000.setTextColor(Color.BLACK);
                    Toman50000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                    Toman10000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                    Toman20000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                }
            }
        });
        PlusCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                CostPlus(1000,false);
            }
        });
        MinusCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if(Integer.parseInt(AddCoin.getText().toString().replace(",",""))>5000)
                CostPlus(-1000,false);
            }
        });
        //Animation Gone
        mAnimation.myTrans_ToBottom(rel_choice_mainmenu,rel_choice_mainmenu.getHeight(),1500,0,2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rel_choice_mainmenu.clearAnimation();
                rel_choice_mainmenu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(textview_above_cardview,0,1000,0,-3).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textview_above_cardview.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(shomaremajazi,0,1000,0,-3).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shomaremajazi.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(MainActivity.getGlobal().isFlag_ExistVirtualphone()) {
            mAnimation.myTrans_ToTop2(virtualPhone, 0, 1000, 0, -3).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    virtualPhone.clearAnimation();
                    virtualPhone.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mAnimation.myTrans_ToTop2(ReciveCode, 0, 1000, 0, -3).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ReciveCode.clearAnimation();
                    ReciveCode.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mAnimation.myTrans_ToTop2(ServiceType, 0, 1000, 0, -3).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ServiceType.clearAnimation();
                    ServiceType.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mAnimation.myTrans_ToTop2(minutes, 0, 1000, 0, -3).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    minutes.clearAnimation();
                    minutes.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


        //Animation Visible
        mAnimation.myTrans_ToTop2(rel_choice_money,0,1200,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rel_choice_money.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(afzayeshmojodi,0,1200,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                afzayeshmojodi.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToBottom(rel_money_cardview,0,1400,3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rel_money_cardview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void btn_ShowWalletFromotherFrag(){
        plus_money_carview.setClickable(false);
        flag_wallet=true;
        rel_choice_money=parent.findViewById(R.id.rel_choice_money);
        rel_money_cardview=parent.findViewById(R.id.rel_money_cardview);
        afzayeshmojodi=parent.findViewById(R.id.textview_afzayeshmojodi);

        rel_choice_mainmenu=parent.findViewById(R.id.rel_choice_mainmenu_rules);
        textview_above_cardview=parent.findViewById(R.id.textview_above_cardview_15min);
        shomaremajazi=parent.findViewById(R.id.textview_above_cardview_shomaremajazi);
        virtualPhone=parent.findViewById(R.id.carview_VirtualPhone);
        minutes=parent.findViewById(R.id.textview_minute);
        rel_choice_mainmenu.setVisibility(View.GONE);
        textview_above_cardview.setVisibility(View.GONE);
        shomaremajazi.setVisibility(View.GONE);
        virtualPhone.setVisibility(View.GONE);
        ReciveCode.setVisibility(View.GONE);
        ServiceType.setVisibility(View.GONE);
        minutes.setVisibility(View.GONE);
        AddCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /*if(AddCoin.getText().toString().equals("")){
                    AddCoin.setText("5000");
                }*/
                if(!AddCoin.getText().toString().equals("")) {
                    if (AddCoin.getText().toString().charAt(0) == '0') {
                        AddCoin.setText("");
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                AddCoin.setSelection(AddCoin.getText().toString().length());
                //AddCoin.setText(SplitText.GetOKPrice(AddCoin.getText().toString()));
                if(!AddCoin.getText().toString().equals(T10000.getText().toString()) && !AddCoin.getText().toString().equals(T50000.getText().toString()) && !AddCoin.getText().toString().equals(T20000.getText().toString())){
                    T50000.setTextColor(Color.BLACK);
                    T20000.setTextColor(Color.BLACK);
                    T10000.setTextColor(Color.BLACK);
                    TextToman10000.setTextColor(Color.BLACK);
                    TextToman20000.setTextColor(Color.BLACK);
                    TextToman50000.setTextColor(Color.BLACK);
                    Toman50000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                    Toman10000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                    Toman20000.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi);
                }
            }
        });
        PlusCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                CostPlus(1000,false);
            }
        });
        MinusCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if(Integer.parseInt(AddCoin.getText().toString().replace(",",""))>5000)
                    CostPlus(-1000,false);
            }
        });
        //Animation Gone

        //Animation Visible
        mAnimation.myTrans_ToTop2(rel_choice_money,0,1200,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rel_choice_money.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(afzayeshmojodi,0,1200,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                afzayeshmojodi.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToBottom(rel_money_cardview,0,1400,3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rel_money_cardview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void btn_ShowMenu(){
        rel_choice_money=parent.findViewById(R.id.rel_choice_money);
        rel_money_cardview=parent.findViewById(R.id.rel_money_cardview);
        afzayeshmojodi=parent.findViewById(R.id.textview_afzayeshmojodi);

        rel_choice_mainmenu=parent.findViewById(R.id.rel_choice_mainmenu_rules);
        textview_above_cardview=parent.findViewById(R.id.textview_above_cardview_15min);
        shomaremajazi=parent.findViewById(R.id.textview_above_cardview_shomaremajazi);
        virtualPhone=parent.findViewById(R.id.carview_VirtualPhone);
        minutes=parent.findViewById(R.id.textview_minute);

        //Animation Gone
        mAnimation.myTrans_ToBottom(rel_choice_mainmenu,rel_choice_mainmenu.getHeight(),1500,2,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rel_choice_mainmenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(textview_above_cardview,0,1000,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textview_above_cardview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(shomaremajazi,0,1000,-3,0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                shomaremajazi.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(virtualPhone, 0, 1000, -3, 0).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (MainActivity.getGlobal().isFlag_ExistVirtualphone()) {

                        virtualPhone.setVisibility(View.VISIBLE);

                    }
                    else{
                        virtualPhone.clearAnimation();

                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        mAnimation.myTrans_ToTop2(ReciveCode, 0, 1000, -3, 0).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (MainActivity.getGlobal().isFlag_ExistVirtualphone()) {
                        ReciveCode.setVisibility(View.VISIBLE);

                    }
                    else{
                        ReciveCode.clearAnimation();

                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        mAnimation.myTrans_ToTop2(ServiceType, 0, 1000, -3, 0).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                    if (MainActivity.getGlobal().isFlag_ExistVirtualphone()) {
                        ServiceType.setVisibility(View.VISIBLE);
                    }
                    else{
                        ServiceType.clearAnimation();

                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        mAnimation.myTrans_ToTop2(minutes, 0, 1000, -3, 0).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (MainActivity.getGlobal().isFlag_ExistVirtualphone()) {
                        minutes.setVisibility(View.VISIBLE);
                    }
                    else{
                        minutes.clearAnimation();
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });



        //Animation Visible
        mAnimation.myTrans_ToTop2(rel_choice_money,0,1200,0,-3).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rel_choice_money.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToTop2(afzayeshmojodi,0,1200,0,-3).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                afzayeshmojodi.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.myTrans_ToBottom(rel_money_cardview,0,1400,0,2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rel_money_cardview.clearAnimation();
                rel_money_cardview.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(flag_wallet) {
            btn_ShowMenu();
            flag_wallet = false;
            plus_money_carview.setClickable(true);

        }
        else{
            MainActivity.getGlobal().FinishRelStartRel(new Exit(getContext()));
        }

    }

    boolean firstlogin;
    public menu(boolean firstlogin) {
        //this.firstlogin = firstlogin;
    }
    public menu(int otherpage) {
        this.otherpage = otherpage;
    }

    public void setResult(APIResult result) {
        this.result = result;
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

    public void ShowTimer(long sec) {
        TIME = sec;
        showServiceType();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (TIME > 0) {
                    timerEvent.onTick(GetTimerString(TIME));
                    TIME--;
                    handler.postDelayed(this, 1000);
                } else {
                    timerEvent.onFinish();
                    MainActivity.getGlobal().setFlag_ExistVirtualphone(false);
                    existVirtualPhone();
                }
                if(TIME==1){
                    MainActivity.getGlobal().setFlag_ExistVirtualphone(false);
                }
            }
        });
    }

    private void showServiceType() {
        try {
            if(mLocalData.getServicesType(getContext()).contains("تلگرام")){
                ServiceTypeImage.setImageResource(R.drawable.ic_telegram_2);
            }
            else if(mLocalData.getServicesType(getContext()).contains("اینستا")){
                ServiceTypeImage.setImageResource(R.drawable.ic_instagram_5);
            }
            else if(mLocalData.getServicesType(getContext()).contains("واتساپ")){
                ServiceTypeImage.setImageResource(R.drawable.ic_whatsapp);
            }
            else if(mLocalData.getServicesType(getContext()).contains("گوگل")){
                ServiceTypeImage.setImageResource(R.drawable.ic_google);
            }
            else if(mLocalData.getServicesType(getContext()).contains("وایبر")){
                ServiceTypeImage.setImageResource(R.drawable.ic_viber);
            }
            else if(mLocalData.getServicesType(getContext()).contains("ویچت")){
                ServiceTypeImage.setImageResource(R.drawable.ic_wechat);
            }
            else if(mLocalData.getServicesType(getContext()).contains("فیسبوک")){
                ServiceTypeImage.setImageResource(R.drawable.ic_facebook);
            }
            else if(mLocalData.getServicesType(getContext()).contains("توییتر")){
                ServiceTypeImage.setImageResource(R.drawable.ic_twitter_1);
            }
            else if(mLocalData.getServicesType(getContext()).contains("یاهو")){
                ServiceTypeImage.setImageResource(R.drawable.ic_yahoo_1);
            }
            else if(mLocalData.getServicesType(getContext()).contains("ایمو")){
                ServiceTypeImage.setImageResource(R.drawable.ic_imo);
            }
            else if(mLocalData.getServicesType(getContext()).contains("پیپال")){
                ServiceTypeImage.setImageResource(R.drawable.ic_paypal);
            }
            else if(mLocalData.getServicesType(getContext()).contains("لاین")){
                ServiceTypeImage.setImageResource(R.drawable.ic_line);
            }
            else if(mLocalData.getServicesType(getContext()).contains("ماکروسافت")){
                ServiceTypeImage.setImageResource(R.drawable.ic_microsoft);
            }
            else if(mLocalData.getServicesType(getContext()).contains("آمازون")){
                ServiceTypeImage.setImageResource(R.drawable.ic_amazon_icon_1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void CostPlus(int cost,boolean isReplace){
        if(!isReplace) {
            int temp = Integer.parseInt(AddCoin.getText().toString().replace(",",""));
            AddCoin.setText(SplitText.GetOKPrice((temp + cost)+""));
        }
        else{
            AddCoin.setText(SplitText.GetOKPrice(cost+""));
        }
    }

    public void existVirtualPhone(){
        virtualPhone=parent.findViewById(R.id.carview_VirtualPhone);
        if(MainActivity.getGlobal().isFlag_ExistVirtualphone()){
            virtualPhone.setVisibility(View.VISIBLE);
            ServiceType.setVisibility(View.VISIBLE);
            minutes.setVisibility(View.VISIBLE);
            ReciveCode.setVisibility(View.VISIBLE);

        }
        else{
            minutes.setVisibility(View.GONE);
            virtualPhone.setVisibility(View.GONE);
            ServiceType.setVisibility(View.GONE);
            ReciveCode.setVisibility(View.GONE);
        }
    }



    public void CheckVirtualExiste() {
        String timeStamp = null;
        String timeReverse=null;
        try {
            timeStamp = mLocalData.getTimeVirtualPhone(getContext());
            timeReverse=mLocalData.getTimeReverse(getContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffDay;
        if (!timeStamp.equals("")) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
            Date date = new Date();

            try {
                Date date1 = formatter.parse(formatter.format(date));
                Date date2=formatter.parse(timeStamp);
                diffDay = new DateMiladi().printDifferenceFormatMilliAbs(date1, date2);
                String s[]=timeReverse.split(":");
                if(Long.parseLong(s[1])*60*1000>diffDay){
                    MainActivity.getGlobal().setFlag_ExistVirtualphone(true);
                    timerEvent=new TimerEvent() {
                        @Override
                        public void onTick(String time) {
                            minutes.setText(time);
                        }

                        @Override
                        public void onFinish() {
                            MainActivity.getGlobal().setFlag_ExistVirtualphone(false);
                            existVirtualPhone();
                        }
                    };
                    ShowTimer((Long.parseLong(s[1])*60*1000-diffDay)/1000);
                    text_VirtualPhone.setText(mLocalData.getVirtualPhone(getContext()));
                    existVirtualPhone();
                }
                else{
                    MainActivity.getGlobal().setFlag_ExistVirtualphone(false);
                    existVirtualPhone();
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    public void CostPay(String cost){
        if(!MainActivity.getGlobal().UserId.equals("")) {
            String url = "https://diacoipj.com/shomarehMajazi/pay?amount=" + cost.replaceAll(",","") + "&id=" + MainActivity.getGlobal().UserId + "&tapp=1";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            MainActivity.getGlobal().startActivity(i);
        }
        else{
            if(new Setting().isNetworkConnect()){
                Presenter.get_global().PostAction(new IView<getNumberResult>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(getNumberResult object) {
                        String text=object.getText();
                        //موجودی شما : 50000 تومان
                        String UserId=text.substring(text.indexOf("نام :"),text.indexOf("یوزرنیم شما "));
                        UserId=UserId.replaceAll("شناسه : ","");
                        UserId=UserId.replaceAll("نام : ","");
                        UserId=UserId.replaceAll("\n","");
                        UserId=UserId.replaceAll("\uD83C\uDD94","");
                        UserId=UserId.replaceAll("\uD83D\uDCA1","");
                        UserId=UserId.replaceAll(" ","");

                        MainActivity.getGlobal().UserId=UserId;
                        text=text.substring(text.indexOf("موجودی شما : "),text.indexOf(" تومان"));
                        text=text.replaceAll("موجودی شما : ","");
                        Money.setText(SplitText.GetOKPrice(text));
                        try {
                            mLocalData.setCoin(getContext(),Integer.parseInt(text));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(MainActivity.getGlobal(), MainActivity.getGlobal().UserId, Toast.LENGTH_SHORT).show();
                        CostPay(cost);
                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                    }
                }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);

            }
            else{
                MainActivity.getGlobal().showSnackBar("warning","اینترنت خود را متصل کنید",1000);
            }
        }

    }
}


