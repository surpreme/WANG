package com;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.com.myCamera.MyCameraActivity;
import com.liziyang.dall.R;
import com.okhttp.MyApp;

import java.io.File;

public class CameraActivity extends Activity {
    private Button btn_android_camera, btn_my_camera;
    private ImageView img_show;
    //定义一个string类型的fileName方便以后使用  用来作为拍摄的图片名字
    private String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.camera_layout );
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder (  );
        StrictMode.setVmPolicy ( builder.build () );
        if (Build.VERSION.SDK_INT>=24){
            builder.detectFileUriExposure ();
        }
        initUI ();
        setUI ();
    }

    private void setUI() {
        btn_android_camera.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //使用intent启动组件
                //系统相机的activity位置 mediaStore.ACTION_IMAGE_CAPTURE

                Intent intent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );

                //定义一个file路径  设置为内存卡路径
                File dir = Environment.getExternalStorageDirectory ();//存在sd卡上
                //命名图片 得到系统毫秒
                fileName = "hey" + System.currentTimeMillis () + ".jpg";
                //初始化路径 把是存在在sd卡上和 保存图片的名字设置进去
                File file = new File ( dir , fileName );
                //设置uri 其他需要图片解码 uri途径就是存储设备和图片名
//                Uri fileuri = Uri.fromFile ( file );
                Uri fileuri =FileProvider.getUriForFile ( CameraActivity.this,getPackageName() +".provider",file);
                //选择拍摄照片存放位置 第二个可以给这个图片一个uri
                //将intent添加额外数据 额外 读出 uri位置
                intent.putExtra ( MediaStore.EXTRA_OUTPUT , fileuri );
                //有返回启动activity intent传输 返回码为0 返回码是自己写的
                startActivityForResult ( intent , 15 );

            }
        } );
        btn_my_camera.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( CameraActivity.this,MyCameraActivity.class );
                startActivity( intent );

            }
        } );
    }

    private void initUI() {
        btn_android_camera = findViewById ( R.id.btn_android_camera_id );
        btn_my_camera = findViewById ( R.id.btn_my_camera_id );
        img_show = findViewById ( R.id.img_show_id );

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult ( requestCode , resultCode , data );
        Log.i ( "TEST" , "onActivityResult" );
        //如果回传码为0和回传码生成
        if (requestCode == 15 && resultCode == RESULT_OK) {
            //指明路径
            File dir = Environment.getExternalStorageDirectory ();//存在sd卡上
            //命名图片 得到系统毫秒
            File file = new File ( dir , fileName );
//            Uri fileuri = Uri.fromFile ( file );
            Uri fileuri= FileProvider.getUriForFile ( CameraActivity.this,getPackageName() +".provider",file);
            //intent启动组件 返回数据的方法 onActivityResult
            //设置图片uri uri图片不用解码
            img_show.setImageURI ( fileuri );
        }
    }
}
