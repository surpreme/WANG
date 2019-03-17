package com.liziyang.dall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liziyang.dall.com.internet.InternetActivity;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class User_card_activity extends Activity {
    private ImageView card_background_icon,card_back_mainActivity_icon;
    private ImageView card_user_icon;
    private LinearLayout qzone_web_layout;
    private  String card_background_icon_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550954143828&di=f1058b3f50e54fa41c0e7138385197a6&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D947187115%2C3580556255%26fm%3D193";
    private String card_user_icon_URL="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2012517582,1970996463&fm=26&gp=0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.user_card_layout );
        init();
        madeUI();
    }

    private void madeUI() {
        qzone_web_layout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( User_card_activity.this,InternetActivity.class );
                startActivity ( intent );
            }
        } );
        card_back_mainActivity_icon.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( User_card_activity.this,Main1.class );
                startActivity ( intent );
            }
        } );
    }

    private void init() {
        card_background_icon=findViewById ( R.id.user_card_head_background_icon);
        card_user_icon=findViewById ( R.id.card_user_icon_id );
        card_back_mainActivity_icon=findViewById ( R.id.card_back_mainActivity_icon_id );
        qzone_web_layout=findViewById ( R.id.qzone_web_id);
        Glide.with ( User_card_activity.this ).load ( card_background_icon_URL ).into ( card_background_icon );
        Glide.with ( User_card_activity.this ).
                load ( card_user_icon_URL ).
                apply ( RequestOptions.bitmapTransform ( new CropCircleTransformation (  ) ) ).
                into ( card_user_icon );


    }
}
