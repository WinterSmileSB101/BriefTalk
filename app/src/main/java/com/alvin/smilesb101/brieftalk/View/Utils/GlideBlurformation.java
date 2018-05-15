package com.alvin.smilesb101.brieftalk.View.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;

public class GlideBlurformation extends BitmapTransformation {
    private Context context;

    public GlideBlurformation(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(context, toTransform, 20,outWidth,outHeight);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
