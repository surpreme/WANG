package com.com.MyText;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liziyang.dall.R;

public class TextViewActivity extends Activity {
    private MyText textView;
    private Button button,button2,snakebar_btn;
    private EditText editText;
    private TextView text_layout_moreInternet,text_layout_moreInternet2,text_layout_moreInternet3;




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.text_layout_setting );
        initUI();
        setUI();

    }

    private void setUI() {
//        String as="<a href='tel:18565554482'>"+"打电话"+"</a>";
//        text_layout_moreInternet.setText ( Html.fromHtml ( as ) );
//        text_layout_moreInternet.setMovementMethod ( LinkMovementMethod.getInstance () );
        editText.setInputType ( InputType.TYPE_TEXT_FLAG_MULTI_LINE );
        editText.setGravity ( Gravity.TOP );
        editText.setSingleLine ( false );
        editText.setHorizontallyScrolling ( false );
        snakebar_btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Snackbar.make ( v,
                        "数据是否要删除",
                        Snackbar.LENGTH_SHORT )
                        .setAction ( "确定" , new View.OnClickListener () {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText ( TextViewActivity.this,"已删除",Toast.LENGTH_SHORT ).show ();
                            }
                        } ).show ();
            }
        } );
        button2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String ass1="18614079738";
                text_layout_moreInternet.setText ( ass1 );
                String ass2="www.baidu.com";
                text_layout_moreInternet2.setText ( ass2 );
                String ass3="1740747328@qq.com";
                text_layout_moreInternet3.setText ( ass3 );
            }
        } );
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (editText.getText ().toString ().length ()!=0){
                    String s=editText.getText ().toString ();
//                textView.setText ( s );

                    // 设置TextView可展示的宽度 ( 父控件宽度 - 左右margin - 左右padding）

                    int whidth = ScreenUtils.getScreenWidth(TextViewActivity.this) - ScreenUtils.dip2px(TextViewActivity.this, 16 * 2);

//        int whidth = 500;
                    textView.initWidth(whidth);
                    // 设置最大行数(如果SDK >= 16 也可以直接在xml里设置)
                    textView.setMaxLines(3);
                    textView.setCloseText (s);
                }else {
//                    String as="<a href='tel:18565554482'>"+"打电话"+"</a>";
//                    String ass="18614079738";
//                    text_layout_moreInternet.setText ( Html.fromHtml ( as ) );

//                    text_layout_moreInternet.setText ( ass );
//                    text_layout_moreInternet.setMovementMethod ( LinkMovementMethod.getInstance () );
                }


            }
        } );

        // 设置TextView可展示的宽度 ( 父控件宽度 - 左右margin - 左右padding）

        int whidth = ScreenUtils.getScreenWidth(this) - ScreenUtils.dip2px(this, 16 * 2);

//        int whidth = 500;
        textView.initWidth(whidth);
        // 设置最大行数(如果SDK >= 16 也可以直接在xml里设置)
        textView.setMaxLines(3);
        String content = "茫茫的长白大山，浩瀚的原始森林，大山脚下，原始森林环抱中散落着几十户人家的" +
                "一个小山村，茅草房，对面炕，烟筒立在屋后边。在村东头有一个独立的房子，那就是青年点，" +
                "窗前有一道小溪流过。学子在这里吃饭，由这里出发每天随社员去地里干活。干的活要么上山伐" +
                "树，抬树，要么砍柳树毛子开荒种地。在山里，可听那吆呵声：“顺山倒了！”放树谨防回头棒！" +
                "树上的枯枝打到别的树上再蹦回来，这回头棒打人最厉害。";
        textView.setCloseText (content);

    }

    private void initUI() {
        text_layout_moreInternet=findViewById ( R.id.text_layout_moreInternet1_id );
        text_layout_moreInternet2=findViewById ( R.id.text_layout_moreInternet2_id );
        text_layout_moreInternet3=findViewById ( R.id.text_layout_moreInternet3_id );
        textView=findViewById ( R.id.mytext_id );
        button=findViewById ( R.id.text_layout_btn_id );
        button2=findViewById ( R.id.text_layout_btn2_id );
        editText=findViewById ( R.id.text_layout_edit_id );
        snakebar_btn=findViewById ( R.id.text_layout_snakebar_id );

    }
}
