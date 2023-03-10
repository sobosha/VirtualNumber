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
            title.setText("???????????? ?? ????????????");
            try {
                Money.setText(SplitText.GetOKPrice(mLocalData.getCoin(getContext())+""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(pages==1){
            type = "service";
            (parent.findViewById(R.id.recycler_services)).setVisibility(View.VISIBLE);
            title.setText("?????????? ????");
            progressBar=parent.findViewById(R.id.progress_load);
            Sort.setVisibility(View.GONE);
        }
        else if(pages==2){
            listCountryWithAmount=new ArrayList<>();
            type = "number";
            (parent.findViewById(R.id.recycler_services)).setVisibility(View.VISIBLE);
            title.setText("???????? ????");
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
                            }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDECD ???????? ?????????? ??????????"), getNumberResult.class);

                        }
                    });
                    Presenter.get_global().PostAction(new IView<getNumberResult>() {
                        @Override
                        public void SendRequest() {

                        }

                        @Override
                        public void OnSucceed(getNumberResult object) {
                            String text=object.getText();
                            //???????????? ?????? : 50000 ??????????
                            String UserId=text.substring(text.indexOf("?????? :"),text.indexOf("?????????????? ?????? "));
                            UserId=UserId.replaceAll("?????????? : ","");
                            UserId=UserId.replaceAll("?????? : ","");
                            UserId=UserId.replaceAll("\n","");
                            UserId=UserId.replaceAll("\uD83C\uDD94","");
                            UserId=UserId.replaceAll("\uD83D\uDCA1","");
                            UserId=UserId.replaceAll(" ","");
                            MainActivity.getGlobal().UserId=UserId;
                            text=text.substring(text.indexOf("???????????? ?????? : "),text.indexOf(" ??????????"));
                            text=text.replaceAll("???????????? ?????? : ","");
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
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 ???????? ????????????"), getNumberResult.class);
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
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDECD ???????? ?????????? ??????????"), getNumberResult.class);
                    Presenter.get_global().PostAction(new IView<getNumberResult>() {
                        @Override
                        public void SendRequest() {

                        }

                        @Override
                        public void OnSucceed(getNumberResult object) {
                           String text=object.getText();
                    //???????????? ?????? : 50000 ??????????
                            String UserId=text.substring(text.indexOf("?????? :"),text.indexOf("?????????????? ?????? "));
                            UserId=UserId.replaceAll("?????????? : ","");
                            UserId=UserId.replaceAll("?????? : ","");
                            UserId=UserId.replaceAll("\n","");
                            UserId=UserId.replaceAll("\uD83C\uDD94","");
                            UserId=UserId.replaceAll("\uD83D\uDCA1","");
                            UserId=UserId.replaceAll(" ","");
                            MainActivity.getGlobal().UserId=UserId;
                            text=text.substring(text.indexOf("???????????? ?????? : "),text.indexOf(" ??????????"));
                            text=text.replaceAll("???????????? ?????? : ","");
                            Money.setText(SplitText.GetOKPrice(text));
                            //Toast.makeText(MainActivity.getGlobal(), text, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 ???????? ????????????"), getNumberResult.class);

                }
            }
            else{
                MainActivity.getGlobal().showSnackBar("warning","???????? ?????????????? ?????? ???? ???????? ????????",1000);
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
                        //???????????? ?????? : 50000 ??????????
                        String UserId=text.substring(text.indexOf("?????? :"),text.indexOf("?????????????? ?????? "));
                        UserId=UserId.replaceAll("?????????? : ","");
                        UserId=UserId.replaceAll("?????? : ","");
                        UserId=UserId.replaceAll("\n","");
                        UserId=UserId.replaceAll("\uD83C\uDD94","");
                        UserId=UserId.replaceAll("\uD83D\uDCA1","");
                        UserId=UserId.replaceAll(" ","");
                        MainActivity.getGlobal().UserId=UserId;
                        text=text.substring(text.indexOf("???????????? ?????? : "),text.indexOf(" ??????????"));
                        text=text.replaceAll("???????????? ?????? : ","");
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
                }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage("\uD83D\uDC64 ???????? ????????????"), getNumberResult.class);
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
                MainActivity.getGlobal().showSnackBar("warning","???????? ?????????????? ?????? ???? ???????? ????????",1000);
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
            if(ServerName.contains("????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_telegram_2,ServerName,"1",R.drawable.shape_telegram));
            else if(ServerName.contains("????????????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_instagram_5,ServerName,"2",R.drawable.shape_instagram));
            else if(ServerName.contains("????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_whatsapp,ServerName,"3",R.drawable.shape_whatsapp));
            else if(ServerName.contains("????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_google,ServerName,"6",R.drawable.shape_google));
            else if(ServerName.contains("??????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_viber,ServerName,"4",R.drawable.shape_viber));
            else if(ServerName.contains("????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_wechat,ServerName,"5",R.drawable.shape_wechat));
            else if(ServerName.contains("????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_facebook,ServerName,"7",R.drawable.shape_facebook));
            else if(ServerName.contains("????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_twitter_1,ServerName,"8",R.drawable.shape_tweeter));
            else if(ServerName.contains("????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_yahoo_1,ServerName,"11",R.drawable.shape_yahoo));
            else if(ServerName.contains("????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_imo,ServerName,"18",R.drawable.shape_imo));
            else if(ServerName.contains("??????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_paypal,ServerName,"15",R.drawable.shape_paypal));
            else if(ServerName.contains("????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_line,ServerName,"10",R.drawable.shape_line));
            else if(ServerName.contains("??????????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_microsoft,ServerName,"9",R.drawable.shape_micro));
            else if(ServerName.contains("????????????"))
                listviewServices.add(new ListviewServices(R.drawable.ic_amazon_icon_1,ServerName,"21",R.drawable.shape_amazon));



        }
    }
    private void ListAddCountry(CountryAmount result){
        listViewCountries=new ArrayList<>();
        for(int i=0;i<result.getServices().size();i++) {
            String ServerName=result.getServices().get(i).getName()+" "+result.getServices().get(i).getEmoji();
            if(ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kazakhstan,7,"????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,7,"??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,380,"????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_peoples_republic_of_china,86,"??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_philippines,63,"??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_myanmar,95,"??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_indonesia,62,"??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malaysia,60,"??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kenya,254,"????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,255,"????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_vietnam,84,"????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_kingdom,44,"????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,371,"??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_romania,40,"????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_estonia,372,"????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if(ServerName.contains("???????????? ????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"???????????? ????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kyrgyzstan, 996, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_france, 33, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_palestine___long_triangle, 972, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cambodia, 855, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_macau, 853, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hong_kong, 852, "?????? ??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_brazil, 55, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_poland, 48, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 595, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 31, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania, 370, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_madagascar, 261, "????????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_democratic_republic_of_the_congo, 243, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria, 234, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????????? ??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_south_africa, 27, "???????????? ??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_panama, 507, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_egypt, 20, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_india, 91, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ireland, 353, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????? ??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 225, "???????? ??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_serbia, 381, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_laos, 856, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_morocco, 212, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_yemen, 967, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ghana, 233, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_canada_pantone, 1, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_argentina, 54, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 964, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_germany, 49, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cameroon, 237, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkey, 90, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_new_zealand, 64, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_austria, 43, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saudi_arabia, 966, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mexico, 52, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_spain, 34, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 213, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 386, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 385, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belarus, 375, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_finland, 358, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sweden, 46, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_georgia, 995, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ethiopia, 251, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zambia, 260, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_pakistan, 92, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_thailand, 66, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 886, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_peru, 51, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_papua_new_guinea, 675, "???????? ????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chad, 235, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mali, 223, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bangladesh, 880, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea, 224, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sri_lanka, 94, "?????? ??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uzbekistan, 998, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_senegal, 221, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_colombia, 57, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_venezuela_state, 58, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_haiti, 509, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 98, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_moldova, 373, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mozambique, 258, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_gambia, 220, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_afghanistan, 93, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uganda, 256, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_australia_converted, 61, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????????? ??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_arab_emirates, 971, "???????????? ??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chile, 56, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 592, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nepal, 977, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_djibouti, 253, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_gabon, 241, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nicaragua, 505, "????????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????????? ?? ??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bosnia_and_herzegovina, 387, "?????????? ?? ??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_togo, 228, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_angola, 244, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bolivia, 591, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uruguay, 598, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ecuador, 593, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_italy, 39, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guatemala, 502, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tunisia, 216, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hungary, 36, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kuwait, 965, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_azerbaijan, 994, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sudan, 249, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_rwanda, 250, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cape_verde, 238, "?????? ??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.snake_flag_of_martinique, 596, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_costa_rica_state, 506, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_honduras_2008_olympics, 504, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_el_salvador, 503, "????????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burundi, 257, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????? ????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea_bissau, 245, "???????? ????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkmenistan, 993, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_syria, 963, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????????????? ?? ????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_trinidad_and_tobago, 868, "???????????????? ?? ????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_lucia, 1758, "?????? ??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_puerto_rico, 1, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bulgaria, 359, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belgium, 32, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????????? ????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_czech_republic, 420, "???????????? ????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_norway, 47, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_portugal, 351, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_luxembourg, 352, "????????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_armenia, 374, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jamaica, 1876, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("???????????? ????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_dominican_republic, 809, "???????????? ????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bhutan, 975, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jordan, 962, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 968, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bahrain, 973, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_qatar, 974, "??????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mongolia, 976, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_maldives, 960, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_libya, 218, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mauritania, 222, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burkina_faso, 226, "??????????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria, 227, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_benin, 229, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_liberia, 231, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_somalia, 252, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zimbabwe, 263, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_namibia, 264, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malawi, 265, "????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lesotho, 266, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_botswana, 267, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 268, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_switzerland, 41, "??????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.unofficial_flag_of_guadeloupe_local, 590, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_barbados, 1246, "????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????????????? ?? ??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_antigua_and_barbuda, 1268, "?????????????? ?? ??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ???????? ?? ????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_kitts_and_nevis, 1869, "?????? ???????? ?? ????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????? ????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_vincent_and_the_grenadines, 1784, "?????? ????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_anguilla, 1264, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_singapore, 65, "??????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("??????????????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tajikistan, 992, "??????????????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("?????????? ????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 670, "?????????? ????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));
            else if (ServerName.contains("????????"))
                listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy, 357, "????????",ServerName,result.getServices().get(i).getAmount(),result.getServices().get(i).getStats(),result.getServices().get(i).getAreacode()));




        }
        /*
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,7,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,380,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kazakhstan,7,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_peoples_republic_of_china,86,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_philippines,63,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_myanmar,95,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_indonesia,62,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malaysia,60,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kenya,254,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,255,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_vietnam,84,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_kingdom,44,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,371,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_romania,40,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_estonia,372,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,1,"???????????? ????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kyrgyzstan,996,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_france,33,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_palestine___long_triangle,972,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cambodia,855,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_macau,853,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hong_kong,852,"?????? ??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_brazil,55,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_poland,48,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,595,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,31,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lithuania,370,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_madagascar,261,"????????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_democratic_republic_of_the_congo,243,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria,234,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_south_africa,27,"???????????? ??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_panama,507,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_egypt,20,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_india,91,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ireland,353,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,225,"???????? ??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_serbia,381,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_laos,856,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_morocco,212,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_yemen,967,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ghana,233,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_canada_pantone,1,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_argentina,54,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,964,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_germany,49,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cameroon,237,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkey,90,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_new_zealand,64,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_austria,43,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saudi_arabia,966,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mexico,52,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_spain,34,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,213,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,386,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,385,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belarus,375,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_finland,358,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sweden,46,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_georgia,995,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ethiopia,251,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zambia,260,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_pakistan,92,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_thailand,66,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,886,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_peru,51,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_papua_new_guinea,675,"???????? ????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chad,235,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mali,223,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bangladesh,880,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea,224,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sri_lanka,94,"?????? ??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uzbekistan,998,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_senegal,221,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_colombia,57,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_venezuela_state,58,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_haiti,509,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,98,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_moldova,373,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mozambique,258,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_gambia,220,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_afghanistan,93,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uganda,256,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_australia_converted,61,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_united_arab_emirates,971,"???????????? ??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_chile,56,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,592,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nepal,977,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_djibouti,253,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_gabon,241,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nicaragua,505,"????????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bosnia_and_herzegovina,387,"?????????? ?? ??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_togo,228,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_angola,244,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bolivia,591,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_uruguay,598,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_ecuador,593,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_italy,39,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guatemala,502,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tunisia,216,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_hungary,36,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_kuwait,965,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_azerbaijan,994,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_sudan,249,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_rwanda,250,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_cape_verde,238,"?????? ??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.snake_flag_of_martinique,596,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_costa_rica_state,506,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_honduras_2008_olympics,504,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_el_salvador,503,"????????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burundi,257,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_guinea_bissau,245,"???????? ????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_turkmenistan,993,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_syria,963,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_trinidad_and_tobago,868,"???????????????? ?? ????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_lucia,1758,"?????? ??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_puerto_rico,1,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bulgaria,359,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_belgium,32,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_czech_republic,420,"???????????? ????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_norway,47,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_portugal,351,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_luxembourg,352,"????????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_armenia,374,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jamaica,1876,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_the_dominican_republic,809,"???????????? ????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bhutan,975,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_jordan,962,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,968,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_bahrain,973,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_qatar,974,"??????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mongolia,976,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_maldives,960,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_libya,218,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_mauritania,222,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_burkina_faso,226,"??????????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_nigeria,227,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_benin,229,"????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_liberia,231,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_somalia,252,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_zimbabwe,263,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_namibia,264,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_malawi,265,"????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_lesotho,266,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_botswana,267,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,268,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_switzerland,41,"??????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.unofficial_flag_of_guadeloupe_local,590,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_barbados,1246,"????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_antigua_and_barbuda,1268,"?????????????? ?? ??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_kitts_and_nevis,1869,"?????? ???????? ?? ????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_saint_vincent_and_the_grenadines,1784,"?????? ????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_anguilla,1264,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_singapore,65,"??????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.flag_of_tajikistan,992,"??????????????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,670,"?????????? ????????"));
        listViewCountries.add(new ListViewCountry(R.drawable.lifebuoy,357,"????????"));
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
