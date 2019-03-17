package com.com.recy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liziyang.dall.R;

import java.util.ArrayList;
import java.util.List;

public class RecyActivity2 extends AppCompatActivity {
    //RecyclerView注重view回收和重用
    //无分割线 这就可以自定义
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private SimpleAdapter2 adapter;
    private Button button,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.more_3 );
        initDatas();
        initView();
        initButton();
        adapter=new SimpleAdapter2 ( this,mDatas );
        recyclerView.setAdapter ( adapter );
        //设置RecyclerView的布局管理
        recyclerView.setLayoutManager ( new StaggeredGridLayoutManager ( 3,StaggeredGridLayoutManager.VERTICAL ) );
        //设置RecyclerView分割线
        //recyclerView.addItemDecoration ( new ItemBottom ( this,ItemBottom.VERTICAL_LIST ) );
        adapter.setOnItemClickListener ( new SimpleAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(View view , int position) {

            }

            @Override
            public void onItemLongClick(View view , int position) {
                adapter.deleteDate ( position );
//                Toast.makeText ( getApplicationContext (),"该项已删除",Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

    private void initButton() {
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //ListView
                //recyclerView.setLayoutManager ( new LinearLayoutManager ( Main2.this ) );

            }
        } );
        button2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //GirdView
                //recyclerView.setLayoutManager ( new GridLayoutManager ( Main2.this,3 ) );
            }
        } );
        button3.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //GirdView2
                //recyclerView.setLayoutManager ( new StaggeredGridLayoutManager ( 5,StaggeredGridLayoutManager.HORIZONTAL ) );

            }
        } );
        button4.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //瀑布流

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
    private void initView() {
        recyclerView=findViewById ( R.id.id_recyclerView );
        button=findViewById ( R.id.button_more3_id);
        button2=findViewById ( R.id.button2_more3_id );
        button3=findViewById ( R.id.button3_more3_id );
        button4=findViewById ( R.id.button4_more3_id );

    }
}

