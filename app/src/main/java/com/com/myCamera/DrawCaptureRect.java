package com.com.myCamera;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.View;

public class DrawCaptureRect extends View{
    private int mcolorfill;
    private int mleft, mtop, mwidth, mheight;
    private Canvas mCanvas;
    private Paint mpaint;
    public DrawCaptureRect(Context context, int left, int top, int width, int height, int colorfill) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mcolorfill = colorfill;
        this.mleft = left;
        this.mtop = top;
        this.mwidth = width;
        this.mheight = height;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas=canvas;
        //clean screen
        mpaint = new Paint();
        mpaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        mCanvas.drawPaint(mpaint);
        mpaint.setXfermode(new PorterDuffXfermode(Mode.SRC));


        mpaint.setColor(mcolorfill);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(2.0f);
        mCanvas.drawLine(mleft, mtop, mleft+mwidth, mtop, mpaint);
        mCanvas.drawLine(mleft+mwidth, mtop, mleft+mwidth, mtop+mheight, mpaint);
        mCanvas.drawLine(mleft, mtop, mleft, mtop+mheight, mpaint);
        mCanvas.drawLine(mleft, mtop+mheight, mleft+mwidth, mtop+mheight, mpaint);
        super.onDraw(mCanvas);
    }
}
