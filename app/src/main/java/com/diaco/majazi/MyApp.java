package com.diaco.majazi;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Looper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.diaco.majazi.Core.CrashRequest;
import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.Setting.nValue;
import com.jaredrummler.android.device.DeviceName;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.tapsell.sdk.Tapsell;

public class MyApp extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    String TAPSELL_KEY = "rflgasbbfbtlmdmgrlaestfpoeclmeqpblraqhashsafihrdqqnfbdmtgskkcrongiemrs";
    String FullModell;
    String userInfo ="";
    private static Context context;
    @Override
    public void onCreate() {

        super.onCreate();
//        LocaleHelper.setLocale(this,"fa-rAF");

        Tapsell.initialize(this, TAPSELL_KEY);
        MyApp.context = getApplicationContext();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/dana_fa_num_bold.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());





        DeviceName.with(this).request(new DeviceName.Callback() {

            @Override public void onFinished(DeviceName.DeviceInfo info, Exception error) {

                String manufacturer = info.manufacturer;
                String name = info.marketName;
                String model = info.model;
                FullModell = manufacturer  + " " + name + " " + model;

            }
        });

  Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread paramThread, final Throwable paramThrowable) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        String str ;
                        if(MainActivity.getGlobal() != null && MainActivity.getGlobal().getCurrentFragment()!=null && MainActivity.getGlobal().getCurrentFragment().getClass()!=null)
                            str = MainActivity.getGlobal().getCurrentFragment().getClass().getName();
                        else str = "";
                        str = str.replace("com.diacotdj.liom","");
                        str=str.replace(".","_");

                        userInfo = "\n -------------------------------------------------------"+
                                "\n  Theme =  " + theme() +
                                "\n  installDate =  " + mLocalData.getInstallDate(context) +
                                "\n ------------------------------------------------------- \n";

                        CrashRequest request;
                        try {

                            String classNames = "";
                            for(int i = 0;i < paramThrowable.getStackTrace().length; i++)
                            {
                                classNames = classNames +  paramThrowable.getStackTrace()[i].getLineNumber() + " " + paramThrowable.getStackTrace()[i].getClassName() +"\n";
                            }

                            request = new CrashRequest(paramThrowable.toString() +
                                    "\n" + userInfo+
                                    "\nClass Name :\n" + paramThrowable.getStackTrace()[0].getClassName() +
                                    "\n #1-Page :\n"+ nValue.getGlobal().getFirstPage()+
                                    "\n #2-Page :\n"+ nValue.getGlobal().getSecondPage()+
                                    "\n #3-Page :\n"+ nValue.getGlobal().getThirdPage()+
                                    "\nMethod Name :\n" + paramThrowable.getStackTrace()[0].getMethodName() +
                                    "\nLine Number :\n" + paramThrowable.getStackTrace()[0].getLineNumber(),
                                    "\nDevice Name :" + FullModell + "\nAndroid SDK Version :" + Build.VERSION.SDK_INT + "   "+"#Android : "+getAndroidVersion()+
                                            "\nMethods :\n" + classNames + "\nFragment :" + str
                                    , str,
                                    nValue.getGlobal().getFirstResponseData() ,  nValue.getGlobal().getSecondResponseData() ,1);//todo change to 0 when release
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            try {
                                request = new CrashRequest(paramThrowable.getMessage() ,"\nDevice Name :" + FullModell + "\n\nAndroid SDK Version :" + Build.VERSION.SDK_INT ,str,
                                        nValue.getGlobal().getFirstResponseData()   , nValue.getGlobal().getSecondResponseData() ,1); //todo change to 0 when release
                            }
                            catch (Exception e1)
                            {
                                e1.printStackTrace();
                                request = new CrashRequest("خطای نامشخص","خطای نامشخص",str, nValue.getGlobal().getFirstResponseData()   , nValue.getGlobal().getSecondResponseData(),1);//todo change to 0 when release
                            }
                        }
                       if(new Setting().isNetworkConnect()) {
                           Presenter.get_global().OnCreate(MyApp.this, "https://api.liom-app.ir/", Presenter.get_global().getSERVER_KEY());
                           Presenter.get_global().PostAction(new IView<String>() {
                               @Override
                               public void SendRequest() {

                               }

                               @Override
                               public void OnSucceed(String object) {
                                   int a = 0;
                               }

                               @Override
                               public void OnError(String error, int statusCode) {
                                   int a = 0;
                               }
                           }, "report", "crash", "", request, String.class);
                       }
                        Looper.loop();
                    }
                }).start();


                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.exit(0);

            }
        });

    }

    public static Context getAppContext() {
        return MyApp.context;
    }

    double androidVersion;
    public double getAndroidVersion()
    {
        switch (Build.VERSION.SDK_INT)
        {
            case 18:
                androidVersion = 4.3;
                break;
            case 19:
            case 20:
                androidVersion = 4.4;
                break;
            case 21:
                androidVersion = 5.0;
                break;
            case 22:
                androidVersion = 5.1;
                break;
            case 23:
                androidVersion = 6.0;
                break;
            case 24:
                androidVersion = 7.0;
                break;
            case 25:
                androidVersion = 7.1;
                break;
            case 26:
                androidVersion = 8.0;
                break;
            case 27:
                androidVersion = 8.1;
                break;
            case 28:
                androidVersion = 9.0;
                break;
            case 29:
                androidVersion = 10.0;
                break;
            case 30:
                androidVersion = 11.0;
                break;
        }
        return androidVersion;
    }


    public  String theme(){
        if(mLocalData.getDarkThemeStatus(nValue.getGlobal().getContext()))
            return "Dark";
        else return "Light";
    }




}

