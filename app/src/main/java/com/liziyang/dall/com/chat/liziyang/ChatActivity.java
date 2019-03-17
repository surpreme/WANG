package com.liziyang.dall.com.chat.liziyang;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liziyang.dall.R;

import java.util.List;

public class ChatActivity extends Activity {
    private ListView listView;
    private Button button;
    private EditText editText;
    private ChatMessageAdapter chatMessageAdapter;
    private List<ChatMessage> datas;
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler (  ){
        public void handleMessage(android.os.Message msg) {
            //等待接收 子线程完成数据的返回
            ChatMessage fromMessage= (ChatMessage) msg.obj;
            datas.add ( fromMessage );
            //更新数据listView
            chatMessageAdapter.notifyDataSetChanged ();

        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.chat_layout);
        initView();
        initDatas();
        //初始化控件 开始准备实现机器人
        initListener();

    }
    private void initListener() {
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final String toMsg=editText.getText ().toString ();
                if (TextUtils.isEmpty ( toMsg )){
                    Toast.makeText ( ChatActivity.this,"发送消息不能为空",Toast.LENGTH_SHORT ).show ();
                    return;
                }
                ChatMessage toMessage=new ChatMessage (  );
                toMessage.setDate ( new Date (  ) );
                toMessage.setMsg ( toMsg );
                toMessage.setType ( ChatMessage.Type.OUTCOMING );
                datas.add ( toMessage );
                //通知更新
                chatMessageAdapter.notifyDataSetChanged ();
                editText.setText ( "" );




                //网络子线程  避免赌塞ui线程
                new Thread (  ){
                    @Override
                    public void run() {
                        ChatMessage fromMessage=HttpUtils.sendMessage(toMsg);
                        Message m=Message.obtain ();
                        m.obj=fromMessage;
                        mHandler.sendMessage ( m );
                    }
                }.start ();

            }
        } );

    }

    private void initDatas() {
        datas=new ArrayList<ChatMessage> (  );
        datas.add ( new ChatMessage ("现在您可以开始聊天了", ChatMessage.Type.OUTCOMING,new Date (  )) );
        datas.add ( new ChatMessage ("小宝宝你好 我是你的贴心小爱心 你在这里可以给我说你任何的话 比心比心 李某制作聊天机器人即为您服务", ChatMessage.Type.INCOMING,new Date (  )) );
        chatMessageAdapter=new ChatMessageAdapter ( this,datas );
        listView.setAdapter ( chatMessageAdapter );

    }

    private void initView() {
        listView=findViewById ( R.id.listView );
        button=findViewById ( R.id.id_send_msg );
        editText=findViewById ( R.id.id_input_msg);
    }
}

