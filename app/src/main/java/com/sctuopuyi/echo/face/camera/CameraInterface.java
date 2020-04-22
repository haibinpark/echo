package com.sctuopuyi.echo.face.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


import com.sctuopuyi.echo.utils.CameraUtil;
import com.sctuopuyi.echo.utils.ImageUtil;
import com.sctuopuyi.echo.utils.MImageUtil;
import com.sctuopuyi.echo.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class CameraInterface {
    private static final String TAG = "YanZi";
    public Camera mCamera;
    private Camera.Parameters mParams;

    public boolean isPreviewing = false;
    private float mPreviwRate = -1f;
    private int mCameraId = -1;//默认-1
    private boolean isGoolgeFaceDetectOn = false;
    private static CameraInterface mCameraInterface;
    private Context mContext;
    private Bitmap rotaBitmap;
    private String path;
    public Size previewSize;
    private double minDiff;
    private ImageButton shutterBtn;
    private TextView tv_camera_countdown;
    private String accountId;
    private String orderId;
    private String bespeakId;
    private String orgMemberId;
    private String memberId;
    private String pushOrderId;
    private String pushBespeakId;
    private int faceType;
    private TextView timecount;
    private String num;
    private TextView tv_toastbyis_count;

    /**
     * 停止相机预览
     */
    public void stopPreview() {
        mCamera.stopPreview();
        isPreviewing = false;
    }


    /**
     * 启动相机预览
     */
    public void startPreview() {
        mCamera.startPreview();
        isPreviewing = true;
    }


    public interface CamOpenOverCallback {
        void cameraHasOpened();
    }

    private CameraInterface() {

    }

    public static synchronized CameraInterface getInstance() {
        if (mCameraInterface == null) {
            mCameraInterface = new CameraInterface();
        }
        return mCameraInterface;
    }


    /**
     * 开始照相
     *
     * @param callback
     */
    public Camera doOpenCamera(Context mContext, CamOpenOverCallback callback, int cameraId) {
        Log.i(TAG, "Camera open....");
        /**
         * !!!Fail to connect to camera service
         * >>没有camera权限
         * >>看看CameraSurfaceView三个回调里是否有问题
         */
        if (CameraUtil.isCameraCanUse()) {
            this.mContext = mContext;
            mCamera = Camera.open(cameraId);
            mCameraId = cameraId;
            if (callback != null) {
                callback.cameraHasOpened();
            }
        } else {
            ToastUtil.shortShow("没有相机使用权限");
        }

        return mCamera;

    }

    /**
     * 开始预览;
     * 预览适配;
     *
     * @param holder
     * @param previewRate
     */
    public void doStartPreview(SurfaceHolder holder, float previewRate) {
        Log.i(TAG, "doStartPreview...");
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {

            /**
             * 解决拍照变形的原理:
             * 当预览图的尺寸比例和实际图片的比例相同的时候就不会出现变形的情况;
             * 所以我们在预览的时候根据获取的图片的最佳尺寸比例设置相同尺寸的预览尺寸比例就Ok;
             * 也就是说如果图片尺寸取的是4:3即1.33比例的,预览的比例也要取成4:3的;
             * 即使预览原分辨率是16:9的我们也设置成4:3,任意机型都可以适配了;
             *
             * 步骤(源码):
             * 1.取出最佳图片尺寸(4:3尺寸);
             * 2.通过预览surfaceview外的framlayout设置预览缩放为4:3即图片的比例尺寸;
             * 3.取到最佳预览尺寸;
             * (原理就是以4:3为标准,通过外面包裹的布局限制预览的尺寸;即使预览应该全屏时16:9也会
             * 屏幕上下留出空白变成4:3)
             */
            updateCameraParameters();
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * 注意:因为surfaceChanged执行了两次,所以这个isPreviewing变成了true;
             * 走到了这步mCamera.stopPreview();
             */
            isPreviewing = true;
            mPreviwRate = previewRate;
            Log.i(TAG, previewRate + "mPreviwRate");

            mParams = mCamera.getParameters();
            Log.i(TAG, "PreviewSize--With = " + mParams.getPreviewSize().width
                    + "Height = " + mParams.getPreviewSize().height);
            Log.i(TAG, "PictureSize--With = " + mParams.getPictureSize().width
                    + "Height = " + mParams.getPictureSize().height);

        }
    }


    /**
     * 更新图片和预览照相机参数
     */
    private void updateCameraParameters() {
//        if (mCamera != null) {
//            Camera.Parameters p = mCamera.getParameters();
//
//            long time = DateUtil.getCurrentStampLong();
//            p.setGpsTimestamp(time);
//            //获取sp里的尺寸信息
//            // Set picture size.
//            String pictureSize = SharedPreferencesUtil.getStringData(mContext,
//                    "KEY_PICTURE_SIZE", null);
//            //如果有SP的尺寸就设置SP里的尺寸,没有就初始化个尺寸
//            if (pictureSize == null) {
//                //初始化图片尺寸
//                initialCameraPictureSize(mContext, p);
//            } else {
//                //设置相机图片尺寸
//                List<Size> supported = p.getSupportedPictureSizes();
//                setCameraPictureSize(pictureSize, supported, p);
//            }
//
//            // Set the preview frame aspect ratio according to the picture size.
//            //获取设置后的图片尺寸
//            Size size = p.getPictureSize();
//            //把预览的父布局按照图片尺寸比例设置相同比例
//            CameraPreviewFrameLayout frameLayout =
//                    (CameraPreviewFrameLayout) ((CameraActivity) mContext).findViewById(R.id.frame_layout);
//            frameLayout.setAspectRatio((double) size.height / size.width);
//            LogUtil.i(size.width / size.height + "+++");
//            //预览尺寸和以前的写法得到的一样
//            previewSize = getOptimalPreviewSize(p, 1.33);//按照4:3比例
//            //设置适配预览尺寸
//            p.setPreviewSize(previewSize.width, previewSize.height);
//
//
//            //相机对焦方式,自动对焦
//            p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//
//
//            //设置到相机上
//            try {
//                mCamera.setParameters(p);
//            } catch (Exception e) {
//                List<String> focusModes = p.getSupportedFocusModes();
//                if (focusModes.size() > 0) {
//                    p.setFocusMode(focusModes.get(0));
//                    mCamera.setParameters(p);
//                    mCamera.setDisplayOrientation(90);
//                }
//            }
//
//            mCamera.setDisplayOrientation(90);
//
//        }
    }

    /**
     * 初始化相机图片尺寸
     *
     * @param context
     * @param parameters
     */
    public static void initialCameraPictureSize(Context context, Camera.Parameters parameters) {

        //获取默认图片尺寸
//        List<Size> supported = parameters.getSupportedPictureSizes();
//
//        if (supported == null) return;
//        //遍历本地所有图片尺寸,得到与系统默认尺寸相同的尺寸并存到sp
//        for (String candidate : context.getResources().getStringArray(
//                R.array.pref_camera_picturesize_entryvalues)) {
//
//            if (setCameraPictureSize(candidate, supported, parameters)) {
//                SharedPreferencesUtil.saveStringData(context, "KEY_PICTURE_SIZE", candidate);
//                return;
//            }
//
//        }
//
//        Log.e(TAG, "No supported picture size found");

    }


    /**
     * 设置相机图片尺寸
     *
     * @param candidate  传入的比较的尺寸
     * @param supported  系统提供的尺寸
     * @param parameters 相机参数
     * @return
     */
    public static boolean setCameraPictureSize(String candidate, List<Size> supported, Camera.Parameters parameters) {
        //传入一个尺寸,系统默认尺寸,相机参数
        //每个尺寸的"X"位置
        //得到相同的尺寸
        int index = candidate.indexOf('x');

        if (index == -1) return false;

        int width = Integer.parseInt(candidate.substring(0, index));

        int height = Integer.parseInt(candidate.substring(index + 1));

        for (Size size : supported) {

            if (size.width == width && size.height == height) {

                parameters.setPictureSize(width, height);

                return true;

            }

        }

        return false;

    }


    //获取最佳取景SIze,传入size集合和目的比例
    private Size getOptimalPreviewSize(Camera.Parameters parameters, double targetRatio) {
        //系统提供的默认尺寸
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        //系统提供尺寸的高宽比和我们提供的想要的高宽比值的差值标准
        final double ASPECT_TOLERANCE = 0.05;

        if (sizes == null) return null;
        Size optimalSize = null;

        double minDiff = Double.MAX_VALUE;

        // Because of bugs of overlay and layout, we sometimes will try to
        // layout the viewfinder in the portrait orientation and thus get the
        // wrong size of mSurfaceView. When we change the preview size, the
        // new overlay will be created before the old one closed, which causes
        // an exception. For now, just get the screen size

        //获取屏幕尺寸
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        //得到宽高中最小的作为高度
        int targetHeight = Math.min(display.getHeight(), display.getWidth());
        //如果高度小于0,就用现在的屏幕高度
        if (targetHeight <= 0) {
            //我们不知道SurefaceView的尺寸,使用屏幕高度
            // We don't know the size of SurefaceView, use screen height
            WindowManager windowManager = (WindowManager)
                    mContext.getSystemService(Context.WINDOW_SERVICE);
            targetHeight = windowManager.getDefaultDisplay().getHeight();
        }

        //试着发现一个合适的尺寸比例
        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            //遍历每个尺寸,得到宽高比例;
            double ratio = (double) size.width / size.height;
            //如果默认尺寸宽高比-我们想要宽高比的差值大于标准,继续找下一个合适的
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            //如果提供的尺寸的高度-屏幕的宽度小于标准值>>意思是找一个现在屏幕小的分辨率作为预览分辨率??
            if (Math.abs(size.height - targetHeight) < minDiff) {
                //就是我们要的分辨率尺寸
                optimalSize = size;
                //把差值赋给标准值
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        //如果上述没有找到合适比例尺寸,忽略宽高比的要求,继续找
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            Log.v(TAG, "No preview size match the aspect ratio");
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    /**
     * dMetrics可以获取屏幕分辨率等一系列信息
     *
     * @return
     */
    protected DisplayMetrics getScreenWH() {
        DisplayMetrics dMetrics = new DisplayMetrics();
        dMetrics = mContext.getResources().getDisplayMetrics();
        Log.i(TAG, mContext + "---");
        return dMetrics;
    }


    /**
     * 停止拍照
     */
    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mPreviwRate = -1f;
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 这里注意:
     * 如果点击照相调用doTakePicture这个方法,必须等待拍照上传结束之后才行;
     * 不然如果连续点击调用doTakePicture会导致takePicture failed异常,程序崩溃
     * 所以每次拍照以及后续操作时把这个按钮设置Enabled就行;
     */
    public void doTakePicture(Context mContext,
                              ShutterCallback mShutterCallback,
                              PictureCallback mJpegPictureCallback) {
        this.mContext = mContext;
        if (isPreviewing && (mCamera != null)) {
            mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
        }
    }

    /**
     * 获取照相机参数
     *
     * @return
     */
    public Camera.Parameters getCameraParams() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            return mParams;
        }
        return null;
    }

    /**
     * 获取照相机
     *
     * @return
     */
    public Camera getCameraDevice() {
        return mCamera;
    }


    public int getCameraId() {
        return mCameraId;
    }


    ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.i(TAG, "myShutterCallback:onShutter...");
        }
    };

    PictureCallback mJpegPictureCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            /**
             * 拍照
             */
            Bitmap b = null;
            if (null != data) {
                b = MImageUtil.byteToBitmap(data);
                mCamera.stopPreview();
                isPreviewing = false;
            }

            if (null != b) {
                /**
                 * 注意:
                 * 1.如果是后置相机拍照,需要旋转正90度;
                 * 2.如果是前置相机拍照,需要旋转负90度;
                 */
                if (mCameraId == 0) {
                    //getRotateBitmap注意,在这里就要进行压缩,不然会内存溢出
                    Bitmap bm = MImageUtil.ratio(b, 960f, 480f);
                    conpressImage(bm, 120);
                    //第二次压缩
                    rotaBitmap = ImageUtil.getRotateBitmap(bm, 90.0f);
                    path = ImageUtil.saveBitmap(rotaBitmap, mContext, "");
                } else {
                    //getRotateBitmap注意,在这里就要进行压缩,不然会内存溢出
                    Bitmap bm = MImageUtil.ratio(b, 960f, 480f);
                    conpressImage(bm, 120);
                    //第二次压缩
                    rotaBitmap = ImageUtil.getRotateBitmap(bm, -90.0f);
                    path = ImageUtil.saveBitmap(rotaBitmap, mContext, "");
                }

            }

        }
    };


    /**
     * 旋转bitmap前压缩
     *
     * @param image
     * @param maxSize
     */
    public void conpressImage(Bitmap image, int maxSize) {
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


}
