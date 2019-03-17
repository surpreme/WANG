package com.liziyang.dall.com.zxing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liziyang.dall.R;
import com.liziyang.dall.com.internet.ZxingInternet;
import com.liziyang.dall.com.zxing.com.zxing.and.GifSizeFilter;
import com.liziyang.dall.com.zxing.com.zxing.and.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class SaoyisaoActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView zXingView;
    private TextView textView;
    private static final int REQUEST_CODE = 2018;
    Intent data;
    ImageView imageView;
    long mLastTime=0;
    long mCurTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.saoyisao_layout );
        zXingView = findViewById ( R.id.zxingview );
        textView = findViewById ( R.id.textView );
        zXingView.setDelegate ( this );
        imageView=findViewById ( R.id.imageView3 );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mLastTime=mCurTime;
                mCurTime= System.currentTimeMillis();
                if(mCurTime-mLastTime<300){//双击事件
                    mCurTime =0;
                    mLastTime = 0;
                    zXingView.closeFlashlight ();
                }else{//单击事件
                    zXingView.openFlashlight ();
                }


            }
        } );
        textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent ( Main2.this,Main4.class );
//                startActivity ( intent );
//                Matisse.from ( Main2.this ).choose ( MimeType.ofAll () )//照片视频全部显示
//                 .countable ( true )//显示选择的数量
//                        .maxSelectable ( 1 ) // 图片选择的最多数量
//                        .gridExpectedSize ( getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                        .capture(false)  // 开启相机，和 captureStrategy 一并使用否则报错
//                        .imageEngine(new Glide4Engine ()) // 使用的图片加载引擎
//                        .restrictOrientation ( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ).thumbnailScale ( 0.85f ) // 缩略图的比例.imageEngine ( new GlideEngine () ) // 使用的图片加载引擎
//                        .theme ( R.style.Matisse_Zhihu ) //主题
//                        .forResult ( REQUEST_CODE );
                Matisse.from(SaoyisaoActivity.this)
                        .choose(MimeType.ofImage())
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .addFilter(new GifSizeFilter (320, 320, 5 * Filter.K * Filter.K))
                        .maxSelectable(1)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .imageEngine(new Glide4Engine ())
                        .forResult(REQUEST_CODE);


            }
        } );


    }



    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult ( requestCode , resultCode , data );
        zXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        if (requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            if (data!=null) {
                //如果用下面的方法 如果相册中选择的图片不属于二维码 则会报错
                // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
//                List<String> paths=Matisse.obtainPathResult ( data );
//                String as=paths.get ( 0 );
//                zXingView.decodeQRCode ( as );
                this.data=data;
                new NewMyTask ().execute (  );
            }


        }
    }




    @Override
    protected void onStart() {
        super.onStart ();
    }

    @Override
    protected void onPause() {
        zXingView.closeFlashlight ();
        super.onPause ();
    }

    @Override
    protected void onStop() {
        zXingView.closeFlashlight ();
        super.onStop ();
    }

    @Override
    protected void onResume() {
        super.onResume ();
        zXingView.startCamera ();
        zXingView.startSpotAndShowRect ();
        zXingView.startSpot ();
        zXingView.setType(BarcodeType.ALL, null);
    }

    /**
     * 处理扫描结果
     *
     * @param result 摄像头扫码时只要回调了该方法 result 就一定有值，不会为 null。解析本地图片或 Bitmap 时 result 可能为 null
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText ( this , "扫描结果" + result , Toast.LENGTH_LONG ).show ();
        zXingView.closeFlashlight ();
        Log.i ( "返回" , result );

    }

    /**
     * 摄像头环境亮度发生变化
     *
     * @param isDark 是否变暗
     */
    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = zXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                zXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
//                zXingView.openFlashlight ();
            }
        } else {
            if (tipText.contains ( ambientBrightnessTip )) {
                tipText = tipText.substring ( 0 , tipText.indexOf ( ambientBrightnessTip ) );
                zXingView.getScanBoxView ().setTipText ( tipText );
            }
        }

    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {

    }
    class NewMyTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            List<String> paths=Matisse.obtainPathResult ( data );
            String as=paths.get ( 0 );
            return QRCodeDecoder.syncDecodeQRCode ( as );
        }

        @Override
        protected void onPostExecute(String s) {
            if (TextUtils.isEmpty(s)){
                Toast.makeText ( SaoyisaoActivity.this,"未扫描到二维码",Toast.LENGTH_LONG ).show ();
            }else {
                 Intent intent=new Intent ( SaoyisaoActivity.this,ZxingInternet.class );
                 intent.putExtra ( "zxinguri",s );
                 startActivity ( intent );

                Toast.makeText(SaoyisaoActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute ( s );
        }
    }

}

