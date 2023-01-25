package com.diaco.majazi.Dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.majazi.Core.IView;
import com.diaco.majazi.Core.Presenter;
import com.diaco.majazi.JsonClass.getCountryResult;
import com.diaco.majazi.JsonClass.getNumberResult;
import com.diaco.majazi.Main.ListViewCountry;
import com.diaco.majazi.Main.ListviewServices;
import com.diaco.majazi.Main.Main.RelItemService;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.R;
import com.diaco.majazi.Setting.CustomClasses.CustomAdapter;
import com.diaco.majazi.Setting.CustomClasses.CustomRel;
import com.diaco.majazi.Setting.SplitText;
import com.diaco.majazi.Setting.mAnimation;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.menu;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Buy extends CustomRel {
    TextView buy,cancle,Desc;
    ProgressBar ProgressBuy;
    RelativeLayout RelDialog;
    ImageView Basket;
    boolean firstClick=false;
    public Buy(Context context,ListViewCountry item) {
        super(context, R.layout.dialog_buy);
        buy(context,item);
    }

    private void buy(Context context, ListViewCountry item) {
        Basket=findViewById(R.id.ImageBasket);
        buy=findViewById(R.id.btn_ok_dialog);
        cancle=findViewById(R.id.btn_cancle_dialog);
        Desc=findViewById(R.id.TextviewDesc);
        ProgressBuy=findViewById(R.id.ProgressBuy);
        RelDialog=findViewById(R.id.BuyDialog);
        String temp[]=item.getServerCode().split(" ");
        String temp2=temp[1]+" "+temp[0];
        Desc.setText("آیا از خرید شماره مجازی کشور "+item.getName()+" با قیمت "+ SplitText.GetOKPrice(item.getAmount()) +" تومان اطمینان دارید؟");
        RelDialog.setVisibility(VISIBLE);
        buy.setText("خرید");
        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                MainActivity.getGlobal().HideMyDialog();
            }
        });
        buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if(!firstClick) {
                    RelDialog.setVisibility(GONE);
                    Basket.setVisibility(GONE);
                    buy.setText("رفتن به فروشگاه");
                    firstClick=true;
                    RelDialog.getLayoutParams().height= (int) MainActivity.getGlobal().getResources().getDimension(R.dimen._250sdp);
                    Presenter.get_global().PostAction(new IView<getCountryResult>() {
                        @Override
                        public void SendRequest() {

                        }

                        @Override
                        public void OnSucceed(getCountryResult object) {
                            ProgressBuy.setVisibility(GONE);
                            String temp = object.getText();
                            if (temp.contains("شماره مجازی شما : ")) {
                                temp = temp.substring(temp.indexOf("شماره مجازی شما : "), temp.indexOf("\n\n"));
                                temp = temp.replaceAll("شماره مجازی شما : ", "");
                                //Toast.makeText(getContext(), "موفق   "+object.getText(), Toast.LENGTH_SHORT).show();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
                                Date date = new Date();
                                String timeStamp = formatter.format(date);
                                mLocalData.setTimeVirtualPhone(MainActivity.getGlobal().getApplicationContext(), timeStamp, "00:10:00", temp);
                                menu menu = new menu(false);
                                //menu.setResult(object);
                                MainActivity.getGlobal().setFlag_ExistVirtualphone(true);
                                MainActivity.getGlobal().HideMyDialog();
                                RelDialog.setVisibility(GONE);
                                mLocalData.setServicesType(getContext(),MainActivity.getGlobal().Service);
                                mLocalData.setReciveCode(getContext(),"");
                                MainActivity.getGlobal().FinishFragStartFrag(menu);
                            } else {
                                temp = temp.substring(0, temp.indexOf("\n\n"));
                                Desc.setText(temp);
                                RelDialog.setVisibility(VISIBLE);
                            }
//                        progressBar.setVisibility(View.GONE);
//                    /*Toast.makeText(MainActivity.getGlobal(), ""+object.getText(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.getGlobal(), "Succes", Toast.LENGTH_SHORT).show();*/
//                        ListAddCountry(object);
//                        customAdapter1 = new CustomAdapter.RecyclerBuilder<ListViewCountry, RelItemService>(getContext(), parent.findViewById(R.id.recycler_services), listViewCountries)
//                                .setView(() -> new RelItemService(getContext(),type))
//                                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartCountry(list.get(position),listViewCountries.get(position).CountryCode,FragServices.this,listViewCountries.get(position).ServerCode))
//                                .orientation(RecyclerView.VERTICAL)
//                                .build();
                            //Toast.makeText(context, item.getServerCode(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnError(String error, int statusCode) {
                            Toast.makeText(MainActivity.getGlobal(), error, Toast.LENGTH_SHORT).show();

                        }
                    }, "api.php?action=true", "", "", MainActivity.getGlobal().getmessage(temp2), getCountryResult.class);
                }
                else {
                    MainActivity.getGlobal().HideMyDialog();
                    MainActivity.getGlobal().FinishFragStartFrag(new menu(1));
                }
            }
        });

    }
}
