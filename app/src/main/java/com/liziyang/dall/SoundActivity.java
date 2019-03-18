package com.liziyang.dall;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class SoundActivity extends Activity {
    //Bundle包
    //binder捆绑 绑定
    //manager经理管理
    //系统服务
    //POWER_SERVICE("power")PowerManager手机电源管理 power动力
    //NOTIFICATION_SERVICE("notification") NotificationManager通知管理 notification通知
    //LOCATION_SERVICE("location") LocationManager 定位控制 location位置
    //CONNECTIVITY_SERVICE("connection") ConnectivityManager网络连结的管理 connection 和connectivity都是连结的意思
    //audio音频
    //声明音频管理器
    AudioManager audioManager;
    AlarmManager alarmManager;
    private Button sound_layout_btn_more,sound_layout_btn_letter,sound_layout_btn_clock;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sound_layout );
        initUI();
        audioManager= (AudioManager) getSystemService( Context.AUDIO_SERVICE );
        //alarm报警 system系统 getSystemService得到系统服务
        alarmManager= (AlarmManager) getSystemService( ALARM_SERVICE );
        setUI();
    }

    private void setUI() {
        sound_layout_btn_more.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // stream流 streamType声音流 的种类 手机 闹钟音乐等STREAM_MUSIC ADJUST_LOWER调低音量
                // audioManager.setStreamVolume( index );index给定音量的值 可以插入耳机调用
                audioManager.adjustStreamVolume( AudioManager.STREAM_VOICE_CALL,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI );

            }
        } );
        sound_layout_btn_letter.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //ADJUST_RAISE调大音量
                //STREAM_ALARM闹钟 CALL电话
                audioManager.adjustStreamVolume( AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI );

            }
        } );
        sound_layout_btn_clock.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //闹钟也分时间 得到当前时间
                //current当前 millis毫
                long time=System.currentTimeMillis();
                time+=1000;
                Intent intent=new Intent( "hey.alarmtest" );
                //pending听候 PendingIntent听候intent
                PendingIntent pendingIntent=PendingIntent.getService( SoundActivity.this,0x190,intent,0 );
                alarmManager.set( AlarmManager.RTC_WAKEUP,time,pendingIntent );
            }
        } );
    }

    private void initUI() {
        sound_layout_btn_more=findViewById ( R.id.sound_layout_btn_more_id );
        sound_layout_btn_letter=findViewById ( R.id.sound_layout_btn_letter_id );
        sound_layout_btn_clock=findViewById ( R.id.sound_layout_btn_clock_id );
    }
}
