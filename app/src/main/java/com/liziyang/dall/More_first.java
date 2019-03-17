package com.liziyang.dall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class More_first extends Fragment {
    private GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.more_1,container,false );
        gridView=view.findViewById ( R.id.news_gridView_id);
        initUI();
        return view;
    }

    private void initUI() {
        gridView.setAdapter ( new News_MyAdapter () );
    }
    class News_MyAdapter extends BaseAdapter {

        private int[] imgResId={
                R.mipmap.img_0,
                R.mipmap.img_1,
                R.mipmap.img_0,
                R.mipmap.img_1,
                R.mipmap.img_0,
                R.mipmap.img_1,
                R.mipmap.img_1



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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;//持有者
            if (convertView==null){
                view= LayoutInflater.from ( getActivity ()).inflate ( R.layout.news_listview_item_layout,null );
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

}
