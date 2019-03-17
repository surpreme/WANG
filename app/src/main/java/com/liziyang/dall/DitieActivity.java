package com.liziyang.dall;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class DitieActivity extends Activity {
    //这里使用系统给的播放视频资源 就可以不用自定义播放按钮什么的了
    //声明控件
    private VideoView videoView;
    private TextView textView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.ditiedengdai_layout );
        //videoView的使用 绑定控件id  指明播放资源 绑定播放资源
        init();
        //一个是path 一个是uri
        //1设置资源
        //解析uri网址
        Uri uri=Uri.parse( "http://220.181.91.153/vcloud1049.tc.qq.com/1049_M0110000002UvuCR4bswwa1001593269.f40.mp4?vkey=E0315B96AF48E9AEBAD08D67E401312FEACA7B21F7C54DE786DEB87C3710FAFC82B3AE62FB8159F87C43E9D8DDA5F6BC7BE9412BBB2EDCB364055BC1FAA4A055592CE120830AE5D3A702A82BDF36C88188AD78EF8CC63D1B" );
        /**如果是路径使用如下
         File file=new File( Environment.getExternalStorageDirectory(),"name"  );
         Uri aUri=Uri.fromFile( file );
         videoView.setVideoURI( aUri );*/

        videoView.setVideoURI( uri );
        //设置按钮
        //2得到播放控制器
        //controller调节器 media媒体
        //媒体调节器初始化并得到 这是系统给的 使用的上下文为本文
        MediaController mediaController=new MediaController( this );
        //将控件设置好媒体调节器
        videoView.setMediaController( mediaController );
        //当videoView获得焦点才可以播放
        //3获得焦点 播放
        //request请求 focus焦点
        videoView.requestFocus();
        //这里直接播放 没有添加按钮启动播放
        try {
            videoView.start();
            //防止出错
            //这个防止出错是自己写的
        }catch (Exception ex){

        }
        //播放完视频完成的工作
        //4处理视频播放完毕的事情
        //completion完成 listener监听
        //控件设置在完成的时候监听
        videoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //这个自动补全代码
                //我在播放完毕的时候提示toast和文本显示播放完毕
                Toast.makeText( DitieActivity.this,"播放完毕",Toast.LENGTH_SHORT ).show();
                textView.setText( "Over ing...." );

            }
        } );
    }

    private void init() {
        videoView=findViewById( R.id.ditie_videoView_id );
        textView=findViewById( R.id.ditie_textView_id );
    }
}
