package com.com.MyText;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.liziyang.dall.R;

class ButtonSpan extends ClickableSpan {
    View.OnClickListener onClickListener;
    private Context context;
    private int colorId;

    public ButtonSpan(Context context,View.OnClickListener onClickListener) {
        this(context,onClickListener,R.color.colorAccent);
    }
    public ButtonSpan(Context context , View.OnClickListener onClickListener , int colorId) {
        this.context = context;
        this.colorId=colorId;
        this.onClickListener=onClickListener;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor ( context.getResources ().getColor ( colorId ) );
        ds.setUnderlineText ( false );
    }

    /**
     * Performs the click action associated with this span.
     *
     * @param widget
     */
    @Override
    public void onClick( View widget) {
        if (onClickListener!=null){
            onClickListener.onClick ( widget );
        }

    }
}
