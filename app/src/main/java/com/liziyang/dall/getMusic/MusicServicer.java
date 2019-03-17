package com.liziyang.dall.getMusic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicServicer extends Service {
    private int newmusicID;
    private MusicResult musicResult;
    private MediaPlayer mediaPlayer=new MediaPlayer ();
    public static String nowPlayname;
    private int state=0x11;//11第一次播放 12暂停 13播放
    private int nowtime,overtime;//当前音乐时间 总时间
    @Override
    public void onCreate() {
        /**
         * 注册广播
         */
        MyBroadcastReceiver receiver=new MyBroadcastReceiver ();
        IntentFilter intentFilter=new IntentFilter ( "com.liziyang.Service" );
        registerReceiver ( receiver,intentFilter );
        /**
         * 自动播放下一曲 mediaPlayer有内置的方法
         */
        mediaPlayer.setOnCompletionListener ( new MediaPlayer.OnCompletionListener () {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent=new Intent ( "com.liziyang.Activity" );
                intent.putExtra ( "Over",true );
                sendBroadcast ( intent );
                nowtime=0;
                overtime=0;
            }
        } );



        super.onCreate ();
    }
    public class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context , Intent intent) {
            /**
             * 默认-1 如果不是则不是播放新歌曲
             * 接受是否发送的新歌曲
             */
            newmusicID=intent.getIntExtra ( "newmusic",-1 );

            if (newmusicID!=-1){
                /**
                 * 额外数据为歌曲对象 这里用序列化
                 */
                //获得歌曲对象
                musicResult= (MusicResult) intent.getSerializableExtra ( "qingmeng1" );
                if (musicResult!=null){
                    playMusic ( musicResult );
                    state=0x12;

                }

            }
            int isplay=intent.getIntExtra ( "isplay",-1 );
            if (isplay!=-1){
                switch (state){
                    case 0x11:
                        //第一次播放歌曲
                        musicResult= (MusicResult) intent.getSerializableExtra ( "qingmeng1" );
                        playMusic ( musicResult );
                        state=0x12;
                        break;
                    case 0x12:
                        mediaPlayer.pause ();
                        state=0x13;
                        break;
                    case 0x13:
                        mediaPlayer.start ();
                        state=0x12;
                        break;
                        default:
                            break;

                }
            }
            int progress=intent.getIntExtra ( "progress", -1);
            if (progress!=-1){
                //获得当前时间
                nowtime= (int) (((progress*1.0)/100)*overtime);
                /**
                 * mediaPlayer有一个方法跳到当前seek进度
                 */
                mediaPlayer.seekTo ( nowtime );


            }
            /**
             * 当前状态发送给activity
             */
            Intent intent2=new Intent ( "com.liziyang.Activity" );
            intent2.putExtra ( "state",state );
            sendBroadcast ( intent2 );


        }
    }

    /**
     * 播放歌曲的实现
     * @param result
     */
    public void playMusic(MusicResult result){
        if (mediaPlayer!=null){
            //停止播放
            mediaPlayer.stop ();
            //等待
            mediaPlayer.reset ();
            try {
                /**
                 * 获得路径 需要特殊处理 必须用try方法 可能会获取不到
                 */
                mediaPlayer.setDataSource ( result.getMusic_path_String () );
                 nowPlayname=result.getMusic_name_String ();
                //准备播放
                mediaPlayer.prepare ();
                //播放
                mediaPlayer.start ();
                overtime=mediaPlayer.getDuration ();//获得当前歌曲总时长
                new Thread (  ){
                    @Override
                    public void run() {
                        while (nowtime < overtime) {
                            try {
                                sleep ( 1000 );
                                nowtime = mediaPlayer.getCurrentPosition ();//获得当前音乐时间
                                Intent intent = new Intent ( "com.liziyang.Activity" );
                                intent.putExtra ( "nowtimeid" , nowtime );
                                intent.putExtra ( "overtimeid" , overtime );
                                sendBroadcast ( intent );//把当前时间和总音乐时长发送给activity
                            } catch (InterruptedException e) {
                                e.printStackTrace ();
                            }
                        }
                    }


                }.start ();
            } catch (Exception e) {
                e.printStackTrace ();
            }


        }


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
