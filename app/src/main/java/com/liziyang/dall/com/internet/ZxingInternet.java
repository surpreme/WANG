package com.liziyang.dall.com.internet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liziyang.dall.R;

public class ZxingInternet extends Activity {
    private WebView webView;
    private String URL;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.zxing_internet_layout );
        webView=findViewById ( R.id.zxing_internet_webView_id );
        Intent intent=new Intent (  );
        URL=intent.getStringExtra ( "zxinguri" );
        webView.setWebViewClient ( new WebViewClient () );
        webView.getSettings ().setUseWideViewPort ( true );
        webView.getSettings ().setLoadWithOverviewMode ( true );
        webView.loadUrl ( URL );
    }
}
