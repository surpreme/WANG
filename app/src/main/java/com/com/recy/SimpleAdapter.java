package com.com.recy;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liziyang.dall.R;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder>{
    //主要谷歌强制开发人员使用ViewHolder来实现 进行统一化
    private LayoutInflater inflater;
    private Context context;
    public List<String> mDatas;
    //定义一个接口来实现项的点击监听
    public interface OnItemClickListener{
        //对外公开两个方法
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);


    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }


    public SimpleAdapter(Context context, List<String> datas){
        //这里适配器是写给主活动互相调用的方法
        this.context=context;
        this.mDatas=datas;
        this.inflater=LayoutInflater.from ( context );

    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            textView=itemView.findViewById ( R.id.item_text );
        }
    }

    //创建viewHolder


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int i) {
        View view=inflater.inflate ( R.layout.item_simple,viewGroup,false );
        MyViewHolder viewHolder=new MyViewHolder ( view );
        return viewHolder;
    }

    //绑定viewHolder
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder , final int position) {
        //注意加了final  加了可以使用他的对象
        viewHolder.textView.setText ( mDatas.get ( position ) );
        setUpItemEvent(viewHolder);


    }




    protected void setUpItemEvent(final MyViewHolder viewHolder) {
        if (onItemClickListener!=null) {
            viewHolder.itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {

                    int ps = viewHolder.getLayoutPosition ();
                    onItemClickListener.onItemClick ( viewHolder.itemView , ps );


                }

            } );
            viewHolder.itemView.setOnLongClickListener ( new View.OnLongClickListener () {
                @Override
                public boolean onLongClick(View v) {
                    int ps = viewHolder.getLayoutPosition ();
                    onItemClickListener.onItemLongClick ( viewHolder.itemView , ps );
                    return false;
                }
            } );
        }

    }

    public void addData(int position){
        mDatas.add ( position,"ONE" );
        //别写错了 是下面那个
        // notifyDataSetChanged ();
        //通知notify  项Item  插入Inserted
        notifyItemInserted ( position );

    }
    public void deleteDate(int position){
        mDatas.remove ( position );
        notifyItemRemoved ( position );
    }


    @Override
    public int getItemCount() {
        //得到个数
        //即为数据列的个数
        return mDatas.size ();
    }
}

