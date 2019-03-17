package com.com.recy;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter2 extends SimpleAdapter {
    //主要谷歌强制开发人员使用ViewHolder来实现 进行统一化
    //动态定义高度 来模拟瀑布流
    private List<Integer> height;


    public SimpleAdapter2(Context context, List<String> datas){
        super(context,datas);
        //这里适配器是写给主活动互相调用的方法
        height=new ArrayList<Integer> (  );
        for (int i=0;i<datas.size ();i++){
            height.add ( (int) (100+Math.random ()*300) );
        }

    }




    //绑定viewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder , int position) {
        ViewGroup.LayoutParams layoutParams=viewHolder.itemView.getLayoutParams ();
        layoutParams.height=height.get ( position );
        viewHolder.itemView.setLayoutParams ( layoutParams );
        viewHolder.textView.setText ( mDatas.get ( position ) );
        setUpItemEvent ( viewHolder );




    }


}


