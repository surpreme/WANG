package com.setting.more;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liziyang.dall.R;
import com.okhttp.DownLoadObserver;
import com.okhttp.DownloadInfo;
import com.okhttp.DownloadManager;

public class LiXianDownActivity extends Activity  implements View.OnClickListener {
    private Button down1, down2, down3;
    private Button cancel1, cancel2, cancel3;
    private ProgressBar progressBar1, progressBar2, progressBar3;
    private TextView textView;
    //取消和暂停都一样是暂停下载 本来下载的文件只是没下载完毕 取消则是多了一步删除文件
    private String url = "https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=21e1cf0db1014a90813e41bb914c5e2f/e61190ef76c6a7eff1edd899f5faaf51f2de6641.jpg";
    private String url2 = "";
    private String url3 = "";
    private EditText editText1,editText2,editText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.lianxiandown_layout );
        down1 = bindView ( R.id.down1 );
        cancel1 = bindView ( R.id.cancel1 );
        textView=bindView ( R.id.baifenbi_text_id);

        progressBar1 = bindView ( R.id.progressBar1 );
        progressBar2 = bindView ( R.id.progressBar2 );
        progressBar3 = bindView ( R.id.progressBar3 );
        down1.setOnClickListener ( this );
        cancel1.setOnClickListener ( this );
//        editText1=findViewById ( R.id )

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.down1:
                DownloadManager.getInstance ().downLoad ( url , new DownLoadObserver () {
                    @Override
                    public void onNext(DownloadInfo value) {

                        super.onNext ( value );
                        progressBar1.setMax ( (int) value.getTotal () );//总长度
                        progressBar1.setProgress ( (int) value.getProgress () );//下载的进度
                        //显示百分比
                        int a=((int) value.getTotal ())/100;
                        textView.setText ( ((int) value.getProgress ())/a+"/100%");

                    }

                    @Override
                    public void onComplete() {
                        if (downloadInfo != null) {
                            Toast.makeText ( LiXianDownActivity.this , downloadInfo.getFileName () + Uri.encode ( "-下载完毕" ) , Toast.LENGTH_LONG ).show ();
                        }

                    }
                } );
                break;
            case R.id.down2:
                break;
            case R.id.down3:
                break;
            case R.id.cancel1:
                DownloadManager.getInstance ().cancel ( url );
                break;
            case R.id.cancel2:
                break;
            case R.id.cancel3:
                break;

        }

    }

    private <T extends View> T bindView(@IdRes int id) {
        View viewById = findViewById ( id );
        return (T) viewById;
    }
}

