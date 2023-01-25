package com.diaco.majazi.Setting;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class jsonHandeler
{
    String link;

    Context context;

    public void getJson(JSONObject message, JSONObject pushMessage, Context con)
    {
        context=con;

        if(message.length()==0){
            return;
        }


        try {


            String keyStr = message.getString("key");
            int key = Integer.parseInt(keyStr);

            switch (key) {
                case 1: {
                    String link = message.getString("link");
                    a(link);
                    break;
                }
                case 4: {
                    String milad = message.getString("link");
                    b(milad);
                    break;
                }
                case 2:
                    link = message.getString("link");
                    if (appInstalledOrNot("org.telegram.messenger")) {
                        telegram(link);
                        break;
                    } else {
                        a(link);//open telegram link with telegram messenger
                        break;
                    }
                case 3: {
                    String title = message.getString("title");
                    String banner = message.getString("banner");
                    String desc = message.getString("desc");
                    String linkk = message.getString("linkk");
                    String text_dl = message.getString("text_dl");

                    Intent intent = new Intent(context, Dialog_push.class)
                            .putExtra("title", title)
                            .putExtra("banner", banner)
                            .putExtra("desc", desc)
                            .putExtra("linkk", linkk)
                            .putExtra("text_dl", text_dl);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }  break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    private void a(String link) {
        String url = "" + link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        context.startActivity(i);

    }
    private void b(String link) {
        String url = "" + link;
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(url));
        context.startActivity(i);

    }



    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
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
            context.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {

        }
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

}
