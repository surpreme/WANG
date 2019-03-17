package com.liziyang.dall;

import android.app.Activity;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liziyang.dall.getMusic.MusicAdapter;
import com.liziyang.dall.getMusic.MusicResult;
import com.liziyang.dall.getMusic.MusicServicer;
import com.liziyang.dall.getMusic.MusicUtil;


import java.util.List;

public class MusicActivity extends Activity {
    private SeekBar music_seekBar;
    private ListView music_listView;
    private TextView nowTimeTextView,overTimeTextView;
    private ImageButton last_music_imgButton,noOryes_music_imgButton,next_music_imgButton;
    private MusicAdapter musicAdapter;
    private List<MusicResult> list;
    private Context xContext;
    private MusicResult musicResult;
    private TextView nowplaymusicname;
    private int index=0;
    private int state=0x11;
    private TextView nowStart_people_text;
    private ImageButton start_music_type_imgButton;
    private PopupWindow popupWindow_music;
    private int flag=0;
    private TextView start_type_text;
    private String start_type_string="顺序播放";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.music_layout );
        init();
        initUI();
        seekbarChange();
    }
    public class MyBroadcastActivity extends BroadcastReceiver{
        @Override
        public void onReceive(Context context , Intent intent) {
            state=intent.getIntExtra ( "state",-1 );
            switch (state){
                case 0x11:
                    noOryes_music_imgButton.setImageResource ( android.R.drawable.ic_media_play );
                    break;
                case 0x12:
                    noOryes_music_imgButton.setImageResource ( android.R.drawable.ic_media_pause );
                    break;
                case 0x13:
                    noOryes_music_imgButton.setImageResource ( android.R.drawable.ic_media_play  );
                    break;
                default:
                    break;
            }
            int overtime=intent.getIntExtra ( "overtimeid",-1 );
            int nowtime=intent.getIntExtra ( "nowtimeid",-1 );
            if (nowtime!=-1){
                music_seekBar.setProgress ( (int) ((nowtime*1.0)/overtime*100) );
                nowTimeTextView.setText ( initTiem ( nowtime,overtime ) );

            }
            boolean isOver=intent.getBooleanExtra ( "Over",false );
            if (isOver==true){
                Intent intentOver=new Intent ( "com.liziyang.Service" );
                if (flag==0){
                    //下一曲
                    if (index==list.size ()-1){
                        index=0;
                    }else {
                        index++;
                    }
                    musicResult=list.get ( index );
                    intentOver.putExtra ( "newmusic",1 );
                    intentOver.putExtra ( "qingmeng1",musicResult );
                    sendBroadcast ( intentOver );
                    try{
                        nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
                        nowStart_people_text.setText ( musicResult.getMusic_author_String ().toString () );
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                }else if (flag==1){
                    musicResult=list.get ( index );
                    intentOver.putExtra ( "newmusic",1 );
                    intentOver.putExtra ( "qingmeng1",musicResult );
                    sendBroadcast ( intentOver );
                    try{
                        nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
                        nowStart_people_text.setText ( musicResult.getMusic_author_String ().toString () );
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                }else if (flag==2){
                    index=(int)(Math.random ()*list.size ());
                    musicResult=list.get ( index );
                    intentOver.putExtra ( "newmusic",1 );
                    intentOver.putExtra ( "qingmeng1",musicResult );
                    sendBroadcast ( intentOver );
                    try{
                        nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
                        nowStart_people_text.setText ( musicResult.getMusic_author_String ().toString () );
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                }


            }

        }
    }
    private void seekbarChange(){
        music_seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
            /**
             * 正在滑动
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar , int progress , boolean fromUser) {

            }

            /**
             * 开始滑动
             * @param seekBar
             */

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 停止滑动
             * @param seekBar
             */

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent=new Intent ( "com.liziyang.Service" );
                /**
                 * 获取当前滑动条拖动位置
                 */
                intent.putExtra ( "progress",seekBar.getProgress () );
                sendBroadcast ( intent );

            }
        } );
    }
    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener () {
        @Override
        public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
            index=position;
            /**
             * 获得当前选中的位置
             */
            musicResult=list.get ( position );
            try{
                nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
            } catch (Exception e) {
                e.printStackTrace ();
            }

            Intent intent=new Intent ( "com.liziyang.Service" );
            intent.putExtra ( "qingmeng1",musicResult );
            /**
             * 1为新歌曲
             */
            intent.putExtra ( "newmusic",1 );
            /**
             * 发送广播到服务
             */
            sendBroadcast ( intent );


        }
    };

    private void initUI() {
        list=MusicUtil.getMusicDate ( xContext );
        musicAdapter=new MusicAdapter ( list,xContext );
        music_listView.setAdapter ( musicAdapter );
        music_listView.setOnItemClickListener ( onItemClickListener );
        /**
         * 播放模式 循环播放 顺序播放等
         */
        start_music_type_imgButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showBottomPopupWindow();


            }
        } );
        last_music_imgButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //上一曲

                //如果为第一首歌曲
                if (index==0){
                    index=list.size ()-1;
                }else {
                    index--;
                }

                musicResult=list.get ( index );
                Intent huu=new Intent ( "com.liziyang.Service" );
                huu.putExtra ( "newmusic",1 );
                huu.putExtra ( "qingmeng1",musicResult );
                sendBroadcast ( huu );


                try{
                    nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
                    nowStart_people_text.setText ( musicResult.getMusic_author_String ().toString () );
                } catch (Exception e) {
                    e.printStackTrace ();
                }



            }
        } );
        next_music_imgButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //下一曲
                if (index==list.size ()-1){
                    index=0;
                }else {
                    index++;
                }
                musicResult=list.get ( index );
                Intent huu=new Intent ( "com.liziyang.Service" );
                huu.putExtra ( "newmusic",1 );
                huu.putExtra ( "qingmeng1",musicResult );
                sendBroadcast ( huu );
                try{
                    nowplaymusicname.setText ( musicResult.getMusic_name_String ().toString () );
                    nowStart_people_text.setText ( musicResult.getMusic_author_String ().toString () );
                } catch (Exception e) {
                    e.printStackTrace ();
                }

            }
        } );
        noOryes_music_imgButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent huu=new Intent ( "com.liziyang.Service" );
                //暂停
                /**
                 * 如果当前第一次进入播放器 播放第一首歌曲
                 */
                if (musicResult==null){
                    musicResult=list.get ( 0 );
                    huu.putExtra ( "qingmeng1",musicResult );
                    try{
                        nowplaymusicname.setText ( list.get ( 0 ).getMusic_name_String ().toString () );
                        nowStart_people_text.setText ( list.get ( 0 ).getMusic_author_String ().toString () );
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }


                }
                huu.putExtra ( "isplay",1 );
                sendBroadcast ( huu );



            }
        } );
    }


    private void init() {
        start_type_text=findViewById ( R.id.start_type_text_id );
        start_music_type_imgButton=findViewById ( R.id.start_music_type_imgButton );
        nowStart_people_text=findViewById ( R.id.now_music_people_id );
        nowplaymusicname=findViewById ( R.id.nowplayname_id );
        music_listView=findViewById ( R.id.music_listView_id);
        nowTimeTextView=findViewById ( R.id.now_music_time_text_id );
        overTimeTextView=findViewById ( R.id.over_music_time_text_id );
        last_music_imgButton=findViewById ( R.id.last_music_imgButton_id );
        noOryes_music_imgButton=findViewById ( R.id.noOryes_music_imgButton_id );
        next_music_imgButton=findViewById ( R.id.next_music_imgButton_id );
        music_seekBar=findViewById ( R.id.music_seekBar_id );
        xContext=MusicActivity.this;
        /**
         * 注册广播
         */
        MyBroadcastActivity myBroadcastActivity=new MyBroadcastActivity ();
        IntentFilter intentFilter=new IntentFilter ( "com.liziyang.Activity" );
        registerReceiver ( myBroadcastActivity,intentFilter );
        /**
         * 启动service服务
         */
        Intent intent=new Intent ( xContext,MusicServicer.class );
        startService ( intent );
    }
    /**
     *  转化毫秒为分秒
     */
    private String initTiem(int now,int over){
        //分
        int now_fen=now/1000/60;
        //秒
        int now_miao=now/1000%60;
        int over_fen=over/1000/60;
        int over_miao=over/1000%60;
        return getT ( now_fen )+":"+getT ( now_miao )+"/"+getT ( over_fen )+":"+getT ( over_miao );
    }
    private String getT(int time){
        if (time<10){
            return "0"+time;
        }else {
            return time+"";
        }


    }
    private void showBottomPopupWindow() {
        //如果代码和xml同时指定布局的位置高宽度等 以代码为准
        /**
         * 设置背影 这里让父布局变背景颜色
         * 最佳是获得屏幕管理者
         */
        setBackGroundAlpha ( 0.5f ,MusicActivity.this);
        /**
         * 解析出使用的布局
         */
        View view = LayoutInflater.from ( MusicActivity.this ).inflate ( R.layout.music_popwindow_start_layout , null );
        /**
         * 构造传入的参数
         */
        popupWindow_music = new PopupWindow ( view , LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT , true );
        /**
         * 将布局设置上去
         */
        popupWindow_music.setContentView ( view );
        /**
         * 布局中的ui控件
         */
        Button button1 = view.findViewById ( R.id.start_312 );
        Button button2 = view.findViewById ( R.id.start_123 );
        Button button3 = view.findViewById ( R.id.start_1 );
        button1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //列表循环
                Toast.makeText ( MusicActivity.this,"当前播放模式为列表循环",Toast.LENGTH_SHORT).show ();
                flag=0;
                start_type_string="列表循环";
                start_type_text.setText ( start_type_string );
                start_music_type_imgButton.setImageResource ( android.R.drawable.ic_menu_sort_by_size );
                popupWindow_music.dismiss ();



            }
        } );
        button2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //单曲播放
                Toast.makeText ( MusicActivity.this,"当前播放模式为单曲播放",Toast.LENGTH_SHORT).show ();
                flag=1;
                start_type_string="单曲播放";
                start_type_text.setText ( start_type_string );
                start_music_type_imgButton.setImageResource ( android.R.drawable.ic_menu_rotate );
                popupWindow_music.dismiss ();



            }
        } );
        button3.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //随机循环
                Toast.makeText ( MusicActivity.this,"当前播放模式为随机循环",Toast.LENGTH_SHORT).show ();
                flag=2;
                start_type_string="随机循环";
                start_type_text.setText ( start_type_string );
                start_music_type_imgButton.setImageResource ( android.R.drawable.ic_menu_help );
                popupWindow_music.dismiss ();

            }
        } );
        /**
         * 设置动画
         */
        popupWindow_music.setAnimationStyle ( R.style.contextMenuAnim );
        /**
         * 点击空白处是否隐藏pop
         * 默认为true
         */

        popupWindow_music.setFocusable(true);
        // 点击空白处时，隐藏掉pop窗口

        /**
         * 显示popupWindows
         * 1显示的布局view
         * 2设置显示的位置
         * 方法1 指定位置
         * 方法2 控件下面
         * 方法3 控件延伸
         */
        View rootView = LayoutInflater.from ( this ).inflate ( R.layout.music_layout , null );
        popupWindow_music.showAtLocation ( rootView , Gravity.BOTTOM , 0 , 0 );
        /**
         * popupWindow关闭调用的方法
         * 这里处理背影
         */
        popupWindow_music.setOnDismissListener ( new PopupWindow.OnDismissListener () {
            @Override
            public void onDismiss() {
                // relativeLayout.setBackgroundColor ( getResources ().getColor ( R.color.mycolor ) );
                setBackGroundAlpha ( 1.0f ,MusicActivity.this);

            }
        } );


    }
    public void setBackGroundAlpha(float alpha, Context context){

        WindowManager.LayoutParams layoutParams=((Activity)context).getWindow ().getAttributes ();
        layoutParams.alpha=alpha;
        ((Activity)context).getWindow ().addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        ((Activity)context).getWindow ().setAttributes ( layoutParams );
    }


}

