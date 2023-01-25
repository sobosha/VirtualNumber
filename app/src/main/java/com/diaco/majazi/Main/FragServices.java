package com.diaco.majazi.Main;

import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.JsonClass.CountryAmount;
import com.diaco.majazi.JsonClass.Token;
import com.diaco.majazi.JsonClass.getCountryResult;
import com.diaco.majazi.JsonClass.getNumberResult;
import com.diaco.majazi.Main.Main.RelItemService;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Setting.CustomClasses.CustomAdapter;
import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.SplitText;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.menu;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragServices extends CustomFragment {
    TextView title;
    int pages;
    String type;
    CustomAdapter customAdapter,customAdapter1;
    List<ListviewServices> listviewServices;
    List<CountryAmount> listCountryWithAmount;
    List<ListViewCountry> listViewCountries;
    RelativeLayout rel_rules,Sort;
    String Service_CODE;
    ProgressBar progressBar;
    TextView Money,HighSort,LowSort;
    int Country_CODE;
    String ServiceNumberCode="";
    int Select=0;
    @Override
    public int layout() {
        return R.layout.fragment_services;
    }

    @Override
    public void onCreateMyView() {
        RelativeLayout textView_plusmoney=parent.findViewById(R.id.btn_Plus_money);
        Sort=parent.findViewById(R.id.Sort);
        HighSort=parent.findViewById(R.id.SortHigh);
        LowSort=parent.findViewById(R.id.SortLow);
        textView_plusmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                MainActivity.getGlobal().FinishFragStartFrag(new menu(1));
            }
        });
        Money=parent.findViewById(R.id.textview_Money);

        title=parent.findViewById(R.id.textview_title_services);
        if(pages==0){
            (parent.findViewById(R.id.recycler_services)).setVisibility(View.GONE);
            rel_rules=parent.findViewById(R.id.rel_choice_mainmenu_rules);
            rel_rules.setVisibility(View.VISIBLE);
            type="rules";
            title.setText("قوانین و مقررات");
            try {
                Money.setText(SplitText.GetOKPrice(mLocalData.getCoin(getContext())+""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(pages==1){
            type = "service";
            (parent.findViewById(R.id.recycler_services)).setVisibility(View.VISIBLE);
            title.setText("سرویس ها");
            progressBar=parent.findViewById(R.id.progress_load);
            Sort.setVisibility(View.GONE);
        }
        else if(pages==2){
            listCountryWithAmount=new ArrayList<>();
            type = "number";
            (parent.findViewById(R.id.recycler_services)).setVisibility(View.VISIBLE);
            title.setText("کشور ها");
            progressBar=parent.findViewById(R.id.progress_load);
        }

        if (type.equals("service"))
        {
            progressBar.setVisibility(View.VISIBLE);
            if(new Setting().isNetworkConnect()) {
                if (mLocalData.getToken(getContext()).equals("")) {
                    getToken(new IAfterSucc() {
                        @Override
                        public void Success() {
                            Presenter.get_global().PostAction(new IView<getNumberResult>() {
                                @Override
                                public void SendRequest() {

                                }

                                @Override
                                public void OnSucceed(getNumberResult object) {
                                    progressBar.setVisibility(View.GONE);
                    /*Toast.makeText(MainActivity.getGlobal(), ""+object.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.getGlobal(), "Succes", Toast.LENGTH_SHORT).show();*/
                                    ListAddServices(object);
                                    customAdapter = new CustomAdapter.RecyclerBuilder<ListviewServices, RelItemService>(getContext(), parent.findViewById(R.id.recycler_services), listviewServices)
                                            .setView(() -> new RelItemService(getContext(), type))
                                            .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartServices(list.get(position)))
                                            .orientation(RecyclerView.VERTICAL)
                                            .grid(3)
                                            .build();
                                }

                                @Override
                                public void OnError(String error, int statusCode) {
                                    Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                                }
                            }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDECD خرید شماره مجازی"), getNumberResult.class);

                        }
                    });
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
                            //Toast.makeText(MainActivity.getGlobal(), text, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);
                } else {
                    Presenter.get_global().PostAction(new IView<getNumberResult>() {
                        @Override
                        public void SendRequest() {

                        }

                        @Override
                        public void OnSucceed(getNumberResult object) {
                            progressBar.setVisibility(View.GONE);
                    /*Toast.makeText(MainActivity.getGlobal(), ""+object.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.getGlobal(), "Succes", Toast.LENGTH_SHORT).show();*/
                            ListAddServices(object);
                            customAdapter = new CustomAdapter.RecyclerBuilder<ListviewServices, RelItemService>(getContext(), parent.findViewById(R.id.recycler_services), listviewServices)
                                    .setView(() -> new RelItemService(getContext(), type))
                                    .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartServices(list.get(position)))
                                    .orientation(RecyclerView.VERTICAL)
                                    .grid(3)
                                    .build();
                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDECD خرید شماره مجازی"), getNumberResult.class);
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
                            //Toast.makeText(MainActivity.getGlobal(), text, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);

                }
            }
            else{
                MainActivity.getGlobal().showSnackBar("warning","لطفا اینترنت خود را متصل کنید",1000);
                mBackPressed();
            }

        }
        else if(type.equals("number"))
        {
            progressBar.setVisibility(View.VISIBLE);
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
                        //Toast.makeText(MainActivity.getGlobal(), text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                    }
                }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 حساب کاربری"), getNumberResult.class);
                Presenter.get_global().PostAction(new IView<getCountryResult>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(getCountryResult object) {

                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                    }
                },"api.php?action=true" , "" , "" , MainActivity.getGlobal().getmessage(Service_CODE),getCountryResult.class);
                Presenter.get_global().PostAction(new IView<CountryAmount>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(CountryAmount object) {
                        Sort.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    /*Toast.makeText(MainActivity.getGlobal(), ""+object.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.getGlobal(), "Succes", Toast.LENGTH_SHORT).show();*/
                        ListAddCountry(object);
                        customAdapter1 = new CustomAdapter.RecyclerBuilder<ListViewCountry, RelItemService>(getContext(), parent.findViewById(R.id.recycler_services), listViewCountries)
                                .setView(() -> new RelItemService(getContext(),type))
                                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartCountry(list.get(position),listViewCountries.get(position).CountryCode,FragServices.this,listViewCountries.get(position).ServerCode))
                                .orientation(RecyclerView.VERTICAL)
                                .build();
                        SortHander(0);
                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                    }
                },"api.php?action=service&service=" , ServiceNumberCode , "" , MainActivity.getGlobal().getmessage(Service_CODE),CountryAmount.class);

            }
            else{
                MainActivity.getGlobal().showSnackBar("warning","لطفا اینترنت خود را متصل کنید",1000);
                mBackPressed();
            }



        }

        LowSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Select!=0) {
                    mAnimation.PressClick(v);
                    SortHander(0);
                    Select=0;
                }
            }
        });
        HighSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Select!=1) {
                    mAnimation.PressClick(v);
                    SortHander(1);
                    Select=1;
                }
            }
        });

    }

    public FragServices(int page,String Code,String NumberCode){
        pages=page;
        if (page==2){
            Service_CODE=Code;
            ServiceNumberCode=NumberCode;
        }
    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(MainActivity.getGlobal().isRelShow){
            MainActivity.getGlobal().HideMyDialog();
        }
        else if(pages==0){
            MainActivity.getGlobal().FinishFragStartFrag(new menu(false));
        }
        else if(pages==2){
            MainActivity.getGlobal().FinishFragStartFrag(new FragServices(1,"",""));
        }
        else{
            MainActivity.getGlobal().FinishFragStartFrag(new menu(false));
        }

    }

    private void ListAddServices(getNumberResult result){
        listviewServices=new ArrayList<>();
        for(int i=0;i<result.getReply_markup().getKeyboard().size();i++){
            String ServerName=result.getReply_markup().getKeyboard().get(i).getText();
            if(ServerName.contains("تلگرام"))
                listviewServices.add(new ListviewServices(R.drawable.ic_telegram_2,ServerName,"1",R.drawable.shape_telegram));
            else if(ServerName.contains("اینستاگرام"))
                listviewServices.add(new ListviewServices(R.drawable.ic_instagram_5,ServerName,"2",R.drawable.shape_instagram));
            else if(ServerName.contains("واتساپ"))
                listviewServices.add(new ListviewServices(R.drawable.ic_whatsapp,ServerName,"3",R.drawable.shape_whatsapp));
            else if(ServerName.contains("گوگل"))
                listviewServices.add(new ListviewServices(R.drawable.ic_google,ServerName,"6",R.drawable.shape_google));
            else if(ServerName.contains("وایبر"))
                listviewServices.add(new ListviewServices(R.drawable.ic_viber,ServerName,"4",R.drawable.shape_viber));
            else if(ServerName.contains("ویچت"))
                listviewServices.add(new ListviewServices(R.drawable.ic_wechat,ServerName,"5",R.drawable.shape_wechat));
            else if(ServerName.contains("فیسبوک"))
                listviewServices.add(new ListviewServices(R.drawable.ic_facebook,ServerName,"7",R.drawable.shape_facebook));
            else if(ServerName.contains("توییتر"))
                listviewServices.add(new ListviewServices(R.drawable.ic_twitter_1,ServerName,"8",R.drawable.shape_tweeter));
            else if(ServerName.contains("یاهو"))
                listviewServices.add(new ListviewServices(R.drawable.ic_yahoo_1,ServerName,"11",R.drawable.shape_yahoo));
            else if(ServerName.contains("ایمو"))
                listviewServices.add(new ListviewServices(R.drawable.ic_imo,ServerName,"18",R.drawable.shape_imo));
            else if(ServerName.contains("پیپال"))
                listviewServices.add(new ListviewServices(R.drawable.ic_paypal,ServerName,"15",R.drawable.shape_paypal));
            else if(ServerName.contains("لاین"))
                listviewServices.add(new ListviewServices(R.drawable.ic_line,ServerName,"10",R.drawable.shape_line));
            else if(ServerName.contains("ماکروسافت"))
                listviewServices.add(new ListviewServices(R.drawable.ic_microsoft,ServerName,"9",R.drawable.shape_micro));
            else if(ServerName.contains("آمازون"))
                listviewServices.add(new ListviewServices(R.drawable.ic_amazon_icon_1,ServerName,"21",R.drawable.shape_amazon));



        }
    }
    private void ListAddCountry(CountryAmount result){
        listViewCountries=new ArrayList<>();
        for(int i=0;i<result.getServices().size();i++) {
            String ServerName=result.getServices().get(i).getName()+" "+result.getServices().get(i).getEmoji();
            if(ServerName.contains("قزاقستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kazakhstan,7,"قزاقستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("روسیه"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,7,"روسیه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("اکراین"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,380,"اکراین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("چین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_peoples_republic_of_china,86,"چین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("فیلیپین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_philippines,63,"فیلیپین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("میانمار"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_myanmar,95,"میانمار",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("اندونزی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_indonesia,62,"اندونزی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("مالزی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malaysia,60,"مالزی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("کنیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kenya,254,"کنیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("تانزانیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,255,"تانزانیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("ویتنام"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_vietnam,84,"ویتنام",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("انگلستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_kingdom,44,"انگلستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("لتونی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,371,"لتونی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("رومانی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_romania,40,"رومانی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("استونی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_estonia,372,"استونی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("آمریکا"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"آمریکا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("آمریکا ویژه"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"آمریکا ویژه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("قرقیزستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kyrgyzstan, 996, "قرقیزستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("فرانسه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_france, 33, "فرانسه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("فلسطین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_palestine___long_triangle, 972, "فلسطین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کامبوج"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cambodia, 855, "کامبوج",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ماکائو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_macau, 853, "ماکائو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("هنگ کنگ"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hong_kong, 852, "هنگ کنگ",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("برزیل"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_brazil, 55, "برزیل",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لهستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_poland, 48, "لهستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پاراگوئه"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 595, "پاراگوئه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("هلند"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 31, "هلند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لیتوانی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania, 370, "لیتوانی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ماداگاسکار"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_madagascar, 261, "ماداگاسکار",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کنگو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_democratic_republic_of_the_congo, 243, "کنگو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نیجریه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria, 234, "نیجریه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آفریقا جنوبی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_south_africa, 27, "آفریقا جنوبی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پاناما"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_panama, 507, "پاناما",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مصر"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_egypt, 20, "مصر",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("هند"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_india, 91, "هند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ایرلند"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ireland, 353, "ایرلند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ساحل عاج"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 225, "ساحل عاج",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("صربستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_serbia, 381, "صربستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لائوس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_laos, 856, "لائوس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مراکش"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_morocco, 212, "مراکش",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("یمن"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_yemen, 967, "یمن",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("غنا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ghana, 233, "غنا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کانادا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_canada_pantone, 1, "کانادا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آرژانتین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_argentina, 54, "آرژانتین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("عراق"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 964, "عراق",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آلمان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_germany, 49, "آلمان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کامرون"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cameroon, 237, "کامرون",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ترکیه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkey, 90, "ترکیه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نیوزلند"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_new_zealand, 64, "نیوزلند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اتریش"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_austria, 43, "اتریش",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("عربستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saudi_arabia, 966, "عربستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مکزیک"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mexico, 52, "مکزیک",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اسپانیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_spain, 34, "اسپانیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("الجزائر"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 213, "الجزائر",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اسلوونی"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 386, "اسلوونی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کرواسی"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 385, "کرواسی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بلاروس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belarus, 375, "بلاروس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("فنلاند"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_finland, 358, "فنلاند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سوئد"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sweden, 46, "سوئد",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گرجستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_georgia, 995, "گرجستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اتیوپی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ethiopia, 251, "اتیوپی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("زامبیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zambia, 260, "زامبیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پاکستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_pakistan, 92, "پاکستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("تایلند"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_thailand, 66, "تایلند",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("تایوان"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 886, "تایوان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پرو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_peru, 51, "پرو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گینه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_papua_new_guinea, 675, "گینه نو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("چاد"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chad, 235, "چاد",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مالی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mali, 223, "مالی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بنگلادش"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bangladesh, 880, "بنگلادش",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گینه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea, 224, "گینه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سری لانکا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sri_lanka, 94, "سری لانکا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ازبکستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uzbekistan, 998, "ازبکستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سنگال"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_senegal, 221, "سنگال",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کلمبیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_colombia, 57, "کلمبیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ونزوئلا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_venezuela_state, 58, "ونزوئلا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("هائیتی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_haiti, 509, "هائیتی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ایران"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 98, "ایران",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مولداوی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_moldova, 373, "مولداوی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("موزامبیک"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mozambique, 258, "موزامبیک",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گامبیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_gambia, 220, "گامبیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("افغانستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_afghanistan, 93, "افغانستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اوگاندا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uganda, 256, "اوگاندا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("استرالیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_australia_converted, 61, "استرالیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("امارات متحده"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_arab_emirates, 971, "امارات متحده",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("شیلی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chile, 56, "شیلی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گویان"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 592, "گویان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نپال"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nepal, 977, "نپال",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("جیبوتی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_djibouti, 253, "جیبوتی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گابن"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_gabon, 241, "گابن",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نیکاراگوئه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nicaragua, 505, "نیکاراگوئه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بوسنی و هزرگوین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bosnia_and_herzegovina, 387, "بوسنی و هزرگوین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("توگو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_togo, 228, "توگو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آنگولا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_angola, 244, "آنگولا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بولیوی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bolivia, 591, "بولیوی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اروگوئه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uruguay, 598, "اروگوئه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اکوادور"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ecuador, 593, "اکوادور",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ایتالیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_italy, 39, "ایتالیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گواتمالا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guatemala, 502, "گواتمالا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("تونس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tunisia, 216, "تونس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مجارستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hungary, 36, "مجارستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کویت"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kuwait, 965, "کویت",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آذربایجان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_azerbaijan, 994, "آذربایجان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سودان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sudan, 249, "سودان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("رواندا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_rwanda, 250, "رواندا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کیپ ورد"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cape_verde, 238, "کیپ ورد",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مارتینیک"))
                listViewCountries.add(new ListViewCountry(R.drawable.snake_flag_of_martinique, 596, "مارتینیک",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("کاستاریکا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_costa_rica_state, 506, "کاستاریکا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("هندوراس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_honduras_2008_olympics, 504, "هندوراس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("السالوادور"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_el_salvador, 503, "السالوادور",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بوروندی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burundi, 257, "بوروندی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گینه بیسائو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea_bissau, 245, "گینه بیسائو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ترکمنستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkmenistan, 993, "ترکمنستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سوریه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_syria, 963, "سوریه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ترینیداد و توباگو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_trinidad_and_tobago, 868, "ترینیداد و توباگو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سنت لوسیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_lucia, 1758, "سنت لوسیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پورتوریکو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_puerto_rico, 1, "پورتوریکو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بلغارستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bulgaria, 359, "بلغارستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بلژیک"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belgium, 32, "بلژیک",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("جمهوری چک"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_czech_republic, 420, "جمهوری چک",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نروژ"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_norway, 47, "نروژ",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("پرتغال"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_portugal, 351, "پرتغال",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لوکزامبورگ"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_luxembourg, 352, "لوکزامبورگ",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("ارمنستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_armenia, 374, "ارمنستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("جامائیکا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jamaica, 1876, "جامائیکا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("جمهوری دومینیکن"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_dominican_republic, 809, "جمهوری دومینیکن",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بوتان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bhutan, 975, "بوتان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اردن"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jordan, 962, "اردن",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("عمان"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 968, "عمان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بحرین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bahrain, 973, "بحرین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("قطر"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_qatar, 974, "قطر",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مغولستان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mongolia, 976, "مغولستان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مالدیو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_maldives, 960, "مالدیو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لیبی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_libya, 218, "لیبی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("موریتانی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mauritania, 222, "موریتانی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بورکینافاسو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burkina_faso, 226, "بورکینافاسو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نیجر"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria, 227, "نیجر",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بنین"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_benin, 229, "بنین",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لیبریا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_liberia, 231, "لیبریا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سومالی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_somalia, 252, "سومالی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("زیمباوه"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zimbabwe, 263, "زیمباوه",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("نامیبیا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_namibia, 264, "نامیبیا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("مالاوی"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malawi, 265, "مالاوی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("لسوتو"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lesotho, 266, "لسوتو",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("بوتسوانا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_botswana, 267, "بوتسوانا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("اسواتینی"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 268, "اسواتینی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سوئیس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_switzerland, 41, "سوئیس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("گوادلوپ"))
                listViewCountries.add(new ListViewCountry(R.drawable.unofficial_flag_of_guadeloupe_local, 590, "گوادلوپ",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("باربادوس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_barbados, 1246, "باربادوس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آنتیگوآ و باربودا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_antigua_and_barbuda, 1268, "آنتیگوآ و باربودا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سنت کیتس و نویس"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_kitts_and_nevis, 1869, "سنت کیتس و نویس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سنت وینسنت"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_vincent_and_the_grenadines, 1784, "سنت وینسنت",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("آنگویلا"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_anguilla, 1264, "آنگویلا",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("سنگاپور"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_singapore, 65, "سنگاپور",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("تاجیسکتان"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tajikistan, 992, "تاجیسکتان",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("تمیور شرقی"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 670, "تمیور شرقی",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("قبرس"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 357, "قبرس",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));




        }
        /*
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,7,"روسیه"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,380,"اکراین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kazakhstan,7,"قزاقستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_peoples_republic_of_china,86,"چین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_philippines,63,"فیلیپین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_myanmar,95,"میانمار"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_indonesia,62,"اندونزی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malaysia,60,"مالزی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kenya,254,"کنیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,255,"تانزانیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_vietnam,84,"ویتنام"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_kingdom,44,"انگلستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,371,"لتونی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_romania,40,"رومانی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_estonia,372,"استونی"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"آمریکا"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"آمریکا ویژه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kyrgyzstan,996,"قرقیزستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_france,33,"فرانسه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_palestine___long_triangle,972,"فلسطین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cambodia,855,"کامبوج"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_macau,853,"ماکائو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hong_kong,852,"هنگ کنگ"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_brazil,55,"برزیل"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_poland,48,"لهستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,595,"پاراگوئه"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,31,"هلند"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,370,"لیتوانی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_madagascar,261,"ماداگاسکار"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_democratic_republic_of_the_congo,243,"کنگو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria,234,"نیجریه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_south_africa,27,"آفریقا جنوبی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_panama,507,"پاناما"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_egypt,20,"مصر"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_india,91,"هند"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ireland,353,"ایرلند"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,225,"ساحل عاج"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_serbia,381,"صربستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_laos,856,"لائوس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_morocco,212,"مراکش"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_yemen,967,"یمن"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ghana,233,"غنا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_canada_pantone,1,"کانادا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_argentina,54,"آرژانتین"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,964,"عراق"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_germany,49,"آلمان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cameroon,237,"کامرون"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkey,90,"ترکیه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_new_zealand,64,"نیوزلند"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_austria,43,"اتریش"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saudi_arabia,966,"عربستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mexico,52,"مکزیک"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_spain,34,"اسپانیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,213,"الجزائر"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,386,"اسلوونی"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,385,"کرواسی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belarus,375,"بلاروس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_finland,358,"فنلاند"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sweden,46,"سوئد"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_georgia,995,"گرجستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ethiopia,251,"اتیوپی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zambia,260,"زامبیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_pakistan,92,"پاکستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_thailand,66,"تایلند"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,886,"تایوان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_peru,51,"پرو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_papua_new_guinea,675,"گینه نو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chad,235,"چاد"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mali,223,"مالی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bangladesh,880,"بنگلادش"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea,224,"گینه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sri_lanka,94,"سری لانکا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uzbekistan,998,"ازبکستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_senegal,221,"سنگال"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_colombia,57,"کلمبیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_venezuela_state,58,"ونزوئلا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_haiti,509,"هائیتی"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,98,"ایران"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_moldova,373,"مولداوی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mozambique,258,"موزامبیک"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_gambia,220,"گامبیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_afghanistan,93,"افغانستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uganda,256,"اوگاندا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_australia_converted,61,"استرالیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_arab_emirates,971,"امارات متحده"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chile,56,"شیلی"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,592,"گویان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nepal,977,"نپال"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_djibouti,253,"جیبوتی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_gabon,241,"گابن"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nicaragua,505,"نیکاراگوئه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bosnia_and_herzegovina,387,"بوسنی و هزرگوین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_togo,228,"توگو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_angola,244,"آنگولا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bolivia,591,"بولیوی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uruguay,598,"اروگوئه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ecuador,593,"اکوادور"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_italy,39,"ایتالیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guatemala,502,"گواتمالا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tunisia,216,"تونس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hungary,36,"مجارستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kuwait,965,"کویت"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_azerbaijan,994,"آذربایجان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sudan,249,"سودان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_rwanda,250,"رواندا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cape_verde,238,"کیپ ورد"));
        listViewCountries.add(new ListViewCountry(R.drawable.snake_flag_of_martinique,596,"مارتینیک"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_costa_rica_state,506,"کاستاریکا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_honduras_2008_olympics,504,"هندوراس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_el_salvador,503,"السالوادور"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burundi,257,"بوروندی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea_bissau,245,"گینه بیسائو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkmenistan,993,"ترکمنستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_syria,963,"سوریه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_trinidad_and_tobago,868,"ترینیداد و توباگو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_lucia,1758,"سنت لوسیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_puerto_rico,1,"پورتوریکو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bulgaria,359,"بلغارستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belgium,32,"بلژیک"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_czech_republic,420,"جمهوری چک"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_norway,47,"نروژ"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_portugal,351,"پرتغال"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_luxembourg,352,"لوکزامبورگ"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_armenia,374,"ارمنستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jamaica,1876,"جامائیکا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_dominican_republic,809,"جمهوری دومینیکن"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bhutan,975,"بوتان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jordan,962,"اردن"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,968,"عمان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bahrain,973,"بحرین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_qatar,974,"قطر"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mongolia,976,"مغولستان"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_maldives,960,"مالدیو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_libya,218,"لیبی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mauritania,222,"موریتانی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burkina_faso,226,"بورکینافاسو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria,227,"نیجر"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_benin,229,"بنین"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_liberia,231,"لیبریا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_somalia,252,"سومالی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zimbabwe,263,"زیمباوه"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_namibia,264,"نامیبیا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malawi,265,"مالاوی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lesotho,266,"لسوتو"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_botswana,267,"بوتسوانا"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,268,"اسواتینی"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_switzerland,41,"سوئیس"));
        listViewCountries.add(new ListViewCountry(R.drawable.unofficial_flag_of_guadeloupe_local,590,"گوادلوپ"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_barbados,1246,"باربادوس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_antigua_and_barbuda,1268,"آنتیگوآ و باربودا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_kitts_and_nevis,1869,"سنت کیتس و نویس"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_vincent_and_the_grenadines,1784,"سنت وینسنت"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_anguilla,1264,"آنگویلا"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_singapore,65,"سنگاپور"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tajikistan,992,"تاجیسکتان"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,670,"تمیور شرقی"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,357,"قبرس"));
*/
    }

    public void getToken(IAfterSucc iAfterSucc){
        Presenter.get_global().GetAction(new IView<Token>() {
            @Override
            public void SendRequest() {

            }

            @Override
            public void OnSucceed(Token object) {
                Toast.makeText(MainActivity.getGlobal(), object.getToken(), Toast.LENGTH_SHORT).show();
                mLocalData.SetToken(MainActivity.getGlobal(),object.getToken());
                iAfterSucc.Success();


            }

            @Override
            public void OnError(String error, int statusCode) {
                Toast.makeText(MainActivity.getGlobal(), "not Token", Toast.LENGTH_SHORT).show();
            }
        },"","","",Token.class);

    }

    public void SortHander(int i){
        if(i==0){
            LowSort.setTextColor(Color.WHITE);
            HighSort.setTextColor(Color.parseColor("#56a1ff"));
            HighSort.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_bluelight);
            LowSort.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_blue);
            Collections.sort(listViewCountries);
            customAdapter1.notifyMyDataSetChanged();
        }
        else{
            HighSort.setTextColor(Color.WHITE);
            LowSort.setTextColor(Color.parseColor("#56a1ff"));
            LowSort.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_bluelight);
            HighSort.setBackgroundResource(R.drawable.shape_cardview_shomaremajazi_blue);
            Collections.reverse(listViewCountries);
            customAdapter1.notifyMyDataSetChanged();
        }

    }
}
