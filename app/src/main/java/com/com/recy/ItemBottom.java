package com.com.recy;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import static java.security.AccessController.getContext;

public class ItemBottom extends RecyclerView.ItemDecoration {

    //分割线 其实用的不多 主要是用来绘制聊天对话框 使用RecyclerView写listView不是他出这个控件的目的
    private static final int[] ATRS=new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST= LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST= LinearLayoutManager.VERTICAL;
    private Drawable drawable;//图片资源
    private int orientation;//方向 水平垂直


    public ItemBottom(Context context , int orientation) {
        final TypedArray a = context.obtainStyledAttributes ( ATRS );
        drawable = a.getDrawable ( 0 );
        a.recycle ();
        setOrientation ( orientation );

    }
    public void setOrientation(int orientation) {
        if (orientation!=HORIZONTAL_LIST&&orientation!=VERTICAL_LIST){
            throw new IllegalArgumentException ( "invalid orientation" );
        }
        this.orientation = orientation;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDraw(@NonNull Canvas c , @NonNull RecyclerView parent , @NonNull RecyclerView.State state) {
        Log.v ( "recyclerView - itemedcoration","onDraw()" );
        if (orientation==VERTICAL_LIST){
            draVertical(c,parent);

        }else {
            drawHorizontal(c,parent);
        }
        super.onDraw ( c , parent , state );
    }

    public void draVertical(Canvas c , RecyclerView parent) {
        final int left=parent.getPaddingLeft ();
        final int right=parent.getWidth ()-parent.getPaddingRight ();
        final int childCount=parent.getChildCount ();
        for (int i=0;i<childCount;i++){
            final View child=parent.getChildAt ( i );
            RecyclerView v=new RecyclerView ( parent.getContext());
            final RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams ();
            final int top=child.getBottom ()+params.bottomMargin;
            final int bottom=top+drawable.getIntrinsicHeight ();
            drawable.setBounds ( left,top,right,bottom );
            drawable.draw ( c );
        }

    }

    public void drawHorizontal(Canvas c , RecyclerView parent) {
        final int top = parent.getPaddingTop ();
        final int bottom = parent.getHeight () - parent.getPaddingBottom ();
        final int childCount = parent.getChildCount ();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt ( i );
            RecyclerView v = new RecyclerView ( parent.getContext () );
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams ();
            final int left = child.getRight () + params.rightMargin;
            final int right = left + drawable.getIntrinsicHeight ();
            drawable.setBounds ( left , top , right , bottom );
            drawable.draw ( c );

        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect , @NonNull View view , @NonNull RecyclerView parent , @NonNull RecyclerView.State state) {
        if (orientation==VERTICAL_LIST){
            outRect.set ( 0,0,0,drawable.getIntrinsicHeight () );
        }else {
            outRect.set ( 0,0,drawable.getIntrinsicWidth (),0 );
        }

    }
}
