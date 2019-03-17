package com.liziyang.dall.com.chat.liziyang;


import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.liziyang.dall.R;

import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private  List<ChatMessage> mDatas;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
        inflater=LayoutInflater.from ( context );
        this.mDatas=mDatas;
    }

    @Override
    public int getCount() {
        //size长度
        return mDatas.size ();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get ( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position){
        ChatMessage chatMessage=mDatas.get ( position );
        if (chatMessage.getType ()== ChatMessage.Type.INCOMING){
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        ChatMessage chatMessage=mDatas.get ( position );
        ViewHolder viewHolder=null;
        if (convertView==null){
            if (getItemViewType ( position )==0){
                //判断接受消息的布局
                convertView=inflater.inflate ( R.layout.result_msg,parent,false );
                viewHolder=new ViewHolder ();
                viewHolder.mDate=convertView.findViewById ( R.id.text_message_time );
                viewHolder.mMsg=convertView.findViewById ( R.id.text_message_body);
            }else {
                //判断发送消息的布局
                convertView=inflater.inflate ( R.layout.send_msg,parent,false );
                viewHolder=new ViewHolder ();
                viewHolder.mDate=convertView.findViewById ( R.id.text_message_time2 );
                viewHolder.mMsg=convertView.findViewById ( R.id.text_message_body2);

            }
            convertView.setTag ( viewHolder );

        }else {
            viewHolder= (ViewHolder) convertView.getTag ();
        }
        //设置数据
        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat df=new java.text.SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
        viewHolder.mDate.setText ( (CharSequence) df.format ( chatMessage.getDate () ) );
        viewHolder.mMsg.setText ( chatMessage.getMsg () );

        return convertView;
    }
    private final class ViewHolder{
        TextView mDate;
        TextView mMsg;
    }
}

