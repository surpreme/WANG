package com.com.myCamera;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ClipView extends View {
    private Paint paint = new Paint();
    private Paint borderPaint = new Paint();

    /** The custom at the top of the column height, such as is not the custom, the default is 0 */
    private int customTopBarHeight = 0;
    /** Cut out frame aspect ratio, the default 4:3 */
    private double clipRatio = 0.75;
    /** Cutting width of box */
    private int clipWidth = -1;
    /** Cutting box height */
    private int clipHeight = -1;
    /** Leave width cutting box on the left empty*/
    private int clipLeftMargin = 0;
    /** For cutting box top width */
    private int clipTopMargin = 0;
    /** Cutting box border width */
    private int clipBorderWidth = 1;
    private boolean isSetMargin = false;
    private OnDrawListenerComplete listenerComplete;

    public ClipView(Context context) {
        super(context);
    }

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();
        // If not cut display Settings box height and width, take the default values
        if (clipWidth == -1 || clipHeight == -1) {
            clipWidth = width - 50;
            clipHeight = (int) (clipWidth * clipRatio);
            // 横屏
            if (width > height) {
                clipHeight = height - 50;
                clipWidth = (int) (clipHeight / clipRatio);
            }
        }
        // If no display Settings cutting box on the left and reserved width, take the default values
        if (!isSetMargin) {
            clipLeftMargin = (width - clipWidth) / 2;
            clipTopMargin = (height - clipHeight) / 2;
        }
        // Draw a shadow
        paint.setAlpha(200);
        // top
        canvas.drawRect(0, customTopBarHeight, width, clipTopMargin, paint);
        // left
        canvas.drawRect(0, clipTopMargin, clipLeftMargin, clipTopMargin
                + clipHeight, paint);
        // right
        canvas.drawRect(clipLeftMargin + clipWidth, clipTopMargin, width,
                clipTopMargin + clipHeight, paint);
        // bottom
        canvas.drawRect(0, clipTopMargin + clipHeight, width, height, paint);

        // draw border
        borderPaint.setStyle(Style.STROKE);
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStrokeWidth(clipBorderWidth);
        canvas.drawRect(clipLeftMargin, clipTopMargin, clipLeftMargin
                + clipWidth, clipTopMargin + clipHeight, borderPaint);

        if (listenerComplete != null) {
            listenerComplete.onDrawCompelete();
        }
    }

    public int getCustomTopBarHeight() {
        return customTopBarHeight;
    }

    public void setCustomTopBarHeight(int customTopBarHeight) {
        this.customTopBarHeight = customTopBarHeight;
    }

    public double getClipRatio() {
        return clipRatio;
    }

    public void setClipRatio(double clipRatio) {
        this.clipRatio = clipRatio;
    }

    public int getClipWidth() {
        // Reduce clipBorderWidth reasons: capture remove the white border line
        return clipWidth - clipBorderWidth;
    }

    public void setClipWidth(int clipWidth) {
        this.clipWidth = clipWidth;
    }

    public int getClipHeight() {
        return clipHeight - clipBorderWidth;
    }

    public void setClipHeight(int clipHeight) {
        this.clipHeight = clipHeight;
    }

    public int getClipLeftMargin() {
        return clipLeftMargin + clipBorderWidth;
    }

    public void setClipLeftMargin(int clipLeftMargin) {
        this.clipLeftMargin = clipLeftMargin;
        isSetMargin = true;
    }

    public int getClipTopMargin() {
        return clipTopMargin + clipBorderWidth;
    }

    public void setClipTopMargin(int clipTopMargin) {
        this.clipTopMargin = clipTopMargin;
        isSetMargin = true;
    }

    public void addOnDrawCompleteListener(OnDrawListenerComplete listener) {
        this.listenerComplete = listener;
    }

    public void removeOnDrawCompleteListener() {
        this.listenerComplete = null;
    }

    /**
     * Cut the area when the call interface drawing
     *
     * @author dongxiangming
     *
     */
    public interface OnDrawListenerComplete {
        public void onDrawCompelete();
    }

}