package com.diaco.majazi.Setting;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;


public class Setting {

    public static String GetUrl() {
        return "https://api.liom-app.ir";
    }

    public static String getVersionName() {
        try {
            PackageInfo pInfo = MainActivity.getGlobal().getPackageManager().getPackageInfo(MainActivity.getGlobal().getPackageName(), 0);
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static int currentHour() {
        return Calendar.getInstance().get(11);
    }

    public static int currentMin() {
        return Calendar.getInstance().get(12);
    }

    int TIME = 0;

    public void ShowTimer(int sec, final TimerEvent timerEvent) {
        TIME = sec;
        final Handler handler = new Handler();
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

    public static String GetTimerString(int sec) {
        sec = sec * 1000;
        String hour = "";
        if (TimeUnit.MILLISECONDS.toHours(sec) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(sec)) > 0)
            hour = TimeUnit.MILLISECONDS.toHours(sec) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(sec)) + ":";

        String min = "";
        if (TimeUnit.MILLISECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(sec)) > 0)
            min = (TimeUnit.MILLISECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(sec))) + ":";

        String secend = TimeUnit.MILLISECONDS.toSeconds(sec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sec)) + "";

        return secend;
    }

    /*--------------------------------- picasso ----------------------------------------*/
    public static void LoadImageWhitoutSize(Context context, ImageView img, String imgSrc, int placeHolder) {
        imgSrc = !imgSrc.startsWith("https") ? imgSrc.replace("http" , "https") : imgSrc ;
        try {
            Glide.with(context)
                    .load(imgSrc)
                    .placeholder(placeHolder)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void LoadImageWhitoutSizeNoPLace(Context context, ImageView img, String imgSrc) {

        try {
            Glide.with(context)
                    .load(imgSrc)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*--------------------------- colors and shape styles ---------------------------*/
    public void changeSvgColor(Context context, ImageView imageView, int color) {
        imageView.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
    }

    public static void setIconTinitColor(Context context, ImageView img, int color) {

        if (color == 0)
            img.setColorFilter(color, PorterDuff.Mode.OVERLAY);
        else
            img.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY);
    }

    public static void setNavigationColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.getGlobal().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.getGlobal(), color));
        }
    }

    public static Drawable setBackWithStroke(Context context, int backColor1, int stokeColor, float rdiuse) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{backColor1, backColor1
        });
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(45);
        gd.setCornerRadius(rdiuse);
        gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp), stokeColor);

        return gd;
    }

    public Drawable setBackStroke(int strockBack, int color, int color2, float rad) {
        GradientDrawable gd = new GradientDrawable();
        if (strockBack != 0)
            gd.setStroke(MainActivity.getGlobal().getResources().getDimensionPixelSize(R.dimen._1sdp), strockBack);
        gd.setColors(new int[]{color, color2});
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.setCornerRadius(rad);
        return gd;
    }

    public Drawable setBackColor(int color, int color2, float rad) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{color, color2});
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.setCornerRadius(rad);
        return gd;
    }

    public Drawable setBackStroke(int strockBack, int color, int color2, float rad, int strokeRad) {
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(strokeRad, strockBack);
        gd.setColors(new int[]{color, color2});
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.setCornerRadius(rad);
        return gd;
    }

    public Drawable setCustomBackStroke(int strockBack, int color, int color2, float rad) {
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(MainActivity.getGlobal().getResources().getDimensionPixelSize(R.dimen._5sdp), strockBack);
        gd.setColors(new int[]{color, color2});
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.setCornerRadius(rad);
        return gd;
    }

    public static Drawable setBackGradiantFullRad(Context context, int backColor1, int backColor2, float rdiuse) {
        GradientDrawable gd = new GradientDrawable();
        if (backColor2 != 0) {
            gd.setColors(new int[]{backColor1, backColor2});
        } else gd.setColors(new int[]{backColor1, backColor1});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(90);
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gd.setCornerRadius(rdiuse);
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }

    public static Drawable setBackGradiantFullRadTB(Context context, int backColor1, int backColor2, float rdiuse) {
        GradientDrawable gd = new GradientDrawable();
        if (backColor2 != 0) {
            gd.setColors(new int[]{backColor1, backColor2});
        } else gd.setColors(new int[]{backColor1, backColor1});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(90);
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gd.setCornerRadius(rdiuse);
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }

    public static Drawable setBackGradiantFullRadBT(Context context, int backColor1, int backColor2, float rdiuse) {
        GradientDrawable gd = new GradientDrawable();
        if (backColor2 != 0) {
            gd.setColors(new int[]{backColor1, backColor2});
        } else gd.setColors(new int[]{backColor1, backColor1});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(90);
        gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
        gd.setCornerRadius(rdiuse);
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }


    public static Drawable setBackGradiantWithRadiuse(int backColor1, int backColor2, int centerColor, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable gd = new GradientDrawable();
        if (backColor2 != 0 && centerColor == 0) {
            gd.setColors(new int[]{backColor1, backColor2, centerColor});
        } else if (backColor2 == 0 && centerColor == 0) {
            gd.setColors(new int[]{backColor1, backColor1, backColor1});
        } else
            gd.setColors(new int[]{backColor1, backColor2});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(45);
        // gd.setCornerRadius(context.getResources().getDimension(R.dimen._30sdp));
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }

    public static Drawable setBackGradiantWithRadiuseWithAngle(int backColor1, int backColor2, int centerColor, float topLeft, float topRight, float bottomRight, float bottomLeft , GradientDrawable.Orientation orientation) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{backColor1, backColor2, centerColor});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(45);
        gd.setOrientation(orientation);
        // gd.setCornerRadius(context.getResources().getDimension(R.dimen._30sdp));
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }

    public static Drawable setBackGradiantWithRadiuseTB(int backColor1, int backColor2, int centerColor, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable gd = new GradientDrawable();
        if (backColor2 != 0 && centerColor == 0) {
            gd.setColors(new int[]{backColor1, backColor2, centerColor});
        } else if (backColor2 == 0 && centerColor == 0) {
            gd.setColors(new int[]{backColor1, backColor1, backColor1});
        } else
            gd.setColors(new int[]{backColor1, backColor2});
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientRadius(90);
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        // gd.setCornerRadius(context.getResources().getDimension(R.dimen._30sdp));
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        // gd.setStroke(context.getResources().getDimensionPixelSize(R.dimen._1sdp),Color.parseColor("#383838"));

        return gd;
    }

    public void setBackgroundWithAttrs(Context context, View v, int attr, float radius) {
        v.setBackground(Setting.setBackWithStroke(
                context, setColorWithAttrs(context, attr), setColorWithAttrs(context, attr)
                , radius
        ));
    }

    public int setColorWithAttrs(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        boolean got = theme.resolveAttribute(attr, typedValue, true);
        return got ? typedValue.data : 0;
    }

    /*-------------- font styles ----------------------------------*/
    public void setTypeFace(TextView textView) {
        Typeface face = Typeface.createFromAsset(MainActivity.getGlobal().getAssets(),
                "fonts/dana_fa_num_bold.ttf");
        textView.setTypeface(face);
    }

    public void setCustomFont(TextView textView,String fontFamily)
    {
        switch (fontFamily)
        {
            case "dana":
                Typeface face = Typeface.createFromAsset(MainActivity.getGlobal().getAssets(),
                        "fonts/dana_fa_num_regular.ttf");
                textView.setTypeface(face);
                break;
            case "dastnevis":
                Typeface face1 = Typeface.createFromAsset(MainActivity.getGlobal().getAssets(),
                        "fonts/danstevis.ttf");
                textView.setTypeface(face1);
                break;
            case "negar":
                Typeface face2 = Typeface.createFromAsset(MainActivity.getGlobal().getAssets(),
                        "fonts/negaar_regular.ttf");
                textView.setTypeface(face2);
                break;
        }

    }

    /* ------------------------- recyclers ----------------------------------*/
    public static void SetRecyclerAdapter(RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.invalidate();

        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }


    public static void SetHorizaentalRecyclerAdapter(RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.getGlobal(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.invalidate();
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    public static void SetVerticalRecyclerAdapter(RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.getGlobal(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.invalidate();
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    public static void SetGridRecyclerAdapter(RecyclerView recyclerView, RecyclerView.Adapter mAdapter, int harchi) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(recyclerView.getContext(), harchi, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.invalidate();
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    /*----------------------------------- sanckBar -----------------------------*/
    public void rtlSnackBar(View v, String text) {
        Snackbar snackbar = Snackbar.make(v, text, Snackbar.LENGTH_SHORT);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }

    /*----------------------------------- number pickers -----------------------------*/
    public static String[] generateNumbers(int start, int end, String[] array, List<String> list, int changePicker, String addZero, String paswand) {
        for (int i = start; i < end; i++) {
            if (i < 9)
                list.add((addZero + (i + changePicker)) + paswand);
            else
                list.add(String.valueOf(i + changePicker) + paswand);
            array[i] = list.get(i);
        }
        return array;
    }

    public void createNumPickerWithStringVals(NumberPicker numberPickers, int minValue, int maxValue, String[] displayValue) {
        numberPickers.setMinValue(minValue);
        numberPickers.setMaxValue(maxValue);
        numberPickers.setDisplayedValues(displayValue);
    }

    /*---------------------------------- keyboards  ------------------------------------------ */

    public static void OnScrollOnKeyBoard(final EditText edtText, final ScrollView scrollView) {
        edtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
                            int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
                            int sy = scrollView.getScrollY();
                            int sh = scrollView.getHeight();
                            int delta = bottom - (sy + sh);

                            scrollView.smoothScrollBy(0, delta);
                        }
                    }, 200);
                }
            }
        });
        edtText.setOnClickListener(v -> new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
                int sy = scrollView.getScrollY();
                int sh = scrollView.getHeight();
                int delta = bottom - (sy + sh);

                scrollView.smoothScrollBy(0, delta);
            }
        }, 200));
    }

    public boolean checkIsKeyboardOpen() {
        InputMethodManager imm = (InputMethodManager) MainActivity.getGlobal().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = MainActivity.getGlobal().getCurrentFocus();
        if (view == null) {
            view = new View(MainActivity.getGlobal());
        }
        if (imm != null) {
            imm.showSoftInput(view, 0);
            return true;
        } else return false;
    }

    public void HideKeyBoard() {
        try {
            final View focuseView = MainActivity.getGlobal().getCurrentFocus();
            if (focuseView != null && focuseView.getWindowToken() != null){
                InputMethodManager imm = (InputMethodManager) MainActivity.getGlobal().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(focuseView.getWindowToken(), 0);
    }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void ShowKeyBoard (Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static void shareText(Context context, String subject, String body) {
        Intent txtIntent = new Intent(Intent.ACTION_SEND);
        txtIntent.setType("text/plain");
        txtIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        txtIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(txtIntent, "Share"));
    }

    public static String[] textSeprator(String text, String signSeprator) {
        String[] separated = text.split(signSeprator);
        return separated;
    }

    /*------------------------------------- anim and transitions ---------------------------------------------*/

    public void TransitionResize(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();


            changeBounds.setDuration(300);
            TransitionManager.go(new Scene((ViewGroup) view));
        }
    }

    public void TransitionTransform(View view, boolean Overlay , long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBound = new ChangeBounds();
            ChangeTransform changeTran = new ChangeTransform();

            changeTran.setReparentWithOverlay(Overlay);

            TransitionSet set = new TransitionSet();
            set.addTransition(changeTran);
            set.addTransition(changeBound);

            set.setDuration(duration);
            TransitionManager.go(new Scene((ViewGroup) view), set);
        }
    }


    public void showSyncButton(final RecyclerView recyclerView, final View view) {
        view.setVisibility(View.VISIBLE);
        recyclerView.setOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                view.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                view.animate().translationY(recyclerView.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });

    }

    /*------------------------------------------- flags and requests  -------------------------------------------------------*/
    public boolean isNetworkConnect() {
        ConnectivityManager conMgr = (ConnectivityManager) nValue.getGlobal().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null)
            return false;
        else
            return true;
    }

    /*------------------------------------------- user track  -------------------------------------------------------*/

    int currentPos;
    boolean exists = false;

//    public void trackUserByTag(View view) {
//        try {
//            currentPos = 0;
////----------------------اینجا اسم پکیج اضافه رو پاک میکنه و فقط اسم کلاس رو برمیگردونه-----------------------
//            String Cname = MainActivity.getGlobal().getCurrentFragment().getClass().getName();
//            String[] splits = Cname.split("\\.");
//            String className = splits[splits.length - 1];
//            //------------------------------------اگر برای بار اول باشه یک دونه توی لیست اد میکنه----------------------------
//            if (nValue.getGlobal().getViewList().size() == 0 || nValue.getGlobal().getViewList() == null) {
//                nValue.getGlobal().getCounterList().add(new CounterList(view.getTag() + "Counter", 1));
//                nValue.getGlobal().getViewList().add(new StepItem(className, view.getTag() + "", nValue.getGlobal().getCounterList().get(nValue.getGlobal().getCounterList().size() - 1).getCounter(), getViewType(view)));
//            } else {
//                for (int i = 0; i < nValue.getGlobal().getViewList().size(); i++) {
////------------------------------------------------------اینجا زمانی که اون ویو رو ئیا کرد ادیتش میکنه و تعداد کلیکشو وارد میکنه------------------------------------
//                    if (nValue.getGlobal().getViewList().get(i).getTag().equals(view.getTag() + "")) {
//                        exists = true;
//                        currentPos = i;
//                        break;
//                    }
////------------------------------------اینجا زمانی که ویو رو پیدا نکرده باشه اتفاق میوفته------------------------------------
//                    else exists = false;
//                }
////------------------------------حالا اینجا اگه ویو وجود داشته باشه ادیت میشه وگرنه یکی به لیست اضافه میشه------------------------------
//                if (exists) {
//                    nValue.getGlobal().getCounterList().set(currentPos, new CounterList(nValue.getGlobal().getCounterList().get(currentPos).getId(), nValue.getGlobal().getCounterList().get(currentPos).getCounter() + 1));
//                    nValue.getGlobal().getViewList().set(currentPos, new StepItem(className, view.getTag() + "", nValue.getGlobal().getCounterList().get(currentPos).getCounter(), getViewType(view)));
//                } else {
//                    nValue.getGlobal().getCounterList().add(new CounterList(view.getTag() + "Counter", 1));
//                    nValue.getGlobal().getViewList().add(new StepItem(className, view.getTag() + "", nValue.getGlobal().getCounterList().get(nValue.getGlobal().getCounterList().size() - 1).getCounter(), getViewType(view)));
//                }
//            }
//        } catch (Exception e) {
//
//        }
//
//
//    }

    String Vtype;

    public String getViewType(View view) {
        String Vname = view.getClass().getName();
        String[] splits = Vname.split("\\.");
        String vsName = splits[splits.length - 1];
        String Vname2 = vsName;
        if (Vname2.contains("AppCompat")) {
            String[] splits2 = Vname2.split("AppCompat");
            String vsName2 = splits2[1];
            return Vtype = vsName2;
        } else return Vname2;
    }

    /*----------------------------------------------------- share ----------------------*/

    public void shareImageUri(Uri uri, String subject) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, subject);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
        whatsappIntent.setType("image/jpeg");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            MainActivity.getGlobal().startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    public void shareText(String subject) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, subject);
        try {
            MainActivity.getGlobal().startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    public static Uri saveImage(Context context, Bitmap image) {
        // Should be processed in another thread
        File imagesFolder = new File(MainActivity.getGlobal().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = MAGFileProvider.getUriForFile(context, "shared_images", file);

        } catch (IOException e) {
            Log.d("lslls", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }


    public static void OnAnimationEnd (Animation animation , IAnimationEnd end) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) { end.TheEnd(); }
            @Override
            public void onAnimationRepeat(Animation animation) { }});
    }

    public static Integer getImageId(String name, Context context) {
        if (name == null) {
            return null;
        } else {
            return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        }
    }

    public void takeScreenshot(Context context , Activity getActivity , String share) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = context.getFilesDir().toString() + "/" + now + ".jpg";
            View v1 = getActivity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(share,imageFile , context);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private void openScreenshot(String share , File imageFile , Context context) {
        Uri uri = Uri.fromFile(imageFile);
        share(share,uri , context);
    }
    public void share(String share,Uri uri , Context context) {
//         R.drawable.share_pic
        //Bitmap mIcon = BitmapFactory.decodeResource(MainActivity.getGlobal().getResources(),icon);
        Bitmap mIcon=loadBitmap(String.valueOf(uri));
        String url[] = new Setting().textSeprator(Setting.getVersionName(), " ");
        String u = "";
        if (url[1].startsWith("B")) {
            u = "https://cafebazaar.ir/app/com.diacotdj.liom";
        } else if (url[1].startsWith("M"))
            u = "https://myket.ir/app/com.diacotdj.liom";
        else u = "https://play.google.com/store/apps/details?id=com.diacotdj.liom";
        new Setting().shareImageUri(Setting.saveImage(context, mIcon), share!=null && !share.equals("")  ? share+"\n" + "دوست خوبم سلام، لیوم می\u200Cتونه برای زندگی بهتر و شاد تر خیلی بهت کمک کنه.\n" +
                " میتونی از آدرس پایین دانلودش کنی." + "\n" + u : "دوست خوبم سلام، لیوم می\u200Cتونه برای زندگی بهتر و شاد تر خیلی بهت کمک کنه.\n" +
                " میتونی از آدرس پایین دانلودش کنی." + "\n" + u);
    }
    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }


    public static byte [] toByteImg (int image) {
        Drawable drawable = ContextCompat.getDrawable(MainActivity.getGlobal(), image);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray() ;
    }

    public static Bitmap getBitmapFByte (byte [] imageData) {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }
    public static String PersianToEnglish (String value) {
        return value.replace("١", "1").replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5")
                .replace("٦", "6").replace("7", "٧").replace("٨", "8").replace("٩", "9").replace("٠", "0")
                .replace("۱", "1").replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5")
                .replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9").replace("۰", "0");
       }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getRawPath (int raw) {
        return "android.resource://" + MainActivity.getGlobal().getPackageName() + "/" + raw;
    }

    public static void CopyTextInClipBoard (Context context , String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", text);
        manager.setPrimaryClip(clipData);
        MainActivity.getGlobal().showSnackBar("accept" , "کد با موفقیت کپی شد" , 1000);
    }

    static CountDownTimer countDownTimer;
    public static void countDownTimer(int second,CountDownEvent countDown)
    {
        countDownTimer = new CountDownTimer(second*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                countDown.onTick(millisUntilFinished / 1000);
            }

            public void onFinish() {
                countDown.onFinish();
            }
        }.start();
    }

    public static void removeCountDown()
    {
        countDownTimer.cancel();
    }
}


