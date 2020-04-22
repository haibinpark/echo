package com.sctuopuyi.echo.face;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sctuopuyi.echo.face.camera.CameraInterface;
import com.sctuopuyi.echo.utils.CameraUtil;
import com.sctuopuyi.echo.utils.ToastUtil;


/**
 * Created on 05/04/2017 11:32.
 */

public class FaceSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public FaceSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!CameraUtil.isCameraCanUse()) {
            ToastUtil.shortShow("请设置摄像头权限!");
            return;
        }

        int mNumberOfCameras = Camera.getNumberOfCameras();


        if (mNumberOfCameras < 1) {
            ToastUtil.shortShow("摄像头不可用,请检查摄像头是否可用?");
        }


        /**
         * 默认调用的ID为前置相机
         */
        mCamera = CameraInterface.getInstance().doOpenCamera(mContext, null, Camera.CameraInfo.CAMERA_FACING_FRONT);

        if (null == mCamera) {
            ToastUtil.shortShow("获取前置摄像头失败");
            return;
        }

        //启动预览
        CameraInterface.getInstance().doStartPreview(mSurfaceHolder, 1.333f);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    //region declare variable
    private static final String TAG = "yanzi";
    CameraInterface mCameraInterface;
    Context mContext;
    SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    //endregion
}
