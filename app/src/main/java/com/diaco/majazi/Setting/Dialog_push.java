package com.diaco.majazi.Setting;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diaco.majazi.R;

public class Dialog_push extends Activity {

    TextView Title;
    TextView MainText;
    ImageView img;
    Button Open;

    String ImageLink;
    String TxtTitle;
    String MatnText;
    String ButtonLink;
    String ButtonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dialog);

        Title = findViewById(R.id.txt_title_push);
        MainText = findViewById(R.id.txt_matn_push);
        img = findViewById(R.id.img_push);
        Open = findViewById(R.id.btn_open_push);

        ImageLink = getIntent().getStringExtra("banner");
        TxtTitle = getIntent().getStringExtra("title");
        MatnText = getIntent().getStringExtra("desc");
        ButtonLink = getIntent().getStringExtra("linkk");
        ButtonText = getIntent().getStringExtra("text_dl");

        Setting.LoadImageWhitoutSizeNoPLace(this,img,ImageLink);


        Title.setText(TxtTitle);
        MainText.setText(MatnText);
        MainText.setLineSpacing(20.0f, 1.0f);
        MainText.setMovementMethod(new ScrollingMovementMethod());
        Open.setText(ButtonText);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(ButtonLink));
                 startActivity(i);

            }
        });

    }


    public void Close(View v) {


        finish();

    }


    public void Open(View v) {
        finish();
        Intent OpenIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ButtonLink));

        startActivity(OpenIntent);



    }
}
