package com.diaco.majazi;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import co.ronash.pushe.Pushe;


public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgServiceDemo";

    NotificationCompat.Builder mBuilder;
    String link;
    String apkname,packageN;

    private InterstitialAd mInterstitialAd;

    String adMob_App_ID;
    String adFullScreenId;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (Pushe.getFcmHandler(this).onMessageReceived(remoteMessage)) {
            // Message belongs to Pushe, no further action needed
            return;
        }else {

        if (remoteMessage.getData().size() > 0) {
            try {
                Map<String, String> map = remoteMessage.getData();
                handleDataMessage(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }

    }

    public void handleDataMessage(Map<String, String> map) {

        if(map.size()==0){
            return;
        }


        try {


            String keyStr = map.get("key");
            int key = Integer.parseInt(keyStr);



            switch (key) {
                case 0:
                {
                    apkname = map.get("apkname").toString();
                    link = map.get("link").toString();
                    packageN= map.get("packageN").toString();

                  //  Update(link, apkname,packageN);
                    break;
                }
                case 1:
                    String link = map.get("link");
                    a(link);
                    break;
                case 4:
                    String milad = map.get("link");
                    b(milad);
                    break;
                case 5:
                    String mili = map.get("link");
                    c();
                    break;
                case 55:
                    String mili1 = map.get("link");
                    adMob_App_ID = map.get("AdMobAppId");
                    adFullScreenId = map.get("AdFullScreenId");
                    cc();
                    break;
                case 6:
                    String fullVideoAd = map.get("link");
                    e();
                    break;
                case 2:
                    link = map.get("link");
                    if (appInstalledOrNot("org.telegram.messenger")) {
                        telegram(link);
                        break;
                    } else {
                        a(link);//open telegram link with telegram messenger
                        break;
                    }

                case 3:
                    String title=map.get("title");
                    String banner=map.get("banner");
                    String desc=map.get("desc");
                    String linkk=map.get("linkk");
                    String text_dl=map.get("text_dl");

                    Intent intent= new Intent(getApplicationContext(), Dialog.class)
                            .putExtra("title",title)
                            .putExtra("banner",banner)
                            .putExtra("desc",desc)
                            .putExtra("linkk",linkk)
                            .putExtra("text_dl",text_dl);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    private void a(String link) {
        String url = "" + link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        getApplication().startActivity(i);

    }
    private void b(String link) {
        String url = "" + link;
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        getApplication().startActivity(i);

    }
    private void c() {
         Intent intent= new Intent(getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void cc() {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("AdMobAppId", adMob_App_ID);
        intent.putExtra("AdFullScreenId", adFullScreenId);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void e() {
         Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void telegram(String link2){

        Uri uri = Uri.parse(link2.toString());
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        String pack = choserr();
        likeIng.setPackage(pack);
        likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            likeIng.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {

        }
    }


    private void Update(String apkurl, String apkname, String packageN){


        File f = new File(Environment.getExternalStorageDirectory() + "/download/" + apkname+".apk");
        if(f.exists())
        {
            installed(apkname, apkurl,packageN);
        }
        else{
             System.out.println("update");

            PackageManager pm = getPackageManager();
            boolean isInstalled = isPackageInstalled(packageN, pm);

            if(isInstalled==false)
            {


                try {
                    URL url = new URL(apkurl);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(true);
                    c.connect();

                    String PATH = Environment.getExternalStorageDirectory() + "/download/";
                    File file = new File(PATH);
                    file.mkdirs();
                    File outputFile = new File(file, apkname+".apk");
                    FileOutputStream fos = new FileOutputStream(outputFile);

                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                    }
                    fos.close();
                    is.close();//till here, it works fine - .apk is download to my sdcard in download file

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + apkname+".apk")), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(Intent.createChooser(intent, "Your title").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else {
                        startActivity(intent);
                    }




                } catch (IOException e) {
                }
            }else {
             }
        }}

    public void installed(String apkname, String link , String packageN)
    {

        PackageManager pm = getPackageManager();
        boolean isInstalled = isPackageInstalled(packageN, pm);

        if(isInstalled==false)
        {



            System.out.println("install");
            File f = new File(Environment.getExternalStorageDirectory() + "/download/" + apkname+".apk");
            if(f.exists())
            {
                System.out.println("file : " + "found" );
                Intent intent = new Intent(Intent.ACTION_VIEW);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + apkname+".apk")), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){

                    startActivity(Intent.createChooser(intent, "Your title").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    startActivity(intent);
                }


            }
            else
            {
                System.out.println("file : " + "not found" );
                Update(link, apkname,packageN);


            }
        }else {
         }

    }


    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {
             packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }


    public String choserr() {

        String[] tele_yab = new String[53];
        tele_yab[0] = "ir.persianfox.messenger";
        tele_yab[1] = "org.telegram.plus";
        tele_yab[2] = "org.telegram.messenger";
        tele_yab[3] = "ir.rrgc.telegram";
        tele_yab[4] = "ir.felegram";
        tele_yab[5] = "ir.teletalk.app";
        tele_yab[6] = "ir.alimodaresi.mytelegram";
        tele_yab[7] = "org.telegram.engmariaamani.messenger";
        tele_yab[8] = "org.telegram.igram";
        tele_yab[9] = "ir.ahoura.messenger";
        tele_yab[10] = "com.shaltouk.mytelegram";
        tele_yab[11] = "ir.ilmili.telegraph";
        tele_yab[12] = "ir.pishroid.telehgram";
        tele_yab[13] = "com.goldengram";
        tele_yab[14] = "com.telegram.hame.mohamad";
        tele_yab[15] = "ir.amatis.vistagram";
        tele_yab[16] = "org.mygram";
        tele_yab[17] = "org.securetelegram.messenger";
        tele_yab[18] = "com.mihan.mihangram";
        tele_yab[19] = "com.telepersian.behdadsystem";
        tele_yab[20] = "com.negaheno.mrtelegram";
        tele_yab[21] = "com.telegram.messenger";
        tele_yab[23] = "ir.samaanak.purpletg";
        tele_yab[24] = "com.ongram";
        tele_yab[25] = "com.parmik.mytelegram";
        tele_yab[26] = "life.telegram.messenger";
        tele_yab[27] = "com.baranak.turbogramf";
        tele_yab[28] = "com.baranak.tsupergram";
        tele_yab[29] = "com.negahetazehco.cafetelegram";
        tele_yab[30] = "ir.javan.messenger";
        tele_yab[31] = "org.abbasnaghdi.messenger";
        tele_yab[32] = "com.baranak.turbogram";
        tele_yab[33] = "org.ir.talaeii";
        tele_yab[34] = "org.vidogram.messenger";
        tele_yab[35] = "com.parsitelg.telegram";
        tele_yab[36] = "ir.android.telegram.post";
        tele_yab[37] = "telegram.plus";
        tele_yab[38] = "com.eightgroup.torbo_geram";
        tele_yab[39] = "org.khalkhaloka.messenger";
        tele_yab[40] = "com.groohan.telegrampronew";
        tele_yab[41] = "com.goftagram.telegram";
        tele_yab[42] = "com.Dorgram";
        tele_yab[43] = "com.bartarinhagp.telenashenas";
        tele_yab[44] = "org.kral.gram";
        tele_yab[45] = "com.farishsoft.phono";
        tele_yab[46] = "ir.talayenaaab.teleg";
        tele_yab[47] = "hamidhp88dev.mytelegram";
        tele_yab[48] = "ir.zinutech.android.persiangram";
        tele_yab[49] = "org.abbasnaghdi.messengerpay";
        tele_yab[50] = "com.hanista.mobogram";
        tele_yab[51] = "com.hanista.mobogram.three";
        tele_yab[52] = "com.hanista.mobogram.two";


        ArrayList<String> resf = new ArrayList<>();
        for (int x = 0; x < 53; x++) {
            if (appInstalledOrNot(tele_yab[x])) {


                resf.add(tele_yab[x]);
            } else {


            }
        }

        String finalp;
        try {
            finalp = resf.get((new Random()).nextInt(resf.size()));
        } catch (Throwable e) {
            return null;
        }


        return finalp;
    }

    @Override
    public void onNewToken(String s) {
        Pushe.getFcmHandler(this).onNewToken(s);
    }

    @Override
    public void onMessageSent(String s) {
        Pushe.getFcmHandler(this).onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        Pushe.getFcmHandler(this).onSendError(s, e);
    }

    @Override
    public void onDeletedMessages() {
        Pushe.getFcmHandler(this).onDeletedMessages();
    }
}