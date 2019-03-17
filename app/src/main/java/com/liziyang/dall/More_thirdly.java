package com.liziyang.dall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.com.recy.RecyActivity2;

import java.util.ArrayList;
import java.util.List;

public class More_thirdly extends Fragment {
    //RecyclerView注重view回收和重用
    //无分割线 这就可以自定义
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private com.com.recy.SimpleAdapter adapter;
    private Button button,button2,button3,button4,button5,button6;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.more_3,container,false );
        recyclerView=view.findViewById ( R.id.id_recyclerView );
        button=view.findViewById ( R.id.button_more3_id );
        button2=view.findViewById ( R.id.button2_more3_id );
        button3=view.findViewById ( R.id.button3_more3_id );
        button4=view.findViewById ( R.id.button4_more3_id );
        button5=view.findViewById ( R.id.button5_more3_id );
        button6=view.findViewById ( R.id.button6_more3_id );
        initButton();
        initDatas();
        init();

        return view;
    }

    private void init() {

        adapter=new com.com.recy.SimpleAdapter ( getActivity (),mDatas );
        recyclerView.setAdapter ( adapter );
        //设置RecyclerView的布局管理
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager ( getActivity (),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager ( linearLayoutManager );
        //设置RecyclerView分割线
        //recyclerView.addItemDecoration ( new ItemBottom ( this,ItemBottom.VERTICAL_LIST ) );
        //设置动画 这里是系统给的 就这一个
        //如果想自定义 可以去github找rey的动画
        //https://github.com/search?q=recyclerViewAnimator  网址
        recyclerView.setItemAnimator ( new DefaultItemAnimator () );
        adapter.setOnItemClickListener ( new com.com.recy.SimpleAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(View view , int position) {
                Toast.makeText ( getActivity (),position+"被点击了",Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onItemLongClick(View view , int position) {
                Toast.makeText ( getActivity(),position+"被长按了",Toast.LENGTH_SHORT ).show ();


            }
        } );
    }

    private void initButton() {
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //ListView
                recyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity ()) );

            }
        } );
        button2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //GirdView
                recyclerView.setLayoutManager ( new GridLayoutManager ( getActivity (),3 ) );
            }
        } );
        button3.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //GirdView2
                recyclerView.setLayoutManager ( new StaggeredGridLayoutManager ( 5,StaggeredGridLayoutManager.HORIZONTAL ) );

            }
        } );
        button4.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //瀑布流
                Intent intent=new Intent ( getActivity (),RecyActivity2.class );
                startActivity ( intent );

            }
        } );
        button5.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //增加项
                //调用适配器写的公共方法 这里删除第一个
                adapter.addData ( 1 );

            }
        } );
        button6.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //减少项
                adapter.deleteDate ( 1 );

            }
        } );

    }

    private void initDatas() {
        mDatas=new ArrayList<String> (  );
        //int是数字类型 可以使用'' 单引号实现填充内容 当然这是一个示例 具体使用不能这么写
        for (int i='A';i<='z';i++){
            //添加内容
            mDatas.add ( ""+(char)i );
        }


    }
}
