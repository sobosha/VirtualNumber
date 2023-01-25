package com.diaco.majazi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.JsonClass.MainJson;
import com.diaco.majazi.JsonClass.Token;
import com.diaco.majazi.JsonClass.callback_query;
import com.diaco.majazi.JsonClass.chat;
import com.diaco.majazi.JsonClass.from;
import com.diaco.majazi.JsonClass.getNumberResult;
import com.diaco.majazi.JsonClass.getStart;
import com.diaco.majazi.JsonClass.getStartResult;
import com.diaco.majazi.JsonClass.id;
import com.diaco.majazi.JsonClass.message;
import com.diaco.majazi.JsonClass.messagechild;
import com.diaco.majazi.Room.AppDatabase;
import com.diaco.majazi.Setting.CustomClasses.CustomRel;
import com.diaco.majazi.Setting.CustomSnackBar;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mFragment;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.Setting.nValue;
import com.diaco.majazi.util.IabHelper;
import com.diaco.majazi.util.IabResult;

import java.text.ParseException;
import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static MainActivity global;
    private boolean flag_ExistVirtualphone=false;
    public String UserId="";
    public String Service="";
    public AppDatabase db;
    public boolean isFlag_ExistVirtualphone() {
        return flag_ExistVirtualphone;
    }

    public void setFlag_ExistVirtualphone(boolean flag_ExistVirtualphone) {
        this.flag_ExistVirtualphone = flag_ExistVirtualphone;
    }

    public static MainActivity getGlobal() {
        return global;
    }

    public MainActivity() {
        global = this;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Presenter.get_global().OnCreate(getApplicationContext(),"https://diacoipj.com/shomarehMajazi/",mLocalData.getToken(MainActivity.this));
        mLocalData.SetToken(MainActivity.this,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjU0MzAxMTIifQ.xJs1_meCJhfi4LZp1EPWZhnSA1sg_1728esId9OQBag");
        db=AppDatabase.getInstance(getGlobal());
        FinishFragStartFrag(new menu(false));
/*        TextView Coin=findViewById(R.id.textview_Money);
        try {
            Coin.setText(String.valueOf(mLocalData.getCoin(getApplicationContext())));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }


    public mFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(mFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
    private mFragment currentFragment;

    public void FinishFragStartFrag(mFragment newFragment) {
        Presenter.get_global().cancelReq();
        currentFragment = newFragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }



    public boolean isMainDialogShow = false;
    public void MainDiallog(String type) {
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
        isMainDialogShow = true;
        findViewById(R.id.relMainDialogs).setVisibility(VISIBLE);
        findViewById(R.id.imgBlackMain).setVisibility(VISIBLE);
            switch (type) {
            }

        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 2, 0);
        findViewById(R.id.imgBlackMain).setOnClickListener(view -> {
            hideMainDialogs();
        });
    }

    public void hideMainDialogs() {
        isMainDialogShow = false;
        findViewById(R.id.relMainDialogs).setVisibility(View.GONE);
        findViewById(R.id.imgBlackMain).setVisibility(View.GONE);
        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 0, 2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.relMainDialogs).clearAnimation();
                ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        findViewById(R.id.imgBlackMain).setOnClickListener(view -> hideMainDialogs());

    }



    boolean canShowSnack = true, showSnack;
    Handler handler;
    Runnable runnable;
    public void showSnackBar(String type, String desc, int duration) {
        if (canShowSnack) {
            showSnack = true;
            canShowSnack = false;
            findViewById(R.id.relSnackBar).setVisibility(VISIBLE);
            if (type.equals("accept") || type.equals("pending") || type.equals("reject") || type.equals("warning") || type.equals("normal")) {
                mAnimation.myTrans_ToTopSnack(findViewById(R.id.relSnackBar), 0, 500);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
                if (handler == null)
                    handler = new Handler(Looper.getMainLooper());
                runnable = () -> hideSnackBar(false);
                handler.postDelayed(runnable, duration);
            }else {
                this.type = desc;
                mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 1500,-1,0);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
            }
        }

    }

    String type = "" ;
    public void hideSnackBar(boolean isTop ) {
        showSnack = false;
        if(isTop){
            mAnimation.myTrans_ToBottomBAck(findViewById(R.id.relSnackBar), 0, 1000).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                    if(type.length()>0) {
                        MainDiallog(type);
                        type="";
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
            mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 500, 0.15f).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }


    CustomRel customRel ;
    public boolean isRelShow ;
    public void FinishRelStartRel (CustomRel customRel) {
        isRelShow = true;
        this.customRel = customRel;
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
        findViewById(R.id.relMainDialogs).setVisibility(VISIBLE);
        findViewById(R.id.imgBlackMain).setVisibility(VISIBLE);
        mAnimation.myFadeIn(findViewById(R.id.relMainDialogs), 0, 300);
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).addView(customRel);

        Animation fadeOut = new AlphaAnimation(0, 1);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        customRel.clearAnimation();
        customRel.startAnimation(animation);
        findViewById(R.id.imgBlackMain).setOnClickListener(view -> HideMyDialog());

    }

    public void HideMyDialog() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        customRel.clearAnimation();
        customRel.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MainActivity.getGlobal().findViewById(R.id.relMainDialogs)
                        .setVisibility(View.GONE);
                MainActivity.getGlobal().findViewById(R.id.imgBlackMain)
                        .setVisibility(View.GONE);
                ((RelativeLayout) MainActivity.getGlobal()
                        .findViewById(R.id.relMainDialogs)).removeAllViews();
                MainActivity.getGlobal()
                        .findViewById(R.id.relMainDialogs).clearAnimation();
                isRelShow=false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        currentFragment.mBackPressed();
    }

    public MainJson getmessage(String text){
        MainJson mainJson = new MainJson();
        message mOrg=new message();
        mOrg.setMessage_id("");
        mOrg.setText(text);

        chat chat=new chat();
        chat.setId("");
        chat.setType("private");
        mOrg.setChat(chat);

        from from=new from();
        from.setId("372883527");
        from.setFirst_name("");
        from.setUsername("");
        mOrg.setFrom(from);

        callback_query callback_query=new callback_query();
        callback_query.setId("372883527");
        callback_query.setData("");
        callback_query.setFrom(new ArrayList<id>());
        messagechild messagechild=new messagechild();
        messagechild.setMessage_id("");
        messagechild.setChat(new ArrayList<id>());
        callback_query.setMessage(messagechild);
        mOrg.setCallback_query(callback_query);



        mainJson.setMessage(mOrg);

        return  mainJson;

    }

    boolean mIsMarketStore = false;
    public boolean ismIsMarketStore() {
        return mIsMarketStore;
    }

    IabHelper mHelper;

    public IabHelper getmHelper() {
        return mHelper;
    }

    public void CheckMarketStore() {

        if (nValue.marketModel.equals("myket")) {
            mHelper = new IabHelper(this, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkCRFmXDzm36v2sWMA80Vku/TcsbEUEF7NPZQ/KTN7uAF8D4loUqaxT8WFW9eQnf9wRRXfbg6o1qj04euvRzKmbJrS4ANRzdvTwzvao6T8tCvCiS/y0/qR77uPOzGPJcz6if4fgDlzCRcT2FmgnrGKNmD00NsTZ4CAInVTY5BoZQIDAQAB");
        } else if (nValue.marketModel.equals("bazar")) {
            mHelper = new IabHelper(this, "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC08X0roGTf+9yC/zaPsP7JRaV2vnDpD9TQ0/6nc2frsT1x8G0G+jeC9m2Heu4BMNvtr0xvf4JJpLyXCcWBA994ViYXx2cfmzj/8/pYXX0T9IExHipyIcjA2zV/vRgivOxx+lAzLZKcveGf+ptNA24bmazgSz1uYsPhs9vgm8T25cMxgvT9OO+Ltb4ZEMmJQoaaOM75NsBuIRN361jeHdQjfRKgI3c9KN0nOJkLj9sCAwEAAQ==");
        }
        try {

            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    mIsMarketStore = result.isSuccess();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}