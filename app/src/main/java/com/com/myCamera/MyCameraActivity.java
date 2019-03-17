package com.com.myCamera;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.liziyang.dall.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MyCameraActivity extends AppCompatActivity implements View.OnClickListener {
    private Button take_photo,cut_photo;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.mycamera_layout );
        take_photo=(Button) findViewById(R.id.take_photo);
        cut_photo=(Button) findViewById(R.id.cut_photo);
        take_photo.setOnClickListener(this);
        cut_photo.setOnClickListener(this);
        imageView=(ImageView) findViewById(R.id.cut_imageview);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.take_photo:
                //1设置intent  action为PageAction.TAKE_PHOTO_ACTION ,请求吗为 PageAction.REQUEST_TAKE_PHOTO_CODE 用来调用照相机，照相机完成后会自动到裁剪页面
                //2 将你自己请求页面的action设置为pageAction.REQUEST_PAGE_ACTION
                //3等所有动作完成后在请求页面重写onactivityresult接收结果，结果码为 pageAction.RESULT_OK结果intent.getByteArrayExtra(pageAction.GET_CUT_PHOTO_KEY)
                Intent intent=new Intent(PageAction.TAKE_PHOTO_ACTION);
                startActivityForResult(intent, PageAction.REQUEST_TAKE_PHOTO_CODE);
                break;
            case R.id.cut_photo:
                //1调用系统图库或者自己定义选择图片，并获取图片路径
                //2设置intent action为PageAction.CUT_PHOTO_ACTION用来调用裁剪类库，会自动进入裁剪页面
                //3将你请求的页面action设置为pageAction.REQUEST_PAGE_ACTION
                //4等所有动作完成后在请求页面重写onactivityresult接收结果，结果intent.getByteArrayExtra("image")
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //2设置intent action为PageAction.CUT_PHOTO_ACTION用来调用裁剪类库，会自动进入裁剪页面
            Intent intent=new Intent(PageAction.CUT_PHOTO_ACTION);
            intent.putExtra(PageAction.WOULD_CUT_PHOTO_PATH_KEY, picturePath);
            startActivityForResult(intent, PageAction.REQUEST_CUT_PHOTO_CODE);
        }else if(requestCode==PageAction.REQUEST_TAKE_PHOTO_CODE && resultCode==PageAction.RESULT_OK&& null != data){

            byte [] imagrarray=data.getByteArrayExtra(PageAction.GET_CUT_PHOTO_KEY);
            if(imagrarray.length>0){
                Bitmap bitmap=BitmapFactory.decodeByteArray(imagrarray, 0, imagrarray.length);
                imageView.setImageBitmap(bitmap);
            }

        }else if(requestCode==PageAction.REQUEST_CUT_PHOTO_CODE && resultCode==PageAction.RESULT_OK&& null != data){
            byte [] imagrarray=data.getByteArrayExtra(PageAction.GET_CUT_PHOTO_KEY);
            if(imagrarray.length>0){
                Bitmap bitmap=BitmapFactory.decodeByteArray(imagrarray, 0, imagrarray.length);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}

