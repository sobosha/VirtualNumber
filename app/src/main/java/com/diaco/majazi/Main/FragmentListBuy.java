package com.diaco.majazi.Main;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.JsonClass.getCountryResult;
import com.diaco.majazi.JsonClass.listOfpayment;
import com.diaco.majazi.JsonClass.payments;
import com.diaco.majazi.Main.Main.RelItemService;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Room.VirtualPhone;
import com.diaco.majazi.Setting.CustomClasses.CustomAdapter;
import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.SplitText;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.menu;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FragmentListBuy extends CustomFragment {

    RelativeLayout textView_plusmoney;
    TextView Money,Title;
    RecyclerView recyclerListBuy;
    CustomAdapter customAdapter;
    List<ListOfBuy> listBuy;
    ProgressBar progressBar;
    @Override
    public int layout() {
        return R.layout.fragment_buylist;
    }

    @Override
    public void onCreateMyView() throws ParseException {
        progressBar=parent.findViewById(R.id.ProgressListBuy);
        progressBar.setVisibility(View.VISIBLE);
        listBuy=new ArrayList<>();
        textView_plusmoney=parent.findViewById(R.id.btn_Plus_money);
        textView_plusmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                MainActivity.getGlobal().FinishFragStartFrag(new menu(1));
            }
        });
        recyclerListBuy=parent.findViewById(R.id.recycler_Listbuy);
        Money=parent.findViewById(R.id.textview_Money);
        Money.setText(SplitText.GetOKPrice(mLocalData.getCoin(getContext())+""));
        Title=parent.findViewById(R.id.textview_title_ListBuy);
        Title.setText("لیست خرید");

        Presenter.get_global().PostAction(new IView<listOfpayment>() {
            @Override
            public void SendRequest() {

            }

            @Override
            public void OnSucceed(listOfpayment object) {
                progressBar.setVisibility(View.GONE);
                ListHandler(object);
                customAdapter = new CustomAdapter.RecyclerBuilder<ListOfBuy, RelItemService>(getContext(), recyclerListBuy, listBuy)
                        .setView(() -> new RelItemService(getContext(), "ListBuy"))
                        .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartListBuy(list.get(position)))
                        .orientation(RecyclerView.VERTICAL)
                        .build();
            }

            @Override
            public void OnError(String error, int statusCode) {

            }
        }, "api.php?action=mybuy", "", "", MainActivity.getGlobal().getmessage("mybuy"), listOfpayment.class);



    }

    private void ListHandler(listOfpayment object){
        for(int i=0;i<object.getPayments().size();i++){
            payments item=object.getPayments().get(i);
            if(item.getService().contains("تلگرام"))
                CountryHandler(R.drawable.ic_telegram_2,item);
            else if(item.getService().contains("اینستاگرام"))
                CountryHandler(R.drawable.ic_instagram_5,item);
            else if(item.getService().contains("واتساپ"))
                CountryHandler(R.drawable.ic_whatsapp,item);
            else if(item.getService().contains("گوگل"))
                CountryHandler(R.drawable.ic_google,item);
            else if(item.getService().contains("وایبر"))
                CountryHandler(R.drawable.ic_viber,item);
            else if(item.getService().contains("ویچت"))
                CountryHandler(R.drawable.ic_wechat,item);
            else if(item.getService().contains("فیسبوک"))
                CountryHandler(R.drawable.ic_facebook,item);
            else if(item.getService().contains("توییتر"))
                CountryHandler(R.drawable.ic_twitter_1,item);
            else if(item.getService().contains("یاهو"))
                CountryHandler(R.drawable.ic_yahoo_1,item);
            else if(item.getService().contains("ایمو"))
                CountryHandler(R.drawable.ic_imo,item);
            else if(item.getService().contains("پیپال"))
                CountryHandler(R.drawable.ic_paypal,item);
            else if(item.getService().contains("لاین"))
                CountryHandler(R.drawable.ic_line,item);
            else if(item.getService().contains("ماکروسافت"))
                CountryHandler(R.drawable.ic_microsoft,item);
            else if(item.getService().contains("آمازون"))
                CountryHandler(R.drawable.ic_amazon_icon_1,item);

        }
    }

    private void CountryHandler(int ic, payments item){
        if(item.getCountry().contains("قزاقستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_kazakhstan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("روسیه"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("اکراین"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("چین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_peoples_republic_of_china,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("فیلیپین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_philippines,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("میانمار"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_myanmar,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("اندونزی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_indonesia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("مالزی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_malaysia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("کنیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_kenya,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("تانزانیا"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("ویتنام"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_vietnam,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("انگلستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_united_kingdom,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("لتونی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_lithuania,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("رومانی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_romania,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("استونی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_estonia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("آمریکا"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if(item.getCountry().contains("آمریکا ویژه"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("قرقیزستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_kyrgyzstan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("فرانسه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_france,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("فلسطین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_palestine___long_triangle,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کامبوج"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_cambodia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ماکائو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_macau,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("هنگ کنگ"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_hong_kong,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("برزیل"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_brazil,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لهستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_poland,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پاراگوئه"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("هلند"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لیتوانی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_lithuania,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ماداگاسکار"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_madagascar,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کنگو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_democratic_republic_of_the_congo,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نیجریه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_nigeria,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آفریقا جنوبی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_south_africa,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پاناما"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_panama,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مصر"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_egypt,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("هند"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_india,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ایرلند"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_ireland,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ساحل عاج"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("صربستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_serbia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لائوس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_laos,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مراکش"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_morocco,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("یمن"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_yemen,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("غنا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_ghana,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کانادا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_canada_pantone,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آرژانتین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_argentina,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("عراق"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آلمان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_germany,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کامرون"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_cameroon,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ترکیه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_turkey,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نیوزلند"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_new_zealand,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اتریش"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_austria,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("عربستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_saudi_arabia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مکزیک"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_mexico,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اسپانیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_spain,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("الجزائر"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اسلوونی"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کرواسی"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بلاروس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_belarus,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("فنلاند"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_finland,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سوئد"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_sweden,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گرجستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_georgia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اتیوپی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_ethiopia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("زامبیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_zambia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پاکستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_pakistan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("تایلند"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_thailand,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("تایوان"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پرو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_peru,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گینه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_papua_new_guinea,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("چاد"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_chad,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مالی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_mali,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بنگلادش"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bangladesh,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گینه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_guinea,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سری لانکا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_sri_lanka,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ازبکستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_uzbekistan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سنگال"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_senegal,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کلمبیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_colombia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ونزوئلا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_venezuela_state,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("هائیتی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_haiti,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ایران"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مولداوی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_moldova,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("موزامبیک"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_mozambique,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گامبیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_gambia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("افغانستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_afghanistan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اوگاندا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_uganda,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("استرالیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_australia_converted,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("امارات متحده"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_united_arab_emirates,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("شیلی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_chile,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گویان"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نپال"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_nepal,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("جیبوتی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_djibouti,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گابن"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_gabon,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نیکاراگوئه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_nicaragua,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بوسنی و هزرگوین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bosnia_and_herzegovina,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("توگو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_togo,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آنگولا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_angola,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بولیوی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bolivia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اروگوئه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_uruguay,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اکوادور"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_ecuador,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ایتالیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_italy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گواتمالا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_guatemala,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("تونس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_tunisia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مجارستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_hungary,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کویت"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_kuwait,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آذربایجان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_azerbaijan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سودان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_sudan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("رواندا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_rwanda,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کیپ ورد"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_cape_verde,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مارتینیک"))
            listBuy.add(new ListOfBuy(R.drawable.snake_flag_of_martinique,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("کاستاریکا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_costa_rica_state,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("هندوراس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_honduras_2008_olympics,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("السالوادور"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_el_salvador,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بوروندی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_burundi,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گینه بیسائو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_guinea_bissau,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ترکمنستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_turkmenistan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سوریه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_syria,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ترینیداد و توباگو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_trinidad_and_tobago,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سنت لوسیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_saint_lucia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پورتوریکو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_puerto_rico,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بلغارستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bulgaria,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بلژیک"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_belgium,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("جمهوری چک"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_czech_republic,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نروژ"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_norway,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("پرتغال"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_portugal,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لوکزامبورگ"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_luxembourg,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("ارمنستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_armenia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("جامائیکا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_jamaica,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("جمهوری دومینیکن"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_the_dominican_republic,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بوتان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bhutan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اردن"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_jordan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("عمان"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بحرین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_bahrain,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("قطر"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_qatar,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مغولستان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_mongolia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مالدیو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_maldives,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لیبی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_libya,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("موریتانی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_mauritania,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بورکینافاسو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_burkina_faso,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نیجر"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_nigeria,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بنین"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_benin,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لیبریا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_liberia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سومالی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_somalia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("زیمباوه"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_zimbabwe,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("نامیبیا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_namibia,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("مالاوی"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_malawi,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("لسوتو"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_lesotho,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("بوتسوانا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_botswana,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("اسواتینی"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سوئیس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_switzerland,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("گوادلوپ"))
            listBuy.add(new ListOfBuy(R.drawable.unofficial_flag_of_guadeloupe_local,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("باربادوس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_barbados,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آنتیگوآ و باربودا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_antigua_and_barbuda,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سنت کیتس و نویس"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_saint_kitts_and_nevis,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سنت وینسنت"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_saint_vincent_and_the_grenadines,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("آنگویلا"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_anguilla,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("سنگاپور"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_singapore,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("تاجیسکتان"))
            listBuy.add(new ListOfBuy(R.drawable.flag_of_tajikistan,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("تمیور شرقی"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));
        else if (item.getCountry().contains("قبرس"))
            listBuy.add(new ListOfBuy(R.drawable.lifebuoy,item.getCountry(),item.getNumber(),item.getAmount(),ic));

    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        MainActivity.getGlobal().FinishFragStartFrag(new menu(false));
    }
}
