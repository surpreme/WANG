package com.liziyang.dall;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MusicVideoActivity extends Activity {
    //这里使用系统给的播放视频资源 就可以不用自定义播放按钮什么的了
    //声明控件
    private VideoView videoView;
    private TextView textView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.music_vedio_layout );
        //videoView的使用 绑定控件id  指明播放资源 绑定播放资源
        init();
        //一个是path 一个是uri
        //1设置资源
        //解析uri网址
        Uri uri=Uri.parse( 	"http://220.181.91.161/vcloud1049.tc.qq.com/1049_M0110000002KrwQP1XHDlr1001436639.f40.mp4?vkey=8353EDE45D70CE8580A858D9ED424476B90214CB8290417D8B1F054B091F6C8067CB1A4B53E5F8B436092AA806BD8C0B55A8A0EDEC78077880783E647A7126FFEA87930A3D3620844292D71EABD5C4C5F4CBFEC24CDD6BC6" );
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
                Toast.makeText( MusicVideoActivity.this,"播放完毕",Toast.LENGTH_SHORT ).show();
                textView.setText( "Over ing...." );

            }
        } );
    }

    private void init() {
        videoView=findViewById( R.id.music_videoView_id );
        textView=findViewById( R.id.music_textView_id );
    }
}
