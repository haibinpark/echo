package com.sctuopuyi.echo.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import io.reactivex.Observable;

import java.io.*;


/**
 * Created on 06/11/2017 15:48.
 */

public class FileUtil {


    public static File getTempImage(String pictureFileName) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File tempFile = new File(Environment.getExternalStorageDirectory(), pictureFileName);
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return tempFile;
        }
        return null;
    }


    public static String SDCARD_STORAGE_PATH_LIVENESS = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "liveness" + File.separator;

    /**
     * 获取存取的绝对路径
     *
     * @return
     */
    public static String getAbsPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }


    public static File getSaveFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file;
    }


    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(
                contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String bitmapConvertString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Observable<String> bytesToBase64(final byte[] bytes) {
        return Observable.create(emitter -> {
            emitter.onNext(bitmapConvertString(bytes));
            emitter.onComplete();
        });
    }

    public static Observable<String> fileToBase64(final File file) {
        return Observable.create(emitter -> {
            emitter.onNext(bitmapConvertString(getBytes(file)));
            emitter.onComplete();
        });
    }

    public static byte[] getBytes(File file) {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ous != null) ous.close();
            } catch (Exception ignored) {
            }
            try {
                if (ios != null) ios.close();
            } catch (Exception ignored) {
            }
        }
        return ous.toByteArray();
    }


    /**
     * 获取图片名称
     *
     * @param pictureUrl
     * @return
     */
    public static String getImageFileName(String pictureUrl) {
        if (pictureUrl.length() == 0)
            return "";
        String fileName = pictureUrl.substring(pictureUrl.lastIndexOf("/") + 1);
        if (fileName.indexOf(".") > 0) {
            return fileName;
        } else {
            return fileName + ".jpg";
        }
    }

    public static void copyText(String coinAddress, Context mContext) {
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("平台数字货币地址", coinAddress);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
