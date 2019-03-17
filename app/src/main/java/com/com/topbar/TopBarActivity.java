package com.com.topbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liziyang.dall.Main1;
import com.liziyang.dall.R;

import java.util.Objects;

public class TopBarActivity extends Activity {
    private Button topbar_one,topbar_two,topbar_three,topbar_four,topbar_four2;
    //notification通知
    private NotificationManager notificationManager;
    //设置通知的id
    //static不变 final不变 一旦赋值不可改变
    private static final int NOTIFICATION_FLAG=1;
    private static final int NOTIFICATION_FLAG2=3;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.topbar_layout );
        initUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
        setUI();


    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

    }

    private void setUI() {
        notificationManager= (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
        /**
         * 通知有提示音 在顶部显示
         */
        topbar_four2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(TopBarActivity.this, "subscribe")
                        .setContentTitle("收到一条订阅消息")
                        .setContentText("地铁沿线30万商铺抢购中！")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                        .setAutoCancel(true)
                        .setNumber(1)
                        .build();
                manager.notify(20, notification);

            }
        } );
        /**
         * 消息通知有声音并屏幕弹出消息 通知栏弹出消息
         */
        topbar_four.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = manager.getNotificationChannel("chat");
                    if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                        startActivity(intent);
                        Toast.makeText(TopBarActivity.this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                    }
                }

                Notification notification = new NotificationCompat.Builder(TopBarActivity.this, "chat")
                        .setContentTitle("收到一条聊天消息")
                        .setContentText("今天中午吃什么？")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                        .setAutoCancel(true)
                        .setNumber(1)
                        .build();
                manager.notify(10, notification);

            }
        } );
        topbar_one.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //早期版本兼容2.x
                Intent intent=new Intent( TopBarActivity.this,Main1.class );
                PendingIntent pi=PendingIntent.getActivity( TopBarActivity.this,0,intent,0 );
                Notification notification=new Notification(  );
                notification.icon=R.mipmap.ic_launcher;
                //直接在通知栏未展开显示的提示
                notification.tickerText="有新消息";
                notification.flags|=Notification.FLAG_AUTO_CANCEL;
//                notification.setLastEventInfo(this,"微信","王幼虎喜欢猫 3条新信息",pi);

                notificationManager.notify(NOTIFICATION_FLAG,notification);
                //notify通知 notification是自己定义的名字
            }
        } );

        topbar_two.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                @SuppressLint({"NewApi","LocalSuppress"}) Notification.Builder notification=new Notification.Builder( TopBarActivity.this )
                        .setSmallIcon(R.mipmap.ic_launcher )
                        .setContentText( "王幼虎喜欢猫 3条新消息" )
                        .setContentTitle( "微信" )
                        .setTicker( "您有新消息" )
                        .setAutoCancel(true);


                Intent intent=new Intent( TopBarActivity.this,Main1.class );
                NotificationManager notificationManager= (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                notificationManager.notify( NOTIFICATION_FLAG,notification.build() );

            }
        } );
        topbar_three.setOnClickListener ( new View.OnClickListener () {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder=new NotificationCompat.Builder( TopBarActivity.this )
                        .setSmallIcon( R.mipmap.ic_launcher )
                        .setContentText( "王幼虎喜欢猫 3条新消息" )
                        .setContentTitle( "微信" )
                        .setTicker( "您有新消息" );
                Intent intent=new Intent( TopBarActivity.this,Main1.class );
                TaskStackBuilder taskStackBuilder=TaskStackBuilder.create( TopBarActivity.this );
                taskStackBuilder.addParentStack( Main1.class);
                taskStackBuilder.addNextIntent( intent );
                PendingIntent pendingIntent=taskStackBuilder.getPendingIntent( 0,PendingIntent.FLAG_UPDATE_CURRENT );
                mBuilder.setContentIntent( pendingIntent );
                //设置点击消失
                mBuilder.setAutoCancel(true);
                NotificationManager notificationManager= (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                Objects.requireNonNull( notificationManager ).notify(NOTIFICATION_FLAG2,mBuilder.build() );


            }
        } );
    }

    private void initUI() {
        topbar_four2=findViewById ( R.id.topbar_four2_id );
        topbar_four=findViewById ( R.id.topbar_four_id );
        topbar_one=findViewById ( R.id.topbar_one_id );
        topbar_two=findViewById ( R.id.topbar_two );
        topbar_three=findViewById ( R.id.topbar_three );

    }
}
