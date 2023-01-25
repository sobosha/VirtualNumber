package com.diaco.majazi.Main.Main;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.Dialog.Buy;
import com.diaco.majazi.JsonClass.getCountryResult;
import com.diaco.majazi.JsonClass.payments;
import com.diaco.majazi.Main.FragServices;
import com.diaco.majazi.Main.ListOfBuy;
import com.diaco.majazi.Main.ListViewCountry;
import com.diaco.majazi.Main.ListviewServices;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Setting.CustomClasses.CustomAdapter;
import com.diaco.majazi.Setting.CustomClasses.CustomRel;
import com.diaco.majazi.Setting.SplitText;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.item_variable_services;
import com.diaco.majazi.menu;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RelItemService extends RelativeLayout implements IView<APIResult> {
    String type;
    public RelItemService(Context context,String type) {
        super(context);
        this.type = type;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (type.equals("service"))
            inflater.inflate(R.layout.recycler_item_services, this, true);
        else if(type.equals("ListBuy"))
            inflater.inflate(R.layout.recycler_item_list_buy, this, true);
        else  inflater.inflate(R.layout.recycler_item_country, this, true);
    }

    public void onStartServices(ListviewServices item)
    {
        RelativeLayout rel_item=findViewById(R.id.rel_service_btn);
        ImageView services_image=findViewById(R.id.imageview_services);
        services_image.setImageResource(item.getImage());
        TextView NameText=findViewById(R.id.TextService);
        String temp=item.getServiceCode().replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "");
        temp= temp.replaceAll("\n","");
        NameText.setText(temp);
        rel_item.setBackgroundResource(item.getBackgroundColor());
        rel_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().Service=NameText.getText().toString();
                mAnimation.PressClick(v);
                MainActivity.getGlobal().FinishFragStartFrag(new FragServices(2,item.getServiceCode(),item.getServiceNumberCode()));
            }
        });
    }

    public void onStartCountry(ListViewCountry item,int countryCode,FragServices fragServices,String ServerCode)
    {
        RelativeLayout rel_item_country=findViewById(R.id.rel_item_country);
        TextView text_name=findViewById(R.id.cardview_textview_country);
        TextView text_code=findViewById(R.id.textview_codecountry);
        //TextView text_Cost=findViewById(R.id.TextViewCost);
        TextView text_CostWithOff=findViewById(R.id.CostWithOff);
        ImageView image_country=findViewById(R.id.imageview_recycler_country);
        image_country.setImageResource(item.getImage());
        /*String str="<html><body><font size =\"15px\">"+SplitText.GetOKPrice(item.getAmount())+"</font><font size =\"1px\"><B>تومان</B></font></html></body>";
        Spanned strHtml= Html.fromHtml(str);*/
        text_name.setText(item.getName());
        text_code.setText("(+"+item.getAreaCode()+")");
        //text_Cost.setText(SplitText.GetOKPrice(item.getAmount()));
        text_CostWithOff.setText(SplitText.GetOKPrice(item.getAmount()));
        rel_item_country.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                /*String action="&method=getnum&country="+item.getCountryCode()+"&operator=any&service="+Service_Code;
                Presenter.get_global().PostVirtualNumber(RelItemService.this,action ,APIResult.class);*/
                MainActivity.getGlobal().FinishRelStartRel(new Buy(getContext(),item));

            }
        });
    }

    public void onStartListBuy(ListOfBuy item){
        ImageView imageCountry=findViewById(R.id.imageview_recycler_country);
        imageCountry.setImageResource(item.getImageCountry());
        TextView Cost=findViewById(R.id.TextViewCost);
        Cost.setText(SplitText.GetOKPrice(item.getCost()));
        ImageView imageService=findViewById(R.id.ImageServices);
        imageService.setImageResource(item.getService());
        TextView Name=findViewById(R.id.textview_countryName);
        Name.setText(item.getName());
        TextView Phone=findViewById(R.id.TextView_Phone_recItem);
        Phone.setText("+"+item.getVirtualPhone());

    }

    @Override
    public void SendRequest() {

    }

    @Override
    public void OnSucceed(APIResult object) {
        Toast.makeText(getContext(), "موفق   "+object.getRESULT(), Toast.LENGTH_SHORT).show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        Date date = new Date();
        String timeStamp=formatter.format(date);
        //mLocalData.setTimeVirtualPhone(MainActivity.getGlobal().getApplicationContext(),timeStamp,object.getTIME(),object.getNUMBER(),object.getID());
        menu menu=new menu(false);
        menu.setResult(object);
        MainActivity.getGlobal().setFlag_ExistVirtualphone(true);
        MainActivity.getGlobal().FinishFragStartFrag(menu);
    }

    @Override
    public void OnError(String error, int statusCode) {

        Toast.makeText(getContext(), "ناموفق   "+statusCode, Toast.LENGTH_SHORT).show();
    }
}
