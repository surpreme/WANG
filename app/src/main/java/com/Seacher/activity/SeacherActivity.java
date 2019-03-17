package com.Seacher.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.liziyang.dall.Main1;
import com.liziyang.dall.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeacherActivity extends Activity {
    private ImageView seacher_back_btn,seacher_none_text_img;
    private EditText edt_search_text;
    private Button searcher_btn;
    private ListView seacher_list;
    //获取外部数据库资源  这里得到内部手机联系人数据库
    //URL可以输入网址 也可以直接输入地址获取 例如手机内部地址
    //存储至listView
    //listView的设置步骤 第一步指明控件 第二部设置适配器 第三部填充适配器中的数据
    //初始化Map  data数据源
    private List<Map<String,String>> data=new ArrayList<Map<String, String>> (  );
    //列表初始化
    private List<PhoneUser> userList=new ArrayList<PhoneUser>(  );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.seacher_layout);
        initUI();
        setUI();

    }

    private void setUI() {
        // getAllContacts();
        getAllContacts();
        //test();
        //这个for语句必须添加
        for (PhoneUser phoneUser:userList){//for循环 得到联系人姓名和手机号
            Log.i( "TEST","联系人"+phoneUser.getDisplayName()+","+phoneUser.getPhone() );
            //新建一个map 新建需要初始化
            Map<String,String> item=new HashMap<String,String> (  );
            //把得到的电话姓名和手机号添加进去 这里得到数据的方法在另一个类中
            item.put( "name",phoneUser.getDisplayName() );
            item.put( "phone",phoneUser.getPhone() );
            //将得到的数据添加到data
            data.add( item );
        }
        //给listView设置适配器
        //设置适配器 初始化  上下文 数据源 布局文件 列名string 填充数据  显示在布局的哪个控件上 一个是string类型 一个是int类型 多个数据要加[]{"1","2"}
        SimpleAdapter adapter=new SimpleAdapter( this,data,R.layout.seacher_list_item_layout,new String[]{"name","phone"},new int[]{R.id.seacher_item_text1,R.id.seacher_item_text2} );
        //将配置好的适配器添加至李叔同View
        seacher_list.setAdapter( adapter );
        seacher_none_text_img.setVisibility ( View.GONE );
        searcher_btn.setVisibility ( View.GONE );
        seacher_list.setVisibility ( View.GONE );
        edt_search_text.addTextChangedListener ( new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s , int start , int count , int after) {

            }

            @Override
            public void onTextChanged(CharSequence s , int start , int before , int count) {
                if (edt_search_text.getText ().toString ().length ()==0){
                    seacher_none_text_img.setVisibility ( View.GONE );
                    searcher_btn.setVisibility ( View.GONE );
                    seacher_list.setVisibility ( View.GONE );
                }else {
                    seacher_none_text_img.setVisibility ( View.VISIBLE );
                    searcher_btn.setVisibility ( View.VISIBLE );
                    seacher_list.setVisibility ( View.VISIBLE );
                    searcher_btn.setOnClickListener ( new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            edt_search_text.setText ( "" );
                        }
                    } );
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        seacher_back_btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( SeacherActivity.this,Main1.class );
                startActivity ( intent );
            }
        } );
    }

    private void initUI() {
        seacher_list=findViewById ( R.id.seacher_list_id );
        seacher_back_btn=findViewById ( R.id.seacher_back_btn_id );
        seacher_none_text_img=findViewById ( R.id.seacher_none_text_img_id );
        edt_search_text=findViewById ( R.id.edt_search_text_id );
        searcher_btn=findViewById ( R.id.searcher_btn_id );

    }
    public void getAllContacts2(){
        //地址 数据库地址 也可以使用网址
        Uri uri=ContactsContract.Data.CONTENT_URI;
        //所有一般所需的数据都在data1中
        ///查询data1，mimetype_id,raw_contact_id
        //resolver分解器 查询 地址 空...排序为名字
        Cursor cursor=getContentResolver().query( uri,null,null,null,"display_name" );
        //得到id int类型 游标 得到柱指数 contract合同 contact往来  往来合同中的data里的id
        int index_contact_id=cursor.getColumnIndex( ContactsContract.Data.CONTACT_ID );
        int index_data1=cursor.getColumnIndex( ContactsContract.Data.DATA1 );
        int index_mimetype=cursor.getColumnIndex( ContactsContract.Data.MIMETYPE );
        //当游标移动到第一条的时候
        cursor.moveToFirst();
        //初始化phoneUser
        PhoneUser phoneUser=new PhoneUser(  );
        //得到一个string类型的id
        String id=cursor.getString( index_contact_id );
        //integer整数 parse解析parseInt解析int类型数据的id
        int tempId=Integer.parseInt( id );
        //phoneUser设置上解析好的数据tempId 调用phoneUser中的set_id 方法
        phoneUser.set_id( tempId );
        //现在只拿到第一条 希望循环
        do {
            //id = cursor.getString( index_contact_id );
            //如果整数解析int类型的id 等于tempId
            if (Integer.parseInt( id )==tempId){
                //添加phoneUser到userList中
                userList.add( phoneUser );
                //初始化phoneUser
                phoneUser=new PhoneUser(  );
                //指明解析好的数据
                tempId=Integer.parseInt( id );
                //调用方法并用此方法将解析好的数据添加进去
                phoneUser.set_id( tempId );
            }

            //String id=cursor.getString( index_contact_id );
            String data1 = cursor.getString( index_data1 );
            String mimetype = cursor.getString( index_mimetype );
            Log.i( "TEST",id + "," + data1 + "," + mimetype );
            //需要判断是得到是号码 还是名字
            //名称
            if (mimetype.equals( "vnd.android.cursor.item/name" )) {
                phoneUser.setDisplayName( data1 );

            }
            //电话
            if (mimetype.equals( "vnd.android.cursor.item/phone_v2" )) {
                phoneUser.setPhone( data1 );


            }
        }while (cursor.moveToNext());
        //这里直接加； 是为了while循环
        //将数据添加到userList中
        userList.add( phoneUser );




    }

    //使用旧版本查询
    public void getAllContacts(){
        //要使用一个工具 初始化ContentResolver内容分解器 context上下文
        ContentResolver contentResolver=getContentResolver();
        //得到联系人名字地址 ContactsContracts往来合同 中的往来
        Uri uri=ContactsContract.Contacts.CONTENT_URI;
        //得到电话号码 往来合同中的普通数据种类 电话 中的联系人
        Uri phoneNumberUri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //游标 contentResolver内容分解器 查询
        Cursor cursorName=contentResolver.query( uri,null,null,null,null );
        //while循环移动
        while (cursorName.moveToNext()){
            //添加手机号码名字
            //初始化phoneUser
            PhoneUser phoneUser=new PhoneUser(  );
            //一般括号里需要加入上下文 这里每加 在phoneUser构造函数
            //名字游标得到string类型 名字游标得到的柱指数 ContactsContract往来合同 中的往来Contacts 的id

            String contact_id=cursorName.getString( cursorName.getColumnIndex( ContactsContract.Contacts._ID ) );
            //phoneUser设置id 整数解析id
            phoneUser.set_id( Integer.parseInt( contact_id ));
            //设置名字 游标得到的string 有游标得到的柱指数 ContactsContract合同往来 中的往来 的用户名字总称
            phoneUser.setDisplayName( cursorName.getString( cursorName.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME ) ) );
            //数字游标 内容分解器 询问 地址  空 往来合同的普通数据种类 id 因为在这里不知道id的确定值 而且是全部   将解析的联系人名字加上去 排序无

            Cursor cursorPhoneNumber=contentResolver.query(
                    phoneNumberUri,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",
                    new String[]{contact_id},
                    null);
            while (cursorPhoneNumber.moveToNext()){
                //while循环 游标移动
                //Log.i( "TEST","Number"+cursorPhoneNumber.getString( cursorPhoneNumber.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER) ) );
                //调用另一个类的设置 数字游标的得到string  游标中的得到柱指数 往来合同的普通数据类型 的phone 中的数字
                phoneUser.setPhone( cursorPhoneNumber.getString( cursorPhoneNumber.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER )));


            }
            //将phoneUser设置到userList
            userList.add( phoneUser );

        }




    }

    public void test(){
        /** //得到contentResolver
         ContentResolver contentResolver=getContentResolver();
         //ContentProvider 公开接口 insert插入 update delete query
         Uri uri= ContactsContract.Contacts.CONTENT_URI;//得到联系人名称
         //查询的时候返回的是cursor
         Cursor cursor=contentResolver.query( uri,new String[]{ContactsContract.Contacts.DISPLAY_NAME},null,null,null );
         while (cursor.moveToNext()){
         //只有一列 下标为0
         String name=cursor.getString( 0 );
         Log.i( "displayName",name );
         }*/
        /** //得到电话号码
         *
         ContentResolver contentResolver=getContentResolver();
         Uri uri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
         Cursor cursor=contentResolver.query( uri,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,null );
         while (cursor.moveToNext()){
         String number=cursor.getString( 0 );
         Log.i( "TEST",number );
         }*/
        ContentResolver contentResolver=getContentResolver();
        Uri uri=ContactsContract.Data.CONTENT_URI;//2.0以上版本可用
        Cursor cursor=contentResolver.query( uri,new String[]{ContactsContract.Data.DATA1},null,null,null );//最后一个为排序
        while (cursor.moveToNext()){
            String data1=cursor.getString( 0 );
            Log.i( "TEST",data1 );
        }



    }

}
