package com.com.myCamera;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.liziyang.dall.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class TakePhotoActivity extends Activity implements OnTouchListener{
    private Button take_photo_button,take_photo_cancle,take_photo_ok;
    private SurfaceHolder mHolder;
    private String path;
    private static Camera mCamera;
    public File photoFile;
    public Handler mHandler = new Handler();
    private CameraPreview mPreview;
    private FrameLayout preview;
    int displayRotation=0;
    int picRotate=0;
    private DrawCaptureRect mDraw;
    private int width=0;
    private int height=0;
    private boolean isfocusing=false;
    private boolean isfocuseed=false;
    private int x=0;
    private int y=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo_layout);
        setTitle(null);
        new OrientationListener(TakePhotoActivity.this).enable();

        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.setOnTouchListener(this);
        preview.addView(mPreview);

        DisplayMetrics dm =getResources().getDisplayMetrics();
        width=dm.widthPixels;
        height=dm.heightPixels;

        take_photo_button=(Button) findViewById(R.id.take_photo_button);
        take_photo_button.setOnClickListener(photoListener);
        take_photo_cancle=(Button) findViewById(R.id.take_photo_cancle);
        take_photo_cancle.setOnClickListener(photoListener);
        take_photo_ok=(Button) findViewById(R.id.take_photo_ok);
        take_photo_ok.setOnClickListener(photoListener);
    }
    /**
     * click event
     * @author dongxiangming
     */
    OnClickListener photoListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.take_photo_cancle:
                    take_photo_cancle.setVisibility(View.INVISIBLE);
                    take_photo_ok.setVisibility(View.INVISIBLE);
                    mCamera.stopPreview();
                    mCamera.startPreview();
                    preview.removeView(mDraw);
                    mDraw= new DrawCaptureRect(TakePhotoActivity.this, width/2-100,height/2-100,200,200,getResources().getColor(R.color.red));
                    break;
                case R.id.take_photo_button:
                    if(isfocuseed){
                        mCamera.takePicture(shutterCallback, null, mPicture);
                        preview.removeView(mDraw);
                        take_photo_cancle.setVisibility(View.VISIBLE);
                        take_photo_ok.setVisibility(View.VISIBLE);
                        isfocusing=false;
                        isfocuseed=false;
                    }else if(isfocusing){
                        Toast.makeText(TakePhotoActivity.this, "Focusing,Please wait", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TakePhotoActivity.this, "Please touch screen to focusing!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.take_photo_ok:
                    take_photo_cancle.setVisibility(View.INVISIBLE);
                    take_photo_ok.setVisibility(View.INVISIBLE);
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(Uri.parse(path),filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Intent intent=new Intent(PageAction.CUT_PHOTO_ACTION);
                    intent.putExtra("tag", 1);
                    intent.putExtra(PageAction.WOULD_CUT_PHOTO_PATH_KEY, picturePath);
                    startActivityForResult(intent, PageAction.REQUEST_CUT_PHOTO_CODE);
                    break;
            }

        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PageAction.REQUEST_CUT_PHOTO_CODE && resultCode==PageAction.RESULT_OK && null != data){
            Intent intent=new Intent();
            intent.setAction(PageAction.REQUEST_PAGE_ACTION);
            byte [] arrays=new byte [] {};
            arrays=data.getByteArrayExtra(PageAction.GET_CUT_PHOTO_KEY);
            intent.putExtra(PageAction.GET_CUT_PHOTO_KEY, arrays);
            setResult(PageAction.RESULT_OK, intent);
            finish();
        }
    };

    private Rect calculateTapArea(float x, float y, float coefficient) {
        float focusAreaSize = 200;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) ((x / width) * 2000 - 1000);
        int centerY = (int) ((y / height) * 2000 - 1000);
        int left = clamp(centerX - (areaSize / 2), -1000, 1000);
        int top = clamp(centerY - (areaSize / 2), -1000, 1000);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top),
                Math.round(rectF.right), Math.round(rectF.bottom));
    }
    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }
    //shutterCallback
    ShutterCallback shutterCallback=new ShutterCallback() {
        @Override
        public void onShutter() {
            mCamera.enableShutterSound(true);
        }
    };

    public  void getCameraInstance(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mCamera = Camera.open(0);
            } else
                mCamera = Camera.open();
        }catch (Exception e){
        }
    }
    //set preview parameters
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        public CameraPreview(Context context, Camera camera) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("TakePhoto", "Error setting camera preview: " + e.getMessage());
            }
        }
        public void surfaceDestroyed(SurfaceHolder holder) {
            resetCamera();
        }
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mHolder.getSurface() == null){
                return;
            }
            try {
                mCamera.stopPreview();
                mCamera.setDisplayOrientation(getCameraDisplayOrientation(0));
                Camera.Parameters params=mCamera.getParameters();
                Size PreviewSize=getBestSupportedSize(params.getSupportedPreviewSizes());
                Size PictureSize=getBestSupportedSize(params.getSupportedPictureSizes());
                params.setPictureFormat(ImageFormat.JPEG);
                params.setPreviewSize(PreviewSize.width,PreviewSize.height);
                params.setPictureSize(PictureSize.width,PictureSize.height);
                params.setJpegQuality(100);
                mCamera.setParameters(params);
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e){
                resetCamera();
                Log.d("takePhoto", "Error starting camera preview: " + e.getMessage());
            }
        }
    }
    //get picture data
    private PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Matrix matrix = new Matrix();
            matrix.setRotate(picRotate);
            Bitmap bitmap=DecodeImageUtils.decodeImage(data, TakePhotoActivity.this,matrix);
/*	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_kk-mm-ss");// 转换格式
			String picName = "IMG_"+sdf.format(new Date()) + ".jpg";
			path=getPicPath()+picName;
	        File pictureFile = new File(getPicPath());
	        if(!pictureFile.exists()){
	        	pictureFile.mkdirs();
	       }
	        pictureFile=new File(path);*/
            //try {
	        	/*BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pictureFile));
	        	bitmap.compress(CompressFormat.JPEG, 100, bos);
	        	bos.flush();
	        	bos.close();*/
	        	/*if(bitmap!=null && !bitmap.isRecycled()){
	        		bitmap.recycle();
	        		System.gc();
	        		System.out.println("image gc");
	        	}*/
            path=MediaStore.Images.Media.insertImage(TakePhotoActivity.this.getContentResolver(), bitmap, null, null);
            TakePhotoActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
            bitmap.recycle();
            // } /*catch (FileNotFoundException e) {
            // Log.d("TakePhoto", "File not found: " + e.getMessage());
            // } catch (IOException e) {
            // Log.d("TakePhoto", "Error accessing file: " + e.getMessage());
            // }*/
        }
    };

    private class OrientationListener extends OrientationEventListener{
        public OrientationListener(Context context) {
            super(context);
        }
        public OrientationListener(Context context, int rate) {
            super(context, rate);
        }
        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == ORIENTATION_UNKNOWN) return;
            CameraInfo info =
                    new CameraInfo();
            Camera.getCameraInfo(0, info);
            orientation = (orientation + 45) / 90 * 90;
            int rotation = 0;
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                rotation = (info.orientation - orientation + 360) % 360;
            } else {  // back-facing camera
                rotation = (info.orientation + orientation) % 360;
            }
            picRotate=rotation;
        }
    }
    private   int  getCameraDisplayOrientation(int cameraId) {
        CameraInfo  info= new CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result;
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }
    private Size getBestSupportedSize(List<Size> sizes) {
        Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }
    private void resetCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
    public static String getPicPath() {
        File defaultDir = Environment.getExternalStorageDirectory();
        String path = defaultDir.getAbsolutePath() + File.separator;
        return path;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            mCamera.release();
            preview.removeView(mDraw);
            preview.removeView(mPreview);
            mPreview.surfaceDestroyed(mHolder);
            finish();
        }
        return true;
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        take_photo_cancle.setVisibility(View.INVISIBLE);
        take_photo_ok.setVisibility(View.INVISIBLE);
        if(mCamera==null){
            getCameraInstance();
        }
        mCamera.stopPreview();
        mCamera.startPreview();
        mDraw= new DrawCaptureRect(TakePhotoActivity.this, width/2-100,height/2-100,200,200,getResources().getColor(R.color.red));
        preview.addView(mDraw);
    }
    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            isfocusing=true;
            x=(int)event.getX()-100;
            y=(int)event.getY()-100;
            preview.removeView(mDraw);
            mDraw= new DrawCaptureRect(TakePhotoActivity.this, x,y,200,200,getResources().getColor(R.color.red));
            preview.addView(mDraw);
            Rect focusRect = calculateTapArea(event.getRawX(), event.getRawY(), 1f);
            Rect meteringRect = calculateTapArea(event.getRawX(), event.getRawY(), 1.5f);
            Camera.Parameters parameters = mCamera.getParameters();
            List<String> focusModes=parameters.getSupportedFocusModes();
            System.out.println("支持的变焦模式"+focusModes);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            if (parameters.getMaxNumFocusAreas() > 0) {
                List<Area> focusAreas = new ArrayList<Area>();
                focusAreas.add(new Area(focusRect, 600));
                parameters.setFocusAreas(focusAreas);
            }
            if (parameters.getMaxNumMeteringAreas() > 0) {
                List<Area> meteringAreas = new ArrayList<Area>();
                meteringAreas.add(new Area(meteringRect, 1000));
                parameters.setMeteringAreas(meteringAreas);
            }
            mCamera.cancelAutoFocus();
            mCamera.setParameters(parameters);
            mCamera.autoFocus(new AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if(success){
                        preview.removeView(mDraw);
                        mDraw= new DrawCaptureRect(TakePhotoActivity.this, x,y,200,200,getResources().getColor(R.color.green));
                        preview.addView(mDraw);
                        isfocuseed=true;
                        isfocusing=false;
                    }else{
                        preview.removeView(mDraw);
                        mDraw= new DrawCaptureRect(TakePhotoActivity.this, x,y,200,200,getResources().getColor(R.color.red));
                        preview.addView(mDraw);
                        isfocuseed=false;
                        isfocusing=true;
                    }
                }
            });
        }
        return true;
    }
}
