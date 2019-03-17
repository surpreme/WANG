package com.okhttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager {
    //atomicReference原参考
    private static final AtomicReference<DownloadManager> INSSTANCE = new AtomicReference<> ();
    //用来放各个下载的请求
    private HashMap<String, okhttp3.Call> downCalls;
    //okhttpClient 是okhttp管理类 client是客户的意思
    private OkHttpClient mClient;

    //获得一个单例类
    public static DownloadManager getInstance() {
        for (;;) {
            DownloadManager current = INSSTANCE.get ();
            if (current != null) {
                return current;

            }
            current = new DownloadManager ();
            if (INSSTANCE.compareAndSet ( null , current )) {
                return current;
            }

        }
    }

    private DownloadManager() {
        downCalls = new HashMap<> ();
        mClient = new OkHttpClient.Builder ().build ();
    }

    public void downLoad(String url , DownLoadObserver downLoadObserver) {
        io.reactivex.Observable.just ( url ).filter ( s -> !downCalls.containsKey ( s ) ) //call的map已经有了 就证明已经在下载了 则这次不下载
                .flatMap ( s -> io.reactivex.Observable.just ( createDownInfo ( s ) ) ).map ( this::getRealFileName )//检测本地文件夹 生成新的文件名
                .flatMap ( downloadInfo -> io.reactivex.Observable.create ( new DownloadSubscribe ( downloadInfo ) ) )//下载
                .observeOn ( AndroidSchedulers.mainThread () )//在主线程中回调
                .subscribeOn ( Schedulers.io () )//在子线程中执行
                .subscribe (downLoadObserver);//添加观察者

    }

    //创建downInfo url请求网址
    private DownloadInfo createDownInfo(String url) {
        DownloadInfo downloadInfo = new DownloadInfo ( url );
        long contentLength = getContextLength ( url );//得到文件大小
        //getContextLength是自定义的方法
        downloadInfo.setTotal ( contentLength );
        //这里需要设置文件的后缀名 设置文件的格式
        String fileName = url.substring ( url.lastIndexOf ( "/" ))+".png";
        downloadInfo.setFileName ( fileName );


        return downloadInfo;
    }

    private DownloadInfo getRealFileName(DownloadInfo downloadInfo) {
        String fileName = downloadInfo.getFileName ();
        long downloadLength = 0, contentLength = downloadInfo.getTotal ();
        File file = new File ( MyApp.sContext.getFilesDir () , fileName );
        if (file.exists ()) {
            downloadLength = file.length ();

        }
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf ( "." );
            String fileNmaeOther;
            if (dotIndex == -1) {
                fileNmaeOther = fileName + "(" + i + ")";

            } else {
                fileNmaeOther = fileName.substring ( 0 , dotIndex ) + "(" + i + ")" + fileName.substring ( dotIndex );
            }
            File newFile = new File ( MyApp.sContext.getFilesDir () , fileNmaeOther );
            file = newFile;
            downloadLength = newFile.length ();
            i++;
        }
        downloadInfo.setProgress ( downloadLength );
        downloadInfo.setFileName ( file.getName () );
        return downloadInfo;
    }

    public void cancel(String url) {
        okhttp3.Call call = downCalls.get ( url );
        if (call != null) {
            call.cancel ();
        }

        downCalls.remove ( url );
    }


    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private DownloadInfo downloadInfo;

        DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        /**
         * Called for each Observer that subscribes.
         *
         * @param e the safe emitter instance, never null
         * @throws Exception on error
         */
        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            //getUrl是在downloadInfo中自定义的方法
            String url = downloadInfo.getUrl ();
            long downloadLength = downloadInfo.getProgress ();//已经下载好的长度
            long contentLength = downloadInfo.getTotal ();//得到文件的总长度
            e.onNext ( downloadInfo );//初始化进度信息
            Request request = new Request.Builder ().addHeader ( "RANGE" , "bytes=" + downloadLength + "-" + contentLength )//确定下载的范围 添加此头 则服务器就可以跳过已经下载好的部分
                    .url ( url ).build ();
            Call call=mClient.newCall ( request );
            downCalls.put ( url,call );//把这个放到call中 方便取消
            Response response=call.execute ();



            File file=new File ( MyApp.sContext.getFilesDir(),downloadInfo.getFileName () );
            InputStream is=null;
            FileOutputStream fileOutputStream=null;
            try{
                if (response.body () != null) {
                    is=response.body ().byteStream ();
                    fileOutputStream=new FileOutputStream ( file,true );
                    byte[] buffer=new byte[2048];//缓冲数组为2kb
                    int len;
                    while ((len=is.read (buffer))!=-1){
                        fileOutputStream.write ( buffer,0,len );
                        downloadLength+=len;
                        downloadInfo.setProgress ( downloadLength );
                        e.onNext ( downloadInfo );

                    }
                    fileOutputStream.flush ();
                    downCalls.remove ( url );

                }

            }finally {
                //关闭IO流
                IOUtil.closeAll(is,fileOutputStream);
            }
            //完成
            e.onComplete ();


        }

    }
    private long getContextLength(String downloadUrl){
        //获得下载长度
        Request request=new Request.Builder ().url ( downloadUrl ).build ();
        try{
            Response response=mClient.newCall ( request ).execute ();
            if (response!=null&&response.isSuccessful ()){
                if (response.body () != null) {
                    long contentLength=response.body ().contentLength ();
                    response.close ();
                    return contentLength==0?DownloadInfo.TOTAL_ERROR:contentLength;
                }

            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return DownloadInfo.TOTAL_ERROR;

    }
}


