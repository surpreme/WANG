package com.okhttp;

import android.util.Log;

import java.util.logging.Logger;

import static android.content.ContentValues.TAG;

public class UtilX {
    //此类没什么用 就是自定义一个打印logcat的方法
    //开关debug
    private static  boolean debug;
    private static final String TAG="HTTPCOME";

    public static void e(String msg){
        if (debug){
            Log.e ( TAG,msg );
        }
    }
}
