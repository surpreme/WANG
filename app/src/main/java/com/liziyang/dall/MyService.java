package com.liziyang.dall;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        //onStartCommand开始命令  command命令
        //这里的文本显示使用的是系统报警服务
        Toast.makeText( getApplicationContext(),"时间到了",Toast.LENGTH_SHORT ).show();
        return super.onStartCommand( intent,flags,startId );
    }

    //binder捆绑 bundle包
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
