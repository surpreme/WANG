package com.com.myCamera;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.view.WindowManager;

public class DecodeImageUtils {
    @SuppressWarnings("deprecation")
    public static Bitmap decodeImage(byte [] data,Context context,Matrix matrix){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int windowWidth = wm.getDefaultDisplay().getWidth();
        int windowHeight = wm.getDefaultDisplay().getHeight();
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length,opts);
        int bitmapHeight = opts.outHeight;
        int bitmapWidth = opts.outWidth;
        if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
            int scaleX = bitmapWidth/windowWidth;
            int scaleY = bitmapHeight/windowHeight;
            if(scaleX>scaleY){
                opts.inSampleSize = scaleX;
            }else{
                opts.inSampleSize = scaleY;
            }
        }else{
            opts.inSampleSize = 1;
        }
        opts.inJustDecodeBounds = false;
        Bitmap oldBitmap = BitmapFactory.decodeByteArray(data, 0, data.length,opts);
        Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0,oldBitmap.getWidth(), oldBitmap.getHeight(),matrix, true);
        return newBitmap;
    }

    @SuppressWarnings("deprecation")
    public static Bitmap decodeImageByPath(String path,Context context){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int windowWidth = wm.getDefaultDisplay().getWidth();
        int windowHeight = wm.getDefaultDisplay().getHeight();
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int bitmapHeight = opts.outHeight;
        int bitmapWidth = opts.outWidth;
        if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
            int scaleX = bitmapWidth/windowWidth;
            int scaleY = bitmapHeight/windowHeight;
            if(scaleX>scaleY){
                opts.inSampleSize = scaleX;
            }else{
                opts.inSampleSize = scaleY;
            }
        }else{
            opts.inSampleSize = 1;
        }
        opts.inJustDecodeBounds = false;
        Bitmap oldBitmap = BitmapFactory.decodeFile(path, opts);
        Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0,oldBitmap.getWidth(), oldBitmap.getHeight(),null, true);
        return newBitmap;
    }
}
