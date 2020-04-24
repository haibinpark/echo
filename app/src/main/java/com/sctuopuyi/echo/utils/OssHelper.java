//package com.sctuopuyi.echo.utils;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Looper;
//import android.util.Log;
//import com.sctuopuyi.echo.app.Constants;
//import com.sctuopuyi.echo.data.DatamanagerHelper;
//
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * Created by fengmlo on 2017/11/27.
// */
//
//public class OssHelper {
//
//    private static OssHelper ossHelper;
//    private OSS oss;
//    private static String bucketName = null;
//
//    public static OssHelper init(Context context, DatamanagerHelper helper) {
//        if (ossHelper == null) {
//            synchronized (OssHelper.class) {
//                if (ossHelper == null) {
//                    ossHelper = new OssHelper(context, helper);
//                }
//            }
//        }
//        return ossHelper;
//    }
//
//    private OssHelper(Context context, DatamanagerHelper helper) {
//        oss = initOSS(context, helper);
//    }
//
//    private OSS initOSS(Context context, String accessKeyId, String accessKeySecret, String securityToken) {
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken);
//        return initOSSSecond(context, credentialProvider);
//    }
//
//    private OSS initOSS(Context context, final DatamanagerHelper datamanagerHelper) {
//        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
//        // 也可查看sample 中 sts 使用方式了解更多(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
//        OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {
//            private StsModelResponse mStsModelResponse;
//
//            @SuppressLint("CheckResult")
//            @Override
//            public OSSFederationToken getFederationToken() {
//                datamanagerHelper.getSts()
//                        .compose(RxUtil.handleMyResult())
//                        .subscribe(new Observer<StsModelResponse>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//
//                            @Override
//                            public void onNext(StsModelResponse stsModelResponse) {
//                                mStsModelResponse = stsModelResponse;
//                                LogUtil.i(mStsModelResponse.credentials.accessKeyId);
//                                LogUtil.i(mStsModelResponse.credentials.accessKeySecret);
//                                LogUtil.i(mStsModelResponse.credentials.securityToken);
//                                LogUtil.i(mStsModelResponse.credentials.expiration);
//                                LogUtil.i(mStsModelResponse.bucketName);
//                                bucketName = stsModelResponse.bucketName;
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                new android.os.Handler(Looper.getMainLooper()).post(() -> ToastUtil.shortShow(e.getMessage()));
//                            }
//                        });
//
//                if (mStsModelResponse != null) {
//                    return new OSSFederationToken(mStsModelResponse.credentials.accessKeyId,
//                            mStsModelResponse.credentials.accessKeySecret,
//                            mStsModelResponse.credentials.securityToken,
//                            mStsModelResponse.credentials.expiration);
//                } else {
//                    return null;
//                }
//            }
//        };
//
//        //该配置类如果不设置，会有默认配置，具体可看该类
//        return initOSSSecond(context, credentialProvider);
//    }
//
//    @NonNull
//    private OSS initOSSSecond(Context context, OSSCredentialProvider credentialProvider) {
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog();  //调用此方法即可开启日志
//        return new OSSClient(context.getApplicationContext(), "https://" + Constants.OSS.ENDPOINT, credentialProvider);
//    }
//
//    public OSS getOss() {
//        return oss;
//    }
//
//    private static OSS getOSS() {
//        if (ossHelper == null)
//            throw new RuntimeException("OssHelper has not initialised!.");
//        return ossHelper.getOss();
//    }
//
//    public static boolean upLoadFile(File file, String fileName) {
//        // 构造上传请求
//        if (bucketName == null){
//            bucketName = Constants.OSS.BUCKET_NAME;
//        }
//        PutObjectRequest put = new PutObjectRequest(bucketName, "zhxy_picture/" + fileName, file.getAbsolutePath());
//        if (doUpFile(put)) return true;
//        return false;
//    }
//
//    public static boolean upVideoLoadFile(File file, String fileName) {
//        // 构造上传请求
//        if (bucketName == null){
//            bucketName = Constants.OSS.BUCKET_NAME;
//        }
//        PutObjectRequest put = new PutObjectRequest(bucketName, "zhxy_video/" + fileName, file.getAbsolutePath());
//        if (doUpFile(put)) return true;
//        return false;
//    }
//
//    public static boolean upLoadFile(byte[] uploadData, String fileName) {
//        // 构造上传请求
//        if (bucketName == null){
//            bucketName = Constants.OSS.BUCKET_NAME;
//        }
//        PutObjectRequest put = new PutObjectRequest(bucketName, fileName, uploadData);
//        if (doUpFile(put)) return true;
//        return false;
//    }
//
//    private static boolean doUpFile(PutObjectRequest put) {
//        // 文件元信息的设置是可选的
//        // ObjectMetadata metadata = new ObjectMetadata();
//        // metadata.setContentType("application/octet-stream"); // 设置content-type
//        // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5
//        // put.setMetadata(metadata);
//        try {
//            PutObjectResult putResult = getOSS().putObject(put);
//            Log.d("PutObject", "UploadSuccess");
//            Log.d("ETag", putResult.getETag());
//            Log.d("RequestId", putResult.getRequestId());
//            return true;
//        } catch (ClientException e) {
//            // 本地异常如网络异常等
//            e.printStackTrace();
//        } catch (ServiceException e) {
//            // 服务异常
//            Log.e("RequestId", e.getRequestId());
//            Log.e("ErrorCode", e.getErrorCode());
//            Log.e("HostId", e.getHostId());
//            Log.e("RawMessage", e.getRawMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    //region no used
//
//    public static void downloadFile(String bucketName, String fileName) {
//        //构造下载文件请求
//        GetObjectRequest get = new GetObjectRequest(bucketName, fileName);
//        //设置下载进度回调
//        get.setProgressListener((request, currentSize, totalSize) -> OSSLog.logDebug("getobj_progress: " + currentSize + "  total_size: " + totalSize, false));
//        FileOutputStream fileOutputStream = null;
//        try {
//            //  打开文件
//            File file = new File(fileName);
//            fileOutputStream = new FileOutputStream(file);
//
//            // 同步执行下载请求，返回结果
//            GetObjectResult getResult = getOSS().getObject(get);
//            Log.d("Content-Length", "" + getResult.getContentLength());
//            // 获取文件输入流
//            InputStream inputStream = getResult.getObjectContent();
//            byte[] buffer = new byte[2048];
//            int len;
//            while ((len = inputStream.read(buffer)) != -1) {
//                // 处理下载的数据，比如图片展示或者写入文件等
//                fileOutputStream.write(buffer, 0, len);
//            }
//            // 下载后可以查看文件元信息
//            ObjectMetadata metadata = getResult.getMetadata();
//            Log.d("ContentType", metadata.getContentType());
//        } catch (ClientException e) {
//            // 本地异常如网络异常等
//            e.printStackTrace();
//        } catch (ServiceException e) {
//            // 服务异常
//            Log.e("RequestId", e.getRequestId());
//            Log.e("ErrorCode", e.getErrorCode());
//            Log.e("HostId", e.getHostId());
//            Log.e("RawMessage", e.getRawMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fileOutputStream != null) {
//                    fileOutputStream.close();
//                }
//            } catch (IOException ignored) {
//            }
//        }
//
//    }
//
//    public static void asyncPutObjectFromLocalFile(File file, String bucketName, String fileName, final ProgressCallback callback) {
//        // Creates the upload request
//        PutObjectRequest put = new PutObjectRequest(bucketName, fileName, file.getAbsolutePath());
//        // Sets the progress callback and upload file asynchronously
//        put.setProgressCallback((request, currentSize, totalSize) -> callback.onProgress(currentSize, totalSize));
//
//        OSSAsyncTask task = getOSS().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                callback.onSuccess();
//                OSSLog.logDebug("PutObject", "UploadSuccess");
//                OSSLog.logDebug("ETag", result.getETag());
//                OSSLog.logDebug("RequestId", result.getRequestId());
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                callback.onFailure(clientExcepion != null ? clientExcepion : serviceException);
//                // request exception
//                if (clientExcepion != null) {
//                    // client side exception,  such as network exception
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // service side exception
//                    OSSLog.logError("ErrorCode", serviceException.getErrorCode());
//                    OSSLog.logError("RequestId", serviceException.getRequestId());
//                    OSSLog.logError("HostId", serviceException.getHostId());
//                    OSSLog.logError("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
//    }
//
//    public interface ProgressCallback {
//        void onProgress(long currentSize, long totalSize);
//
//        void onSuccess();
//
//        void onFailure(@Nullable Exception e);
//    }
//
//    //endregion
//}
