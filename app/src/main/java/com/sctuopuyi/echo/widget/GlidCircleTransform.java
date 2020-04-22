//package com.sctuopuyi.dsport.widget;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//
///**
// * 原型图片
// * 引用地址
// * http://www.jianshu.com/p/7cd815b5da42
// * Created on 26/10/2017 15:42.
// */
//
//public class GlidCircleTransform extends BitmapTransformation {
//    public GlidCircleTransform(Context context) {
//        super(context);
//    }
//
//    public GlidCircleTransform(Context context, int size) {
//        super(context);
//        this.size = size;
//    }
//
//    public GlidCircleTransform(BitmapPool bitmapPool) {
//        super(bitmapPool);
//    }
//
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return circleCrop(pool, toTransform);
//    }
//
//    @Override
//    public String getId() {
//        return getClass().getName();
//    }
//
//
//    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) return null;
//        size = Math.min(source.getWidth(), source.getHeight());
//        int x = (source.getWidth() - size) / 2;
//        int y = (source.getHeight() - size) / 2;
//
//        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//
//        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        float r = size / 2f;
//        canvas.drawCircle(r, r, r, paint);
//        return result;
//    }
//
//    private int size = 0;
//}
