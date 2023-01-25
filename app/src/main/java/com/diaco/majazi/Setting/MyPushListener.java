package com.diaco.majazi.Setting;


import org.json.JSONException;
import org.json.JSONObject;

import co.ronash.pushe.PusheListenerService;


public class MyPushListener extends PusheListenerService {

    String typeMessage="";
    @Override
    public void onMessageReceived(JSONObject message , JSONObject content) {

        try {
            typeMessage=message.getString("model");

            if (typeMessage.equals("json"))
            {
                jsonHandeler jsonH=new jsonHandeler();
                jsonH.getJson(message,content,getApplicationContext());

            }else
            {
                try {
                    nValue.getGlobal().StartjoinModel = message.getString("model");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(message.length()==0){
            return;
        }

}}