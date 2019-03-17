package com.liziyang.dall.com.internet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.liziyang.dall.LoginActivity;
import com.liziyang.dall.Main1;
import com.liziyang.dall.R;
import com.liziyang.dall.User_card_activity;

import java.net.URL;

public class InternetActivity extends Activity {
    private WebView webView;
    private ImageView webView_back;
    private String URL="https://www.baidu.com";
    private EditText editText;
    private ImageView searcher_image;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.net_internet_layout );
        init();
        made_UI();
    }

    @Override
    protected void onResume() {
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService ( Context.INPUT_METHOD_SERVICE );
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput ( 0,InputMethodManager.HIDE_NOT_ALWAYS );
        }

        super.onResume ();
    }

    private void made_UI() {
        webView.setWebViewClient ( new WebViewClient () );
        webView.getSettings ().setUseWideViewPort ( true );
        webView.getSettings ().setLoadWithOverviewMode ( true );
        webView.loadUrl ( URL );
        webView_back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( InternetActivity.this,User_card_activity.class );
                startActivity ( intent );
            }
        } );
        searcher_image.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String search_URL=null;
                search_URL=editText.getText ().toString ();
                if (search_URL.length ()!=0){
                    search_URL = "https://"+editText.getText ().toString ();
                    webView.setWebViewClient ( new WebViewClient () );
                    webView.getSettings ().setUseWideViewPort ( true );
                    webView.getSettings ().setLoadWithOverviewMode ( true );
                    webView.loadUrl ( search_URL );
                }else if (search_URL.length ()==0){
                    Toast.makeText ( InternetActivity.this,"输入不可以为空",Toast.LENGTH_SHORT ).show ();
                }



            }
        } );
    }

    private void init() {
        webView=findViewById ( R.id.webView_id );
        webView_back=findViewById ( R.id.webView_back_id );
        searcher_image=findViewById ( R.id.internet_searcher_button_icon_id );
        editText=findViewById ( R.id.internet_searcher_edit_id );
    }
}
