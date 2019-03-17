package com.okhttp;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.liziyang.dall.greengao2.DaoMaster;
import com.liziyang.dall.greengao2.DaoSession;

import org.greenrobot.greendao.database.Database;


public class MyApp extends Application {
    //为了使用一些文件路径 全局context比较方便
    //Application是context的子类 使用他是最方便
    /**
     * 全局的context对象
     */
    public static Context sContext;
    //声明一个全局对象
    //需要在功能清单注册
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate ();
        sContext=this;
        MultiDex.install(this);
//        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper ( this,"come.db" );
//        Database db=helper.getWritableDb ();
//        daoSession=new DaoMaster ( db ).newSession ();
        /**
         * 注意当你写完整个类再加密的话会报错
         * 因为表已经在手机中创建完毕 要不更新数据库版本 要不换一个名字来测试
         */
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper ( this,"co1me.db" );
        Database database=helper.getEncryptedWritableDb ( "wangyouhu.." );
        DaoMaster daoMaster=new DaoMaster ( database );
        daoSession=daoMaster.newSession ();
    }
    //这个重写方法会用到的 必不可少 他是一个工具
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
