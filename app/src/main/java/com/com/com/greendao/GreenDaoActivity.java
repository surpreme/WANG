package com.com.com.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liziyang.dall.R;
import com.liziyang.dall.greengao2.DaoSession;
import com.liziyang.dall.greengao2.UserInfoDao;
import com.okhttp.MyApp;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

public class GreenDaoActivity extends Activity {
    private EditText editText1,editText2;
    private Button addButton,deleteButton,changeButton,searchButton;
    private UserInfo userInfo;
    private long MYID=2018120623;
    private TextView textView;
    //表格不需要额外创建了 就不需要判断第一次安装软件了
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.greendao_layout );
        initUI();
        setUI();


    }

    private void setUI() {
        //在按钮点击的时候也可以判断输入是否为空 为空则点击无效
        addButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addUI();
                //正常
                //添加正常 有数据修改正常
                //不需要加判断 已经封装好
                //insert可能会报错 就是相同数据的 使用insertOrReplace则可以避免 他封装了方法
                //已无错误
            }
        } );
        deleteButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                deleteUI();
                //删除全部  删除行正常
                //删除一列中的某一个键没有这项操作 只能修改
                //已无错误
            }
        } );
        changeButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                changeUI();
                //正常
                //id修改有缺陷 如已有数据已有同name 同其他的就会替换这个相同的数据
                //更新数据update
                //    * 在这里不要添加设置id 否则不会修改
                //    * 修改id用insertOrReplace 但有相同数据又会替换 无replace单独的方法
                //    * 不修改id用update
            }
        } );
        searchButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                SearcherUI();
                //正常
            }
        } );
    }

    private void SearcherUI() {
        /**
         * 查询所有
         * 测试成功
         */
//        DaoSession daoSession=((App)getApplication ()).getDaoSession();
//        List<UserInfo> userInfos=daoSession.loadAll ( UserInfo.class );
        DaoSession daoSession=((MyApp)getApplication ()).getDaoSession ();
        UserInfoDao userInfoDao=daoSession.getUserInfoDao ();
//        /**
//         *  根据条件查询 这里设置id大于3
//         *  使用语句查询ID出错 注意是_id参数 这里我是用db工具打开表看到的键
//         *   其他数据需要大写 如NAME
//         *  测试成功
//         */
//
//        List<UserInfo> list=userInfoDao.queryRaw ( "where _id>?","1881041" );
        /**
         * 从第二条数据开始查询5条信息
         * 可以用来做分页功能
         * offset设置起始位置
         * 测试成功
         */
//        List<UserInfo> list1=userInfoDao.queryBuilder ().offset ( 2 ).limit ( 5 ).list ();

//        /**
//         * 排序1
//         * 查询姓李的所有人 并用id排序
//         * 没加判断 若没有数据不报错
//         * 测试成功
//         */
//        List<UserInfo> list2=userInfoDao.queryBuilder ().where ( UserInfoDao.Properties.Name.eq ( "李紫洋12" ) ).orderAsc ( UserInfoDao.Properties.Id ).list ();
        /**
         * 排序2
         * 查询叫wang的所有人 id是大于2 或（number不等于8   id等于2）
         * 参数gt大于
         * eq等于
         * noteq不等于
         * ge等于或大于
         * 测试成功
         */
//        QueryBuilder<UserInfo>qb=userInfoDao.queryBuilder ();
//        qb.where ( UserInfoDao.Properties.Name.eq ( "wang" ) ,
//                qb.or ( UserInfoDao.Properties.Id.gt ( 2 ),
//                        qb.and ( UserInfoDao.Properties.Id.eq ( 2 ),
//                                UserInfoDao.Properties.Number.notEq ( 8 ) )) );
//        List<UserInfo> list3=qb.list ();
        /**
         * 查询 id在2到74中间 查询2个数据
         * 测试成功
         *
         */
        List<UserInfo> list4=userInfoDao.queryBuilder ().where ( UserInfoDao.Properties.Id.between ( 2,74 ) ).limit ( 2 ).build ().list ();
        /**
         * 这个用来测试输出内容
         * 我使用判断数量来测试 因为logcat并不显示数据库查询到的内容 只是给一个编码
         */

//        for (int i=0;i<list4.size ();i++){
//                Log.i ( "查询内容",list4.get ( i ).toString () +i);
//        }
        /**
         * 这里的logcat打印实现了内容显示
         */
        try {
            for (UserInfo u : list4) {
                Log.i ( "查询内容" , "id" + u.getId () + "名字" + u.getName () + "号码" + u.getNumber () );
                textView.setText ( "查询内容" + "id" + u.getId () + "名字" + u.getName () + "号码" + u.getNumber () );
            }
        }finally {

        }

        /**
         * 懒加载 这里可以使用方法 读出表中的内容
         * 测试成功
         //         */
//        LazyList<UserInfo> l = null;
//        try{
//            l=userInfoDao.queryBuilder ().listLazy ();
//            for ( UserInfo  u:l){
//                Log.i ( "查询内容","id"+u.getId ()+"名字"+u.getName ()+"号码"+u.getNumber () );
//            }
//        }finally {
//            //关闭查询 避免内存泄漏
//            //判断流是否为空 否则会出错
//
//            if (l != null) {
//                l.close ();
//            }
//
//        }







    }

    private void changeUI() {
        DaoSession daoSession=((MyApp)getApplication ()).getDaoSession ();
        UserInfoDao userInfoDao=daoSession.getUserInfoDao ();
        UserInfo userInfo=userInfoDao.queryBuilder ().where ( UserInfoDao.Properties.Name.eq ( "wang" ) ).build ().unique ();
        if (userInfo!=null){
            userInfo.setName ( "王幼虎" );
            userInfo.setNumber ( 1881042 );
            /**
             * 更新数据update
             * 在这里不要添加设置id 否则不会修改
             * 修改id用insertOrReplace 但有相同数据又会替换 无replace单独的方法
             * 不修改id用update
             * 这种做法是为了考虑多表查询 方便处理
             *
             */
            /**
             * insert添加
             * replace更换
             * update更新
             * where(WhereCondition cond, WhereCondition… condMore): 查询条件，参数为查询的条件！
             *
             * or(WhereCondition cond1, WhereCondition cond2, WhereCondition… condMore): 嵌套条件或者，用法同 or。
             *
             * and(WhereCondition cond1, WhereCondition cond2, WhereCondition… condMore): 嵌套条件且，用法同 and。
             *
             * join(Property sourceProperty, Class destinationEntityClass):多表查询，后面会讲。
             *
             * 输出结果有四种方式，选择其中一种最适合的即可，list()返回值是 List,而其他三种返回值均实现 Closeable,需要注意的不使用数据时游标的关闭操作：
             *
             * list （）所有实体都加载到内存中。结果通常是一个没有魔法的 ArrayList。最容易使用。
             *
             * listLazy （）实体按需加载到内存中。首次访问列表中的元素后，将加载并缓存该元素以供将来使用。必须关闭。
             *
             * listLazyUncached （）实体的“虚拟”列表：对列表元素的任何访问都会导致从数据库加载其数据。必须关闭。
             *
             * listIterator （）让我们通过按需加载数据（懒惰）来迭代结果。数据未缓存。必须关闭。
             * eq()：“equal (’=?’)” 等于；
             *
             * notEq() ：“not equal (’<>?’)” 不等于；
             *
             * like()：" LIKE ?" 值等于；
             *
             * between()：" BETWEEN ? AND ?" 取中间范围；
             *
             * in()：" IN (" in命令;
             *
             * notIn()：" NOT IN (" not in 命令;
             *
             * gt()：">?" 大于;
             *
             * lt()："<? " 小于;
             *
             * ge()：">=?" 大于等于;
             *
             * le()："<=? " 小于等于;
             *
             * isNull()：" IS NULL" 为空;
             *
             * isNotNull()：" IS NOT NULL" 不为空;
             */
//            userInfoDao.update ( userInfo );
            userInfo.setId ( MYID );
            userInfoDao.insertOrReplace ( userInfo );
            Toast.makeText ( this,"修改成功",Toast.LENGTH_LONG ).show ();
        }else {
            Toast.makeText ( this,"修改出错或找不到数据",Toast.LENGTH_LONG ).show ();
        }



    }

    private void deleteUI() {
        DaoSession daoSession=((MyApp)getApplication ()).getDaoSession ();
        UserInfoDao userInfoDao=daoSession.getUserInfoDao ();
        /**
         *  指定id删除数据  1l是id=1
         *  不需要判断是否为空 不会崩溃 已封装好
         */


        // userInfoDao.deleteByKey ( 1l );
        /**
         * 指定名字删除列
         * 这里使用了deleteQuery 删除查询 再次点击则不会报错
         * 只能删除一列 如果要删除列中一个数据 需要修改 （警告）
         */
        DeleteQuery<UserInfo> deleteQuery=daoSession.queryBuilder ( UserInfo.class ).where ( UserInfoDao.Properties.Name.eq ( "wang" ) ).buildDelete ();
        deleteQuery.executeDeleteWithoutDetachingEntities ();
        // daoSession.clear ();

        /**
         *  Entity实体是数据库表的关联
         *  例如一对多 或多对多等
         */


        /***
         * 删除全部
         */
        //删除数据
        // 设置为全局对象
        // daoSession.delete ( userInfo );
        //删除全部
        //daoSession.deleteAll ( UserInfo.class );


    }

    private void addUI() {
        /**
         * 初始化上下文对象
         * 设置全局对象
         */
        DaoSession daoSession=((MyApp)getApplication ()).getDaoSession();
        /**
         * 获得全文对象
         */
        //App app= (App) getApplication ();
        // DaoSession daoSession1=app.getDaoSession ();
        // UserInfo userInfo=new UserInfo (  );
        userInfo=new UserInfo (  );
        /**
         * 在表注册的时候写了注解@NotNull
         * 说明本值不可以为空
         */
        if (editText2.getText ().length ()!=0){
            String q=editText2.getText ().toString ();
            if (q!=null){
                int number=Integer.parseInt ( q );
                userInfo.setNumber ( number );
                //这里说明一个情况如果number无值 则number则写入为0 需要添加判断

            }

        }


        /**
         * 获取editText输入数字
         */
        //int c=Integer.parseInt ( editText1.getText ().toString () );
        if (editText1.getText ().toString ().length ()!=0){
            String name=editText1.getText ().toString ();
            /**
             * 将数据通过已经定义的方法添加进去
             */
            userInfo.setName ( name );
            //userInfo.setNumber ( number );
            /**
             * id能修改 在insertOrReplace是处理所有情况的方法 以免其他方法出现bug  建议不要使用一个表格来记录所有数据
             */
            // userInfo.setId ( MYID );

        }


        /**
         * 将封装好的东西添加到daoSession
         */
        //添加已有数据中类似的可能会报错
        //daoSession.insert ( userInfo );
        //无数据则添加 数据存在则替换
        daoSession.insertOrReplace ( userInfo );




    }

    private void initUI() {
        editText1=findViewById ( R.id.greendao_editText);
        editText2=findViewById ( R.id.greendao_editText2);
        addButton=findViewById ( R.id.greendao_button1_id );
        deleteButton=findViewById ( R.id.greendao_button2 );
        changeButton=findViewById ( R.id.greendao_button3 );
        searchButton=findViewById ( R.id.greendao_button4 );
        textView=findViewById ( R.id.greendao_textView );
    }
}
