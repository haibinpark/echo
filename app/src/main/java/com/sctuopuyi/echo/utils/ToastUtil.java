package com.sctuopuyi.echo.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.sctuopuyi.echo.R;
import com.sctuopuyi.echo.app.App;


/**
 * Created by codeest on 2016/8/4.
 */
public class ToastUtil {

    static ToastUtil td;

    private static void show(int resId) {
        show(App.Companion.getInstance().getString(resId));
    }

    private static void show(String msg) {
        if (td == null) {
            td = new ToastUtil(App.Companion.getInstance());
        }
        td.setText(msg);
        if (td.toast != null)
            td.toast.cancel();
        td.create().show();
    }

    public static void shortShow(String msg) {
        if (td == null) {
            td = new ToastUtil(App.Companion.getInstance());
        }
        td.setText(msg);
        if (td.toast != null)
            td.toast.cancel();
        td.createShort().show();
    }


    Context context;
    Toast toast;
    String msg;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public Toast create() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }


    public Toast createShort() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
