package com.sctuopuyi.echo.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by fengmlo on 2017/12/6.
 */

public class ImagePickerUtil {

    public static final int REQUEST_PICK_IMAGE = 0x101;
    private ImagePicker imagePicker;
    private static volatile ImagePickerUtil instance;

    private ImagePickerUtil() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setMultiMode(false);
//        imagePicker.setSelectLimit(10);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
    }

    public static ImagePickerUtil getInstance() {
        if (instance == null) {
            synchronized (ImagePickerUtil.class) {
                if (instance == null) {
                    instance = new ImagePickerUtil();
                }
            }
        }
        return instance;
    }

    public ImagePickerUtil withCrop() {
        imagePicker.setCrop(true);
        imagePicker.setFocusHeight(SystemUtil.dp2px(300));
        imagePicker.setFocusWidth(SystemUtil.dp2px(300));
        imagePicker.setOutPutX(600);
        imagePicker.setOutPutY(600);
        return this;
    }

    public ImagePickerUtil withoutCrop() {
        imagePicker.setCrop(false);
        return this;
    }


    public ImagePickerUtil setMulti(boolean isMulti) {
        imagePicker.setMultiMode(isMulti);
        return this;
    }

    public ImagePickerUtil setLimit(int limit) {
        imagePicker.setSelectLimit(limit);
        return this;
    }

    public void pick(Activity activity) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        activity.startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    public static class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Activity activity, String s, ImageView imageView, int i, int i1) {
            Glide.with(activity).load(Uri.fromFile(new File(s))).into(imageView);
        }

        @Override
        public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
            Glide.with(activity).load(Uri.fromFile(new File(path))).into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }

    @Nullable
    public static ArrayList<ImageItem> getImages(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == ImagePickerUtil.REQUEST_PICK_IMAGE) {
                return (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            }
        }
        return null;
    }

}
