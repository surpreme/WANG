package com.com.myCamera;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.liziyang.dall.R;

import java.io.ByteArrayOutputStream;


public class CutPhotoActivity extends AppCompatActivity implements OnTouchListener{
    private ImageView imageview;
    private Bitmap bitmap;
    private ClipView clipview;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private int tag=-1;
    private ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cut_photo_layout);
        setTitle(null);
        String imgPath=getIntent().getStringExtra(PageAction.WOULD_CUT_PHOTO_PATH_KEY);
        if(null==imgPath || "".equals(imgPath)){
            Toast.makeText(CutPhotoActivity.this, "The picture's path is wrong", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bitmap=DecodeImageUtils.decodeImageByPath(imgPath, CutPhotoActivity.this);
        //bitmap=BitmapFactory.decodeFile(imgPath);
        tag=getIntent().getIntExtra("tag", -1);

        imageview=(ImageView) findViewById(R.id.imageView);

        imageview.setOnTouchListener(this);

        initClipView(imageview.getTop(), bitmap);
        bar=getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cut_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                byte [] bitMapArray=new byte[]{};
                bitMapArray=bitmapTobyteArray();
                Intent intent=new Intent();
                if(tag==1){
                    intent.setAction(PageAction.TAKE_PHOTO_ACTION);
                }else{
                    intent.setAction(PageAction.REQUEST_PAGE_ACTION);
                }
                intent.putExtra(PageAction.GET_CUT_PHOTO_KEY, bitMapArray);
                setResult(PageAction.RESULT_OK, intent);
                finish();
                break;

            case android.R.id.home:
                if(bitmap!=null && !bitmap.isRecycled()){
                    bitmap.recycle();
                    System.gc();
                }
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initClipView(int top,final Bitmap bitmap) {
        clipview = new ClipView(CutPhotoActivity.this);
        clipview.setCustomTopBarHeight(top);
        clipview.addOnDrawCompleteListener(new ClipView.OnDrawListenerComplete () {
            public void onDrawCompelete() {
                clipview.removeOnDrawCompleteListener();
                int clipHeight = clipview.getClipHeight();
                int clipWidth = clipview.getClipWidth();
                int midX = clipview.getClipLeftMargin() + (clipWidth / 2);
                int midY = clipview.getClipTopMargin() + (clipHeight / 2);

                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                // According to the cutting box for scaling
                float scale = (clipWidth * 1.0f) / imageWidth;
                if (imageWidth > imageHeight) {
                    scale = (clipHeight * 1.0f) / imageHeight;
                }

                // The starting center
                float imageMidX = imageWidth * scale / 2;
                float imageMidY = clipview.getCustomTopBarHeight()
                        + imageHeight * scale / 2;
                imageview.setScaleType(ScaleType.MATRIX);

                // Scale
                matrix.postScale(scale, scale);
                //Translate
                matrix.postTranslate(midX - imageMidX, midY - imageMidY);

                imageview.setImageMatrix(matrix);
                imageview.setImageBitmap(bitmap);
            }
        });
        this.addContentView(clipview, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                // Set the starting point position
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        return true;
    }
    /**
     *Multi-touch, calculate the distance down the two fingers
     *
     * @param event
     * @return
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     *Multi-touch, calculate the first to put down the center coordinates of two fingers
     *
     * @param point
     * @param event
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
    /**
     * For cutting box screenshots
     *
     * @return
     */
    private Bitmap getBitmap() {
        // get statusBarHeight
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contenttop = this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contenttop - statusBarHeight;

        // get screenshot
        View view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap finalBitmap = Bitmap.createBitmap(view.getDrawingCache(),
                clipview.getClipLeftMargin(), clipview.getClipTopMargin()
                        +titleBarHeight+statusBarHeight*2, clipview.getClipWidth(),
                clipview.getClipHeight());
        // destroyDrawingCache
        view.destroyDrawingCache();
        return finalBitmap;
    }
    private byte [] bitmapTobyteArray(){
        Bitmap clipBitmap = getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        clipBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        if(clipBitmap!=null && !clipBitmap.isRecycled()){
            clipBitmap.recycle();
            System.gc();
        }
        return bitmapByte;
    }
}
