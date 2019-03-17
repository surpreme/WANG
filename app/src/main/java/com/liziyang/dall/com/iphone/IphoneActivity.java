package com.liziyang.dall.com.iphone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liziyang.dall.R;

public class IphoneActivity extends Activity {
    private TextView iphone_text_btn,message_iphone_tet_btn;
    private EditText editText;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.iphone_all_layout );
        init();
        initUI();
    }

    private void initUI() {
        iphone_text_btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "点击打电话", Toast.LENGTH_SHORT).show();
                //创建一个intent（隐式）需要指定一个action  用系统源代码和工具找到打电话页面的action contacts
                // 系统打电话应用软件包名**注action android：name="android.action.DIAI"/>
                String action="android.intent.action.DIAL";
                action =Intent.ACTION_DIAL;
                Intent intent=new Intent(action);
                //携带数据有两种方式intent.putExtra(name,value)和intent.setData()括号里面需要一个uri
                String number="18614079738";//这个是找到输入数据的路径
                intent.setData(Uri.parse("tel:"+number));//此处的冒号极易忘掉
                //tel为固定模式 是手机电话的一个固定模式


                //start activity(intent)启动拨号页面
                startActivity(intent);

            }
        } );
        message_iphone_tet_btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "点击发短信", Toast.LENGTH_SHORT).show();-
//创建一个intent(隐式)
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                //携带内容，电话号码
                String number= "18614079738";
                String sms= editText.getText().toString();
                intent.setData( Uri.parse( "smsto:"+number ));//这里smsto为固定格式 内部识别码  注：这里的smsto别忘了加冒号：
                //携带额外数据
                intent.putExtra( "sms_body",sms );//短信文本的获取输出

                //startactivity（intent）启动画面
                startActivity(intent);
//在这里只是调用了发短信的画面 并没有发送短信 如果只是跳转画面就可以不加画面

            }
        } );
    }

    private void init() {
        editText=findViewById ( R.id.message_iphone_id_edit );
        iphone_text_btn=findViewById ( R.id.iphone_text_btn_id );
        message_iphone_tet_btn=findViewById ( R.id.message_iphone_tet_btn_id );
    }
}
