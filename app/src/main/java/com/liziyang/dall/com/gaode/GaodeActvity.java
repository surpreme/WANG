package com.liziyang.dall.com.gaode;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.sql.Date;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.liziyang.dall.R;

public class GaodeActvity extends Activity implements LocationSource,AMapLocationListener {
    //注  浮动布局只能放在mapview外 否则不显示
    /**
     * 地图控件
     */
    private MapView mapView;
    /**
     * 地图对象
     */
    private AMap aMap;
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    /**
     * 显示位置的textView
     * @param savedInstanceState
     */
    private TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.gaode_layout );
        /**
         * 查找控件
         */
        mapView=findViewById ( R.id.mapView);
        textView=findViewById ( R.id.gaode_textview );
        /**
         * 设置缓存并创建
         */
        mapView.onCreate ( savedInstanceState );
        /**
         * 地图控件设置地图对象
         */
        aMap=mapView.getMap ();
        /**
         * 设置显示定位按钮 并可以点击
         */
        UiSettings settings=aMap.getUiSettings ();
        /**
         * 设置定位监听
         */
        aMap.setLocationSource ( this );
        /**
         * 是否显示定位按钮
         */
        settings.setMyLocationButtonEnabled ( true );
        /**
         * 是否可以触发定位并显示定位层
         */
        aMap.setMyLocationEnabled ( true );
        /**
         * 定位的小图标
         */
        MyLocationStyle myLocationStyle=new MyLocationStyle ();
        /**
         * 定位图标对象是bitmap
         */
        myLocationStyle.myLocationIcon ( BitmapDescriptorFactory.fromResource ( R.drawable.my ) );
        //定义背景等
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        myLocationStyle.interval(10000);
        /**
         * 给地图对象设置定位样式
         */
        aMap.setMyLocationStyle (  myLocationStyle);
        /**
         * 开始定位
         */
        initLocal();

    }

    private void initLocal() {
        /**
         *   @@@设置发起端参数
         *
         */


        /**
         * 这里设置上下文
         */
        mLocationClient =new AMapLocationClient (getApplicationContext ());
        /**
         * 设置定位回调
         */
        mLocationClient.setLocationListener ( this );
        /**
         * 初始化定位参数
         */
        mLocationOption=new AMapLocationClientOption ();
        /**
         * 设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
         */
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        mLocationOption.setHttpTimeOut ( 50000 );
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


    }

    /**
     * 定位回调参数
     * @param amapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                /**
                 * 设置时间格式
                 */
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng (amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    /**
                     * 添加定位图标 可显示定位信息
                     */

                    aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    textView.setText ( buffer.toString () );
                    isFirstLoc = false;
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("RUNNING", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }

    }

    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {

        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() +  "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title("当前定位信息");
        //子标题
        options.snippet(buffer.toString());
        //设置多少帧刷新一次图片资源
        options.period(30000);

        return options;
    }

    /**
     * //激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

    }

    /**
     * 停止定位
     */

    @Override
    public void deactivate() {
        mListener = null;

    }


    /**
     * 活动生命周期显示做的处理
     * 必须重写
     */
    @Override
    protected void onResume() {
        super.onResume ();
        mapView.onResume ();
        Log.d ( "测试" , Test.sHA1 ( this ) );
    }
    /**
     * 活动生命周期失去焦点做的处理
     * 必须重写
     */
    @Override
    protected void onPause() {
        super.onPause ();
        mapView.onPause ();
    }
    /**
     * 活动生命周期失去焦点做的处理
     * 必须重写
     */

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mapView.onDestroy ();
    }

    /**
     * 缓存处理
     * 必须重写
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState ( outState );
        mapView.onSaveInstanceState(outState);
    }

}
