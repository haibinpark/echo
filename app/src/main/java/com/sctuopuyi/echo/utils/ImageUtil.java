package com.sctuopuyi.echo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.sctuopuyi.echo.face.camera.CameraInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeFile;

/**
 * 图片扩展类
 * Created on 22/03/2017 14:58.
 */

public class ImageUtil {

//    public static void setImageHasError(Activity activity, String res, ImageView imageView3, int errorRes) {
//        Glide
//                .with(activity)
//                .load(res)
//                .error(errorRes)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView3)
//        ;
//    }
//
//    public static void setImageWenjian(Context context, String res, ImageView imageView3) {
//        Glide
//                .with(context)
//                .load(res)
//                .error(R.drawable.img_wenjianpic)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView3)
//        ;
//    }

    public static void setImage(Activity activity, int res, ImageView imageView3, boolean b) {
        Glide
                .with(activity)
                .load(res)
                .into(imageView3)
        ;
    }


    /**
     * 加载本地图片
     *
     * @param url the file url
     * @return Bitmap
     */
    public static Bitmap getLoacalBitmap(String url) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis);
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 获取制定路径的图片数量
     *
     * @param path
     * @return
     */
    public static int getImageCountInPath(String path) {
        int i = 0;
        File file = new File(path);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase();
                getImageCountInPath(dirPath + "/");
            } else if (files[j].isFile() & name.endsWith(".jpg")) {
                i++;
            }
        }
        return i;
    }

    /**
     * 获取图片名称列表
     *
     * @param path
     * @return
     */
    public static List<String> getImageListName(String path) {
        List<String> imageList = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            if (files[j].isFile() & files[j].getName().endsWith(".jpg")) {
                imageList.add(files[j].getName());
            }
        }
        return imageList;
    }


    /**
     * Image compress factory class
     *  1:像素压缩;
     *  2.质量压缩;
     *  3.大小压缩;
     *  4.bitmap与文件与流的互相转换
     * @author
     *
     */

    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param outPath
     */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param imgPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        //这里是bitmap本身压缩还是压缩还是文件压缩
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 获取压缩后的文件的字节流
     *
     * @param imgPath
     * @param maxSize
     * @return
     */
    public static byte[] getCompressImageBytes(String imgPath, int maxSize) {
        return compressBitmap(getBitmap(imgPath), maxSize);
    }

    /**
     * 保存图片
     *
     * @param data
     */
    public static String saveImageByteBmp(byte[] data, Context context, String fileName) {
        String imagePath = "";
        Bitmap rotaBitmap;
        Bitmap b = null;
        if (null != data) {
            b = MImageUtil.byteToBitmap(data);
        }
        if (null != b) {
            /**
             * 注意:
             * 1.如果是后置相机拍照,需要旋转正90度;
             * 2.如果是前置相机拍照,需要旋转负90度;
             */
            if (CameraInterface.getInstance().getCameraId() == 0) {
                //getRotateBitmap注意,在这里就要进行压缩,不然会内存溢出
                Bitmap bm = MImageUtil.ratio(b, 960f, 480f);
                compressImage(bm, 120);
                //第二次压缩
                rotaBitmap = ImageUtil.getRotateBitmap(bm, 90.0f);
                imagePath = ImageUtil.saveBitmap(rotaBitmap, context, fileName);
            } else {
                //getRotateBitmap注意,在这里就要进行压缩,不然会内存溢出
                Bitmap bm = MImageUtil.ratio(b, 960f, 480f);
                compressImage(bm, 120);
                //第二次压缩
                rotaBitmap = ImageUtil.getRotateBitmap(bm, 0f);
                imagePath = ImageUtil.saveBitmap(rotaBitmap, context, fileName);
            }
        }
        return imagePath;
    }

    /**
     * 旋转bitmap前压缩
     *
     * @param image
     * @param maxSize
     */
    public static void compressImage(Bitmap image, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        //这里是bitmap本身压缩还是压缩还是文件压缩
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
    }


    /**
     * 压缩图片字节流
     *
     * @param bitmap
     * @param maxSize
     * @return
     */
    private static byte[] compressBitmap(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        if (bitmap == null) {
            ToastUtil.shortShow("图片不存在，请确认图片是否存在或检查是否开启存储权限");
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            // Compress by loop
            //这里是bitmap本身压缩还是压缩还是文件压缩
            while (os.toByteArray().length / 1024 > maxSize) {
                // Clean up os
                os.reset();
                // interval 10
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            }
        }
        return os.toByteArray();
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param image
     * @param outPath
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage(bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 压缩bitmap(从本地图片)
     *
     * @param imagePath 本地图片路径
     * @param scale     压缩比率 可以为空,那么是默认是200,越大压缩比越小
     * @return 一个缩放好的bitmap
     */
    public static Bitmap fileZoomBitmap(String imagePath, Float scale) {
        if (!new File(imagePath).exists()) {
            return null;
        }

        if (scale == null) {
            scale = 200f;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        int be = (int) (options.outHeight / scale);
        if (be <= 1) {
            be = 1;
        }
        options.inSampleSize = be;// be=2.表示压缩为原来的1/2,以此类推
        bm = decodeFile(imagePath, options);

        return bm;
    }

    /**
     * 压缩bitmap(从res图片)
     *
     * @param res
     * @param drawable
     * @param scale
     * @return
     */
    public static Bitmap resZoomBitmap(Resources res, int drawable, Float scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeResource(res, drawable, options);
        options.inJustDecodeBounds = false;
        //这里可以得到bm的狂傲尺寸信息而不需要把bm加载到内存中
        int be = (int) (options.outHeight / scale);
        if (be <= 1) {
            be = 1;
        }
        options.inSampleSize = be;// be=2.表示压缩为原来的1/2,以此类推
        bm = BitmapFactory.decodeResource(res, drawable, options);
        return bm;
    }

    /**
     * 压缩文件
     *
     * @param f
     * @param mBitmap
     */
    public static File bitmapZoomFile(File f, Bitmap mBitmap) {
        try {
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            //bitmap和流压缩到文件中,100为不压缩,50即压缩50%
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 旋转Bitmap(照相用)
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    /**
     * 删除文件夹下所有文件
     *
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        // 删除指定文件夹下所有文件
        // param path 文件夹完整绝对路径
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除指定文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        // 删除文件夹
        // param folderPath 文件夹完整绝对路径
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
        }
    }

    /**
     * 对扫脸获取的图片字节数组第一次压缩,防止OOM和卡顿
     *
     * @param imgByte
     * @return
     */
    public static Bitmap byteToBitmap(byte[] imgByte) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
        //根据参数128*128的值改变inSampleSize的大小,参数越大inSampleSize相对越大
        options.inSampleSize = ImageUtil.computeSampleSize(options, -1, 500 * 500);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);

        return bitmap;
    }

    /**
     * 根据安卓源码得来的获取最佳inSampleSize缩小比例的方法
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * @return
     */
    private static String initPath() {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**
     * 获取图片名字
     *
     * @return
     */
    public static String getPictureFileName() {
        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + dataTake + ".jpg";
        return jpegName;
    }

    /**
     * 保存图片文件
     *
     * @param context
     * @param bitmap
     * @param fileNamePath
     * @return
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String fileNamePath) {
        //删除原来的文件
        MImageUtil.deleteFile(new File(fileNamePath));
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(fileNamePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param b
     */
    public static String saveBitmap(Bitmap b, Context mContext, String jpegName) {
        try {
            /*fout = new FileOutputStream(jpegName);
            bos = new BufferedOutputStream(fout);
			//30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
			b.compress(Bitmap.CompressFormat.JPEG, qualty, bos);

			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap�ɹ�");

			while((new File(jpegName).length()/1024)>140){
				 qualty-=15;
				 fout = new FileOutputStream(jpegName);
				 bos = new BufferedOutputStream(fout);
				//30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
				b.compress(Bitmap.CompressFormat.JPEG, qualty, bos);

				bos.flush();
				bos.close();

			}*/
            long start = System.currentTimeMillis();
            //第一次先压缩一般质量并压缩到固定到大小!!
            Bitmap bm = MImageUtil.ratio(b, 960f, 480f);
            //第二次防止图片仍然过大,循环减小质量和大小
            MImageUtil.compressAndGenImage(bm, jpegName, 120);
            long end = System.currentTimeMillis();

            //保存成功后发个广播通知图库更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(jpegName));
            intent.setData(uri);
            mContext.sendBroadcast(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jpegName;

    }

    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth  需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList, Boolean mIsPortrait) {
        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (mIsPortrait) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }


    /**
     * 设置原型图片
     *
     * @param context
     * @param size
     * @param imageView
     * @param url
     */
    public static void setCircleImage(Context context, int size, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
//                .error(R.drawable.hd_default_avatar)
                .into(imageView);
    }

    /**
     * 设置原型图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void setCircleImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }


    private static final String TAG = "FileUtil";
    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "PlayCamera";
    private static String newJpegName;
    private static FileOutputStream fout;
    private static BufferedOutputStream bos;


    public static void setGiftImage(Context context, int gift_yz, ImageView imageView) {
//        Glide.with(context)
//                .load(gift_yz)
//                .centerCrop()
//                .transform(new GlideCircleTransform(context))
//                .error(R.drawable.hd_default_avatar)
//                .into(imageView);

        Glide
                .with(context)
                .load(gift_yz)
//                .error(R.mipmap.img01)
//                .placeholder(R.mipmap.gift_yz)
                .into(imageView);
    }

    /**
     * 根据图片路径获取图片的地址
     *
     * @param path
     * @return
     */
    public static String getImageNameByPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static void clear(Activity activity) {
        Glide.get(activity).clearMemory();
    }

    public static void setImage(Activity activity, String backPictureUrl, ImageView imageView3, boolean b) {
        Glide
                .with(activity)
                .load(backPictureUrl)
                .into(imageView3)
        ;
    }


    /**
     * 设置水印图片在左上角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                dp2px(context, paddingLeft), dp2px(context, paddingTop));
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 设置水印图片在右下角
     *
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     *
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                dp2px(context, paddingTop));
    }

    /**
     * 设置水印图片到左下角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, dp2px(context, paddingLeft),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 给图片添加文字到左上角
     *
     * @param context
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到右下角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到右上方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text,
                                            int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到左下方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到中间
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text,
                                          int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }


    public static Bitmap drawTextToCenter(Context context, String path, String text,
                                          int size, int color) {
        Bitmap bitmap = ImageUtil.getBitmap(path);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param w
     * @param h
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 拼凑oss文件地址
     *
     * @param fileName
     * @return
     */
//    public static String spellOssUrl(String fileName) {
//        return "https://" +
//                Constants.OSS.BUCKET_NAME +
//                "." +
//                Constants.OSS.ENDPOINT +
//                "/" +
//                fileName;
//
//    }

}
