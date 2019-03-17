package com.liziyang.dall.com.PermissionUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.liziyang.dall.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;


public class PermissionUtils {
    public static final String TAG = PermissionUtils.class.getSimpleName();
    //定义所需的数据为int数字
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_MULTI_PERMISSION = 100;
    //定义所需数据为权限别名
    public static final String PERMISSION_RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = android.Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = android.Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_COARSE_LOCATION,PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,};


    //接口
    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }


    public static void requestPermission(final Activity activity,final int requestCode,PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            return;
        }
        final String requestPermission = requestPermissions[ requestCode ];
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission( activity,requestPermission );

        } catch (RuntimeException e) {
            Toast.makeText( activity,"请打开权限",Toast.LENGTH_LONG ).show();
            return;
        }
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i( TAG,"ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED" );
            if (ActivityCompat.shouldShowRequestPermissionRationale( activity,requestPermission )) {
                Log.i( TAG,"requestPermission shouldShowRequestPermissionRationale" );
                shouldShowRationale( activity,requestCode,requestPermission );


            } else {
                Log.d( TAG,"requestCameraPermission else" );
                ActivityCompat.requestPermissions( activity,new String[]{requestPermission},requestCode );


            }

        } else {
            Log.d( TAG,"ActivityCompat.checkSelfPermission ==== PackageManager.PERMISSION_GRANTED" );
            Toast.makeText( activity,"opened:" + requestPermissions[ requestCode ],Toast.LENGTH_SHORT ).show();
            permissionGrant.onPermissionGranted( requestCode );


        }

    }

    private static void requestMultResult(Activity activity,String[] permissions,int[] grantResults,PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        Log.d( TAG,"onRequestPermissionsResult permissions length:" + permissions.length );
        Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Log.d( TAG,"permissions: [i]:" + i + ", permissions[i]" + permissions[ i ] + ",grantResults[i]:" + grantResults[ i ] );
            perms.put( permissions[ i ],grantResults[ i ] );

            if (grantResults[ i ] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add( permissions[ i ] );
            }
        }
        if (notGranted.size() == 0) {
            Toast.makeText( activity,"权限全部开启 请放心使用各项功能" + notGranted,Toast.LENGTH_LONG ).show();
            permissionGrant.onPermissionGranted( CODE_MULTI_PERMISSION );
        } else {
            openSettingActivity( activity,"those permission need granted!" );


        }


    }


    //一次申请多个权限
    public static void requestMultiPermissions(final Activity activity,PermissionGrant grant) {
        final List<String> permissionsList = getNoGrantedPermission( activity,false );
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission( activity,true );

        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Log.d( TAG,"requestMultiPermissions permissionsList:" +
                permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size() );


        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions( activity,permissionsList.toArray( new String[ permissionsList.size() ] ),CODE_MULTI_PERMISSION );
            Log.d( TAG,"showMessageOKCancel requestPermissions" );


        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel( activity,"应该打开那些权限",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,int which) {
                    ActivityCompat.requestPermissions( activity,shouldRationalePermissionsList.toArray
                            ( new String[ shouldRationalePermissionsList.size() ] ),CODE_MULTI_PERMISSION );

                    Log.d( TAG,"showMessageOKCancel requestPermissions" );

                }
            } );


        } else {
            grant.onPermissionGranted( CODE_MULTI_PERMISSION );
        }
    }





    private static void shouldShowRationale(final Activity activity,final int requestCode,final String requestPermission) {
        String[] permissionHint = activity.getResources().getStringArray( R.array.permissions );
        showMessageOKCancel( activity,"Rationale: " + permissionHint[ requestCode ],new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog,int which) {
                ActivityCompat.requestPermissions( activity,new String[]{requestPermission},requestCode );
                Log.d( TAG,"showMessageOKCancel requestPermissions:" + requestPermission );
            }


        } );
    }

    private static void showMessageOKCancel(final Activity context,String message,DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder( context )
                .setMessage( message )
                .setPositiveButton( "确定",okListener )
                .setNegativeButton( "取消",null )
                .create().show();
    }

    public static void requestPermissionsResult(final Activity activity,
                                                final int requestCode,
                                                String[] permissions,
                                                int[] grantResults,
                                                PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultResult( activity,permissions,grantResults,permissionGrant );
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Toast.makeText( activity,"illegal requestCode:" + requestCode,Toast.LENGTH_SHORT ).show();
            return;

        }
        Log.i( TAG,"onRequestPermissionsResult requestCode:" +
                requestCode + ",permissions:" + permissions.toString() +
                ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length );


        if (grantResults.length == 1 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {
            Log.i( TAG,"onRequestPermissionsResult PERMISSION_GRANTED" );
            permissionGrant.onPermissionGranted( requestCode );

        } else {
            Log.i( TAG,"onRequestPermissionsResult PERMISSION NOT GRANTED" );
            String[] permissionsHint = activity.getResources().getStringArray( R.array.permissions );
            openSettingActivity( activity,"Result" + permissionsHint[ requestCode ] );

        }

    }

    private static void openSettingActivity(final Activity activity,String message) {

        showMessageOKCancel( activity,message,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent();
                intent.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
                Uri uri = Uri.fromParts( "package",activity.getPackageName(),null );
                intent.setData( uri );
                activity.startActivity( intent );


            }
        } );
    }

    public static ArrayList<String> getNoGrantedPermission(Activity activity,boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[ i ];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission( activity,requestPermission );
            } catch (RuntimeException e) {
                Toast.makeText( activity,"请打开这些权限",Toast.LENGTH_LONG ).show();
                return null;
            }
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i( TAG,"getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission );
                if (ActivityCompat.shouldShowRequestPermissionRationale( activity,requestPermission )) {
                    Log.d( TAG,"shouldShowRequestPermissionRationale if" );
                    if (isShouldRationale) {
                        permissions.add( requestPermission );
                    } else {
                        if (!isShouldRationale) {
                            permissions.add( requestPermission );

                        }
                        Log.d( TAG,"shouldShowRequestPermissionRationale else" );
                    }

                }

            }

        }
        return permissions;
    }
}






