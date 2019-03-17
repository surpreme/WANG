package com.liziyang.dall;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.Seacher.activity.SeacherActivity;
import com.liziyang.dall.com.chat.liziyang.ChatActivity;
import com.liziyang.dall.com.gaode.GaodeActvity;
import com.liziyang.dall.com.iphone.IphoneActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;

public class MessageFragment extends Fragment {
    private Banner message_banner;
    ArrayList<String> arrayList;
    private RefreshLayout refreshLayout;
//    private GridView gridView;
    private TextView music_more_text;
    private GifImageView gifImageView_music_vedio,ditiedengdai_gif,chat_gif,iphone_icon_gif,gaode_icon_gif;
    TextSwitcher textSwitcher;
    private TextView seacher_text;
    String[] strings={
            "你有藏在心底的秘密吗",
            "多重好礼砸向你！点我捡便宜",
            "妈妈都说好！宝宝成长少不了",
            "快来解锁《青春有你》三重好礼"};
    int num;
    Timer timer;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler (  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage ( msg );
            switch (msg.what){
                case 0:
                    textSwitcher.setText ( strings[num++%strings.length] );
                    break;
            }
        }
    };




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.message_layout , container , false );
        message_banner = view.findViewById ( R.id.message_banner_id );
        refreshLayout = view.findViewById ( R.id.refreshLayout );
        music_more_text=view.findViewById ( R.id.music_more_id );
//        gridView=view.findViewById ( R.id.gridView_message_id);
        gifImageView_music_vedio=view.findViewById ( R.id.gifImageView_music_vedio_id );
        ditiedengdai_gif=view.findViewById ( R.id.ditiedengdai_id );
        chat_gif=view.findViewById ( R.id.chat_icon_id );
        iphone_icon_gif=view.findViewById ( R.id.iphone_icon_gif );
        gaode_icon_gif=view.findViewById ( R.id.gaode_gif_id);
        textSwitcher=view.findViewById ( R.id.textSwitcher);
        seacher_text=view.findViewById ( R.id.message_seracher_text_id);
        setUI ();
//        gridView.setAdapter ( new MyAdapter() );

        return view;
    }

    private void setUI() {
        seacher_text.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent ( getActivity (),SeacherActivity.class );
                startActivity ( intent );
            }
        } );
        timer=new Timer (  );
        timer.schedule ( new TimerTask () {
            @Override
            public void run() {
//                textSwitcher.setText ( strings[num++%strings.length] );
                handler.sendEmptyMessage(0); // 发送到UI线程
            }
        },1,4000 );
        textSwitcher.setFactory ( new ViewSwitcher.ViewFactory () {
            @Override
            public View makeView() {
                final TextView textView=new TextView ( getActivity () );
                textView.setTextSize ( 15 );
                textView.setTextColor ( getResources ().getColor ( R.color.colorPrimary  ) );
                textView.setGravity ( Gravity.LEFT |Gravity.CENTER);
                textView.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText ( getActivity (),
                                "即将进入"+textView.getText ().toString (),Toast.LENGTH_SHORT ).show ();


                    }
                } );

                return textView;
            }
        } );
        gaode_icon_gif.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( getActivity (),GaodeActvity.class );
                startActivity ( intent );
            }
        } );
        iphone_icon_gif.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent ( getActivity (),IphoneActivity.class );
                startActivity ( intent );
            }
        } );
        chat_gif.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( getActivity (),ChatActivity.class );
                startActivity ( intent );
            }
        } );
        ditiedengdai_gif.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
Intent intent=new Intent ( getActivity (),DitieActivity.class );
startActivity ( intent );
            }
        } );
        gifImageView_music_vedio.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
//                AssetFileDescriptor fileDescriptor = null;//目录 assets/video/Test.mp4 
//                try {
//                    fileDescriptor = getResources ().getAssets().openFd("video/mymusicvedio.mp4");
//                    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
//                    mediaPlayer.prepare ();
//                } catch (IOException e) {
//                    e.printStackTrace ();
//                }
//
//
                Intent intent=new Intent ( getActivity (),MusicVideoActivity.class );
                startActivity ( intent );
            }
        } );

        music_more_text.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( getActivity (),MusicActivity.class );
                startActivity ( intent );
            }
        } );


            arrayList = new ArrayList<> ();
            arrayList.add ( "http://img.besoo.com/file/201807/17/gqgrlceo544.jpg" );
            arrayList.add ( "http://img.besoo.com/file/201807/17/xtsqpfzk5hv.jpg" );
            arrayList.add ( "http://img.besoo.com/file/201807/17/cxe1amqlrl5.jpg" );
            arrayList.add ( "http://img.besoo.com/file/201807/17/kgbxyaefxx3.jpg" );
            arrayList.add ( "http://img.besoo.com/file/201807/17/sl4fdwg35th.jpg" );
            message_banner.setImageLoader ( new GlideImageLoader () );
            //设置动画效果
            message_banner.setBannerAnimation ( Transformer.Tablet );
            //设置样式 带圆指示器
            message_banner.setBannerStyle ( BannerConfig.CIRCLE_INDICATOR );
            //指示器的位置
            message_banner.setIndicatorGravity ( BannerConfig.RIGHT );
            //自动轮播是否开启
            message_banner.isAutoPlay ( true );
            //设置轮播时间
            message_banner.setDelayTime ( 3000 );

            message_banner.setImages ( arrayList );
            message_banner.setOnBannerListener ( new OnBannerListener () {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText ( getActivity () , position + "被点击了" , Toast.LENGTH_LONG ).show ();
                }
            } );
            message_banner.start ();
        }


    @Override
    public void onResume() {
        super.onResume ();

    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        //在销毁的时候销毁广播
//        unregisterReceiver ( networkChangedReceiver );
    }

     class MyAdapter extends BaseAdapter {
         private int[] imgResId={
                 R.mipmap.img_0,
                 R.mipmap.img_1,
                 R.mipmap.img_0,
                 R.mipmap.img_1,
                 R.mipmap.img_0,
                 R.mipmap.img_1,
                 R.mipmap.img_0,


         };
         private String[] names={
                 "2017年11月2日  星期四",
                 "2017年10月20日  星期五",
                 "2017年9月11日  星期一",
                 "2017年9月1日  星期五",
                 "2017年6月20日  星期二",
                 "2018年3月15日  星期日",
                 "2017年6月18日  星期一",
         };
         @Override
         public int getCount() {
             return names.length;
         }

         @Override
         public Object getItem(int position){
             return position;
         }

         @Override
         public long getItemId(int position) {
             return position;
         }
         @SuppressLint("InflateParams")
         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             View view;
             ViewHolder holder;//持有者
             if (convertView==null){
                 view= LayoutInflater.from ( getActivity () ).inflate ( R.layout.news_listview_item_layout ,null );
                 holder=new ViewHolder ();
                 //第一次解析
                 holder.image=view.findViewById ( R.id.img );
                 holder.tv=view.findViewById ( R.id.text1 );
                 view.setTag ( holder );
             }else {
                 //其他的去持有者拿
                 view=convertView;
                 holder=(ViewHolder)view.getTag ();

             }
             ImageView image=holder.image;
             TextView text=holder.tv;
             image.setImageResource ( imgResId[position] );
             text.setText ( names[position] );
             return view;
         }

    }
    class ViewHolder{
        ImageView image;
        TextView tv;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        timer.cancel ();
    }
}
