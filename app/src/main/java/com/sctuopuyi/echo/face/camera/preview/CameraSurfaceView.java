package com.sctuopuyi.echo.face.camera.preview;



import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sctuopuyi.echo.face.camera.CameraInterface;


public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "yanzi";
	CameraInterface mCameraInterface;
	Context mContext;
	SurfaceHolder mSurfaceHolder;
	private Camera mCamera;

/*	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		CameraCodeInterface.getInstance().doStartPreview(mSurfaceHolder, 1.333f);
	}*/

	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent��͸�� transparent͸��
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder.addCallback(this);


	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
			Log.i(TAG, "surfaceCreated...");
		/**
		 * 默认调用的ID为前置相机
		 */
//		CameraCodeInterface.getInstance().doOpenCamera(null, CameraInfo.CAMERA_FACING_BACK);
		mCamera = CameraInterface.getInstance().doOpenCamera(mContext,null, CameraInfo.CAMERA_FACING_FRONT);
		//这里
		CameraInterface.getInstance().doStartPreview(mSurfaceHolder, 1.333f);

}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
		//摄像头画面显示在Surface上

		Log.i(TAG, "surfaceChanged...");
		//这里不知咋的会执行两遍??
		/**
		 * 这个方法是当surface的尺寸和大小出现变化的时候会调用
		 */

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "surfaceDestroyed...");
		CameraInterface.getInstance().doStopCamera();
	}
	public SurfaceHolder getSurfaceHolder(){
		return mSurfaceHolder;
	}



}
