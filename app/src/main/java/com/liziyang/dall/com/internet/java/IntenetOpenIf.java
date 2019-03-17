package com.liziyang.dall.com.internet.java;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class IntenetOpenIf extends BroadcastReceiver {
    @Override
    public void onReceive(Context context , Intent intent) {
        int netWorkStates = NetWorkUtils.getNetWorkStates ( context );

        switch (netWorkStates) {
            case NetWorkUtils.TYPE_NONE:
                //断网了
                Toast.makeText ( context,"网络断开连结",Toast.LENGTH_LONG ).show ();
                break;
            case NetWorkUtils.TYPE_MOBILE:
                //打开了移动网络
//                Toast.makeText ( context,"网络已恢复正常",Toast.LENGTH_LONG).show ();

                break;
            case NetWorkUtils.TYPE_WIFI:
                //打开了WIFI
//                Toast.makeText ( context,"网络已恢复正常",Toast.LENGTH_LONG).show ();
                break;

            default:
                break;


        }
    }
}

