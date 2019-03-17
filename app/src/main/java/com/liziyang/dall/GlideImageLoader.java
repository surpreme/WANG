package com.liziyang.dall;

import android.content.Context;
import android.graphics.NinePatch;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.youth.banner.loader.ImageLoader;

import java.lang.annotation.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context , Object path , ImageView imageView) {
        Glide.with ( context )
                .load ( path )
                .into ( imageView );


    }
}
