package com.liziyang.dall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.Ht;

public class More_sencondly extends Fragment {
    private TextView item_text1,item_text2,item_text3,item_text4,item_text5;
    private ListView item_list1,item_list2,item_list3,item_list4,item_list5;
    private TextView nono_text;
    private ImageView nono_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.more_2,container,false );
        item_text1=view.findViewById ( R.id.item_text_id1);
        item_text2=view.findViewById ( R.id.item_text_id2);
        item_text3=view.findViewById ( R.id.item_text_id3);
        item_text4=view.findViewById ( R.id.item_text_id4);
        item_text5=view.findViewById ( R.id.item_text_id5);
        item_list1=view.findViewById ( R.id.text_more_list_id1);
        item_list2=view.findViewById ( R.id.text_more_list_id2);
        item_list3=view.findViewById ( R.id.text_more_list_id3);
        item_list4=view.findViewById ( R.id.text_more_list_id4);
        item_list5=view.findViewById ( R.id.text_more_list_id5);
        nono_text=view.findViewById ( R.id.nono_text_id);
        nono_img=view.findViewById ( R.id.nono_img_id);
        setUI();
        return view;
    }

    private void setUI() {
        item_list1.setAdapter ( new MyAdapterY( ) );
        item_list2.setAdapter ( new MyAdapterY() );
        item_list3.setAdapter ( new MyAdapterY() );
        item_list4.setAdapter ( new MyAdapterY() );
        item_list5.setAdapter ( new MyAdapterY() );
        item_list1.setVisibility ( View.GONE );
        item_list2.setVisibility ( View.GONE );
        item_list3.setVisibility ( View.GONE );
        item_list4.setVisibility ( View.GONE );
        item_list5.setVisibility ( View.GONE );
        item_text1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Ht.my==false){
                    item_list1.setVisibility ( View.GONE );
                    Ht.my=true;
                }else if(Ht.my==true){
                    /**
                     * 如果这里判断语句为else则可能会出现无法判断布尔值的真假
                     */
                    item_list1.setVisibility ( View.VISIBLE );
                    Ht.my=false;


                }

//                item_list1.setVisibility ( View.VISIBLE );
            }
        } );
        item_text2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Ht.my2==false){
                    item_list2.setVisibility ( View.GONE );
                    Ht.my2=true;
                }else if(Ht.my2==true){
                    /**
                     * 如果这里判断语句为else则可能会出现无法判断布尔值的真假
                     */
                    item_list2.setVisibility ( View.VISIBLE );
                    Ht.my2=false;


                }
            }
        } );
        item_text3.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Ht.my3==false){
                    item_list3.setVisibility ( View.GONE );
                    Ht.my3=true;
                }else if(Ht.my3==true){
                    /**
                     * 如果这里判断语句为else则可能会出现无法判断布尔值的真假
                     */
                    item_list3.setVisibility ( View.VISIBLE );
                    Ht.my3=false;


                }

            }
        } );
        item_text4.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Ht.my4==false){
                    item_list4.setVisibility ( View.GONE );
                    Ht.my4=true;
                }else if(Ht.my4==true){
                    /**
                     * 如果这里判断语句为else则可能会出现无法判断布尔值的真假
                     */
                    item_list4.setVisibility ( View.VISIBLE );
                    Ht.my4=false;


                }

            }
        } );
        item_text5.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Ht.my5==false){
                    item_list5.setVisibility ( View.GONE );
                    Ht.my5=true;
                }else if(Ht.my5==true){
                    /**
                     * 如果这里判断语句为else则可能会出现无法判断布尔值的真假
                     */
                    item_list5.setVisibility ( View.VISIBLE );
                    Ht.my5=false;


                }

            }
        } );




        nono_img.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                nono_text.setVisibility ( View.GONE );
                nono_img.setVisibility ( View.GONE );

            }
        } );

    }

    private class MyAdapterY extends BaseAdapter {
        private int[] imgResId={
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying,
                R.mipmap.dianying




        };
        private String[] names={
                "王幼虎喜欢猫","雨晨","迷失的羔羊","QQ购物","QQ邮箱",
                "王幼虎喜欢猫","雨晨","迷失的羔羊","QQ购物","QQ邮箱",
                "王幼虎喜欢猫","雨晨","迷失的羔羊","QQ购物","QQ邮箱"
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
                view= LayoutInflater.from ( getActivity ()).inflate ( R.layout.more2_item,null );
                holder=new ViewHolder ();
                //第一次解析
                holder.image=view.findViewById ( R.id.more2_item_img );
                holder.tv=view.findViewById ( R.id.more2_item_txt );
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
