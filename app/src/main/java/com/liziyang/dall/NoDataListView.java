package com.liziyang.dall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.text.MeasureFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class NoDataListView extends ListView  {
    /**
     * 处理无网或数据为空的方法类
     * 思路是item显示为0 则处理为显示此界面
     * @param context
     */

    private int height=0;
    private int width=0;
    private Bitmap nodataBitmap=null;
    private int minItem=0;
    private Paint paint;
    /**
     * 判断是否无数据的数字
     */
    private int noDataImgInt;

    public NoDataListView(Context context) {
        this ( context,null );

    }

    public NoDataListView(Context context , AttributeSet attrs) {
        this ( context , attrs,0 );

    }

    public NoDataListView(Context context , AttributeSet attrs , int defStyleAttr) {
        super ( context , attrs , defStyleAttr );

        /**
         * 获取xml自定义属性文件 进行定义
         */
        @SuppressLint("Recycle")
        TypedArray array=context.obtainStyledAttributes ( attrs,R.styleable.NoDataListView );
        noDataImgInt=array.getResourceId ( R.styleable.NoDataListView_noDataImg,0 );
        minItem=array.getInt ( R.styleable.NoDataListView_minItem,0 );
        if (noDataImgInt!=0){
            nodataBitmap=BitmapFactory.decodeResource ( getResources (),noDataImgInt );

        }else {
            nodataBitmap=Bitmap.createBitmap ( 1,1,Bitmap.Config.ARGB_8888 );

        }
        paint=new Paint (  );
        /**
         * recycle回收
         * 对使用的工具进行关闭
         */
        array.recycle ();


    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec) {
        /**
         * 将测量的宽高设置给height和width
         */
        height=MeasureSpec.getSize ( heightMeasureSpec );
        width=MeasureSpec.getSize ( widthMeasureSpec );
        super.onMeasure ( widthMeasureSpec , heightMeasureSpec );
    }

    /**
     * 进行绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        /***
         * 初始化适配器进行获取item的个数
         */
        Adapter adapter=getAdapter ();
        super.onDraw ( canvas );
        if (adapter==null||adapter.getCount ()<=minItem){
            canvas.drawBitmap ( nodataBitmap,
                    width/2-nodataBitmap.getWidth ()/2,
                    height/2-nodataBitmap.getHeight ()/2,
                    paint );

        }
    }



}
