package com.liziyang.dall;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.CameraActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liziyang.dall.com.PermissionUtils.PermissionUtils;
import com.liziyang.dall.com.zxing.SaoyisaoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 继承扩展
 */
public class Main1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private String navigationView_user_icon_URL="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2012517582,1970996463&fm=26&gp=0.jpg";


    /**
     * fragment
     */
    private MessageFragment messageFragment;
    private NewsFragment newsFragment;
    private SettingFragment settingFragment;
    private View messageLayout;
    private View newsLayout;
    private View settingLayout;
    private ImageView message_image;
    private ImageView news_image;
    private ImageView setting_image;
    private TextView message_text;
    private TextView news_text;
    private TextView setting_text;
    private FragmentManager fragmentManager;
    /**
     * fragment重叠问题的解决
     */
    private Bundle bundle;



    /**
     * 声明控件
     */
    private ImageButton more_logo_icon;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private  DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private PopupWindow popupWindow;
    private ImageView user_icon;
    private boolean logif=true;
    private TextView user_name_text;



    private Banner banner;
    ArrayList<String> arrayList;


    /**
     * 注 用户头像尺寸60*60
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        this.bundle=savedInstanceState;
        setContentView ( R.layout.activity_main1 );
        /**
         * 初始化并为控件设计功能
         */
        init();
        madeUI();
        QUANXIAN();
        fragmentManager = getSupportFragmentManager ();



    }



    private void setTabSelection(int index) {
        //清除选中
        clearSelection();
        FragmentTransaction transaction=fragmentManager.beginTransaction ();
        //隐藏碎片
        hideFragment(transaction);
        switch (index){
            case 0:
                message_image.setImageResource ( R.mipmap.skin_tab_icon_now_selected );
                message_text.setTextColor ( Color.parseColor ( "#82858b" ) );
                //判断碎片是否为空 以免重复建立 影响性能
                if (messageFragment==null){
                    messageFragment=new MessageFragment ();
                    transaction.add ( R.id.content,messageFragment );
                }else {
                    transaction.show ( messageFragment );
                }
                break;
            case 1:
                news_image.setImageResource ( R.mipmap.skin_tab_icon_plugin_selected);
                news_text.setTextColor ( Color.parseColor ( "#82858b" ) );


                //判断碎片是否为空 以免重复建立 影响性能
                if (newsFragment==null){
                    newsFragment=new NewsFragment ();
                    transaction.add ( R.id.content,newsFragment );
                }else {
                    transaction.show ( newsFragment );
                }
                break;
            case 2:
                setting_image.setImageResource ( R.mipmap.skin_tab_icon_contact_selected );
                setting_text.setTextColor ( Color.parseColor ( "#82858b" ) );
                //判断碎片是否为空 以免重复建立 影响性能
                if (settingFragment==null){
                    settingFragment=new SettingFragment ();
                    transaction.add ( R.id.content,settingFragment );
                }else {
                    transaction.show ( settingFragment );
                }
                break;
        }
        transaction.commit ();
    }

    private void hideFragment(FragmentTransaction transaction) {
        //隐藏碎片 避免重叠
        if (messageFragment!=null){
            transaction.hide ( messageFragment );
        }if (newsFragment!=null){
            transaction.hide ( newsFragment );
        }if (settingFragment!=null){
            transaction.hide ( settingFragment );
        }
    }

    private void clearSelection() {
        //设置清除后的图片文字修改
        message_image.setImageResource ( R.mipmap.skin_tab_icon_now_normal );
        message_text.setTextColor ( Color.parseColor ( "#82858b" ) );
        news_image.setImageResource ( R.mipmap.skin_tab_icon_plugin_normal );
        news_text.setTextColor ( Color.parseColor ( "#82858b" ) );
        setting_image.setImageResource ( R.mipmap.skin_tab_icon_contact_normal );
        setting_text.setTextColor ( Color.parseColor ( "#82858b" ) );


    }

    @Override
    protected void onStart() {
        super.onStart ();
        setTabSelection ( 0 );

    }

    /**
     * 处理fragment重叠
     */
    @Override
    protected void onResume() {
        super.onResume ();
        if (bundle!=null){
            setTabSelection ( 0 );
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState!=null) {
            FragmentTransaction transaction=fragmentManager.beginTransaction ();
            if (messageFragment != null) {
                transaction.hide ( messageFragment );
            }
            if (newsFragment != null) {
                transaction.hide ( newsFragment );
            }
            if (settingFragment != null) {
                transaction.hide ( settingFragment );
            }
            transaction.commit ();
        }
        super.onSaveInstanceState ( outState );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            FragmentTransaction transaction=fragmentManager.beginTransaction ();
            if (messageFragment != null) {
                transaction.hide ( messageFragment );
            }
            if (newsFragment != null) {
                transaction.hide ( newsFragment );
            }
            if (settingFragment != null) {
                transaction.hide ( settingFragment );
            }
            transaction.commit ();
        }
        super.onRestoreInstanceState ( savedInstanceState );
    }

    private void QUANXIAN() {
        /**
         * 判断是否第一次打开软件
         */
        SharedPreferences sharedPreferences=getSharedPreferences( "isfer",Activity.MODE_PRIVATE );
        boolean isfer=sharedPreferences.getBoolean( "isFirstIn",true );
        if (isfer){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean( "isFirstIn",false );
            editor.commit();
            /**
             * 一次性调用全部 不是最佳做法
             */
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_GET_ACCOUNTS, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_CALL_PHONE, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_PHONE_STATE, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
//            PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, permissionGrant);
            showCamera();
            getAccounts();
            callPhone();
            readPhoneState();
            accessFineLocation();
            accessCoarseLocation();
            readExternalStorage();
            writeExternalStorage();
            recordAudio();







        }else {

        }
    }

    /**
     * 当单个寻求得到权限用这些方法
     * @param
     */
    public void showCamera(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            PermissionUtils.requestPermission(this,PermissionUtils.CODE_CAMERA,permissionGrant);
        }
    }
    public void getAccounts(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_GET_ACCOUNTS , permissionGrant );
        }
    }
    public void callPhone() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_CALL_PHONE , permissionGrant );
        }
    }
    public void readPhoneState() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_READ_PHONE_STATE , permissionGrant );
        }
    }
    public void accessFineLocation() {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission ( this , PermissionUtils.CODE_ACCESS_FINE_LOCATION , permissionGrant );
            }
    }
    public void accessCoarseLocation() {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                PermissionUtils.requestPermission ( this , PermissionUtils.CODE_ACCESS_COARSE_LOCATION , permissionGrant );
            }
    }
    public void readExternalStorage() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_READ_EXTERNAL_STORAGE , permissionGrant );
        }
    }
    public void writeExternalStorage() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE , permissionGrant );
        }
    }
    public void recordAudio() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission ( this , PermissionUtils.CODE_RECORD_AUDIO , permissionGrant );
        }
    }
    public PermissionUtils.PermissionGrant permissionGrant=new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch(requestCode){
                case PermissionUtils.CODE_RECORD_AUDIO:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_GET_ACCOUNTS:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_READ_PHONE_STATE:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_CALL_PHONE:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_CAMERA:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();

                    break;

                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:

                    Toast.makeText(Main1.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();

                    break;

                default:

                    break;

            }
        }
    };
    public void onRequestPermissionResult(final int requestCode,String[] permissions,int[] grantResults){
        PermissionUtils.requestPermissionsResult( Main1.this,requestCode,permissions,grantResults,permissionGrant );
    }


    private void madeUI() {
        banner.setImageLoader ( new GlideImageLoader () );
        //设置动画效果
        banner.setBannerAnimation(Transformer.Tablet);
        //设置样式 带圆指示器
        banner.setBannerStyle ( BannerConfig.CIRCLE_INDICATOR );
        //指示器的位置
        banner.setIndicatorGravity ( BannerConfig.RIGHT );
        //自动轮播是否开启
        banner.isAutoPlay ( true );
        //设置轮播时间
        banner.setDelayTime ( 3000 );

        banner.setImages ( arrayList );
        banner.setOnBannerListener ( new OnBannerListener () {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText ( Main1.this,position+"被点击了",Toast.LENGTH_LONG ).show ();
            }
        } );
        banner.start ();
        Glide.with ( Main1.this ).
                load ( navigationView_user_icon_URL ).
                apply ( RequestOptions.bitmapTransform ( new CropCircleTransformation (  ) ) ).
                into ( user_icon );
        more_logo_icon.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showBottomPopupWindow();
            }
        } );
        drawer.addDrawerListener ( toggle );
        toggle.syncState ();
        navigationView.setNavigationItemSelectedListener ( this );
        user_name_text.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                /**
                 * 用户名片界面
                 */
                Intent intent =new Intent ( Main1.this,User_card_activity.class );
                startActivity ( intent );
            }
        } );
        user_icon.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (logif){
                    Intent intent=new Intent ( Main1.this,LoginActivity.class );
                    startActivity ( intent );
                }else {

                }


            }
        } );

    }

    private void showBottomPopupWindow() {
        /**
         * 解析出使用的布局
         */
        @SuppressLint("InflateParams") View more_icon_view= LayoutInflater.from ( Main1.this ).inflate ( R.layout.moreiconlayout , null );
        /**
         * 设置背影 这里让父布局变背景颜色
         * 最佳是获得屏幕管理者
         */
        setBackGroundAlpha ( 0.5f ,Main1.this);
        //如果代码和xml同时指定布局的位置高宽度等 以代码为准
        /**
         * 构造传入的参数
         */
        popupWindow = new PopupWindow ( more_icon_view , LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT , true );
        /**
         * 将布局设置上去
         */
        popupWindow.setContentView ( more_icon_view );
        /**
         * 布局中的ui控件
         */
        LinearLayout no1 = more_icon_view.findViewById ( R.id.linearLayout1 );
        LinearLayout no2 = more_icon_view.findViewById ( R.id.linearLayout2 );
        LinearLayout no3 = more_icon_view.findViewById ( R.id.linearLayout3 );
        LinearLayout no4 = more_icon_view.findViewById ( R.id.linearLayout4);

        no1.setOnClickListener ( this );
        no2.setOnClickListener ( this );
        no3.setOnClickListener ( this );
        no4.setOnClickListener ( this );
        /**
         * 设置动画
         */
//        popupWindow.setAnimationStyle ( R.style.contextMenuAnim );
        /**
         * 显示popupWindows
         * 1显示的布局view
         * 2设置显示的位置
         * 方法1 指定位置
         * 方法2 控件下面
         * 方法3 控件延伸
         * //相对某个控件的位置（正左下方），无偏移
         * showAsDropDown(View anchor)：
         * //相对某个控件的位置，有偏移;xoff表示x轴的偏移，正值表示向左，负值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
         * showAsDropDown(View anchor, int xoff, int yoff)：
         * //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
         * showAtLocation(View parent, int gravity, int x, int y)：
         */
        /**
         * 正下方
         */
        popupWindow.showAsDropDown ( more_logo_icon );
        /**
         * 迁移正下方
         */
//        popupWindow.showAsDropDown ( imageButton,-200,100 );
//        popupWindow.setFocusable(true);
        /**
         * popupWindow关闭调用的方法
         * 这里处理背影
         */
        popupWindow.setOnDismissListener ( new PopupWindow.OnDismissListener () {
            @Override
            public void onDismiss() {
                // relativeLayout.setBackgroundColor ( getResources ().getColor ( R.color.mycolor ) );
                setBackGroundAlpha ( 1.0f ,Main1.this);

            }
        } );

    }

    private void setBackGroundAlpha(float alpha , Context context) {
        WindowManager.LayoutParams layoutParams=((Activity)context).getWindow ().getAttributes ();
        layoutParams.alpha=alpha;
        ((Activity)context).getWindow ().addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        ((Activity)context).getWindow ().setAttributes ( layoutParams );
    }




    private void init() {
        messageLayout = findViewById ( R.id.message_layout );
        newsLayout = findViewById ( R.id.news_layout );
        settingLayout = findViewById ( R.id.setting_layout );
        message_image = findViewById ( R.id.image_message );
        news_image = findViewById ( R.id.image_news );
        setting_image = findViewById ( R.id.image_setting );
        message_text = findViewById ( R.id.message_text );
        news_text = findViewById ( R.id.news_text );
        setting_text = findViewById ( R.id.setting_text );
        messageLayout.setOnClickListener ( this );
        newsLayout.setOnClickListener ( this );
        settingLayout.setOnClickListener ( this );
         toolbar = (Toolbar) findViewById ( R.id.toolbar );
        /**
         * 注这里用到顶部官方导航栏 这个设置要放到初始化中 否则点击无作用
         */
        setSupportActionBar ( toolbar );
        drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        navigationView = (NavigationView) findViewById ( R.id.nav_view );
        more_logo_icon=findViewById ( R.id.more_logo_id);
        View view=navigationView.getHeaderView ( 0 );
        user_icon=view.findViewById ( R.id.user_icon_id);
        banner=findViewById ( R.id.banner);
        user_name_text=view.findViewById ( R.id.user_name_id);
        toggle = new ActionBarDrawerToggle ( this ,
                drawer ,
                toolbar ,
                R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close );
        arrayList = new ArrayList<> ();
        arrayList.add("http://img.besoo.com/file/201807/17/gqgrlceo544.jpg");
        arrayList.add("http://img.besoo.com/file/201807/17/xtsqpfzk5hv.jpg");
        arrayList.add("http://img.besoo.com/file/201807/17/cxe1amqlrl5.jpg");
        arrayList.add("http://img.besoo.com/file/201807/17/kgbxyaefxx3.jpg");
        arrayList.add("http://img.besoo.com/file/201807/17/sl4fdwg35th.jpg");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        if (drawer.isDrawerOpen ( GravityCompat.START )) {
            drawer.closeDrawer ( GravityCompat.START );
        } else {
            super.onBackPressed ();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater ().inflate ( R.menu.main1 , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected ( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent ( Main1.this,CameraActivity.class );
            startActivity ( intent );
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        drawer.closeDrawer ( GravityCompat.START );
        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.linearLayout1:
               Intent add_intent=new Intent ( Main1.this,AddFriendsActivty.class );
               startActivity ( add_intent );
                break;
            case R.id.linearLayout2:
                Intent intent_Saoyisao=new Intent ( Main1.this,SaoyisaoActivity.class );
                startActivity ( intent_Saoyisao );

                break;
            case R.id.linearLayout3:
                Toast.makeText ( this,"第3个按钮被点击了",Toast.LENGTH_LONG).show ();
                break;
            case R.id.linearLayout4:
                Toast.makeText ( this,"第1个按钮被点击了",Toast.LENGTH_LONG).show ();
                break;
            case R.id.message_layout:
                setTabSelection(0);
                break;
            case R.id.news_layout:
                setTabSelection(1);
                break;
            case R.id.setting_layout:
                setTabSelection(2);
                break;
                default:
                    break;
        }

    }


}
