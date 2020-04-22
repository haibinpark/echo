package com.sctuopuyi.echo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sctuopuyi.echo.R;
import com.sctuopuyi.echo.utils.PhoneUtils;
import com.sctuopuyi.echo.widget.MyVerifyCodeTimer;


public class CommonDialogEmptyPlus extends Dialog {

    public CommonDialogEmptyPlus(Context context) {
        super(context);
    }

    public CommonDialogEmptyPlus(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CommonDialogEmptyPlus(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 每次使用必须new一个builder，因为初始化界面在builder构造函数里
     */
    public static class Builder {
        private CommonDialogEmptyPlus mDialog;
        private Context context;
        private View mView;//dialog视图
        private LinearLayout mContainer;
        private boolean canBack = true;//是否可以返回键取消，默认可以
        private boolean canTouch = true;//是否可以点击外部取消，默认可以

        public Builder(Context context) {
            this.context = context;
            initView();
        }

        /**
         * 初始化视图
         */
        private void initView() {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = mInflater.inflate(R.layout.dialog_common_empty, null);
            mContainer = mView.findViewById(R.id.dialog_common_empty_content);
        }

        /**
         * 设置内容
         */
        public Builder setContainer(View view) {
            mContainer.addView(view);
            return this;
        }

        /**
         * 设置不可以返回键+out取消
         */
        public Builder noCancleAll() {
            canBack = false;
            canTouch = false;
            return this;
        }

        /**
         * 设置不可以点击外部取消，可以点击返回按钮取消
         */
        public Builder noCancleTouchButCanBackPress() {
            canBack = true;
            canTouch = false;
            return this;
        }

        public Dialog getmDailog() {
            return mDialog;
        }

        /**
         * 关闭dialog
         */
        public void closeDialog() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }

        public CommonDialogEmptyPlus create() {
            mDialog = new CommonDialogEmptyPlus(context, R.style.ComWinTransBackHalfDia);
            mDialog.setContentView(mView);
            //设置dialog的大小
            android.view.WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width = lp.WRAP_CONTENT;//dp
            lp.height = lp.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(lp);
            mDialog.setCancelable(canBack);
            mDialog.setCanceledOnTouchOutside(canTouch);
            return mDialog;
        }

        public boolean isShowing() {
            if (mDialog != null && mDialog.isShowing()) {
                return true;
            }
            return false;
        }
    }

    /**
     * 实名认证dialog
     */
    public static View getPersonAuthenView(Context c, String phone, String btnName, OnDialogBtnListener listener, OnDialogBtnListener btnListener) {
        View view = LayoutInflater.from(c).inflate(R.layout.dialog_content_person_authen, null);
        TextView tv_phone = view.findViewById(R.id.tv_phone);
        EditText et_code = view.findViewById(R.id.et_code);
        TextView tv_get_code = view.findViewById(R.id.tv_get_code);
        TextView tv_bottom_btn = view.findViewById(R.id.tv_bottom_btn);
        tv_bottom_btn.setText(btnName);

        tv_phone.setText(PhoneUtils.hideCenterNum(phone));
        tv_bottom_btn.setOnClickListener(v -> {
            if (btnListener != null) {
                btnListener.onClick(et_code.getText().toString());
            }
        });

        MyVerifyCodeTimer timer = new MyVerifyCodeTimer(tv_get_code);
        tv_get_code.setOnClickListener(v -> {
            if (listener != null) {
                tv_get_code.setEnabled(false);
                timer.start();
                listener.onClick(phone);
            }
        });

        return view;
    }

    /**
     * 增补货币不够dialog
     */
    public static View getAppendCoinTipView(Context c, String num, OnDialogBtnListener btnListener) {
        View view = LayoutInflater.from(c).inflate(R.layout.dialog_content_append_coin_tip, null);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_bottom_btn = view.findViewById(R.id.tv_bottom_btn);

        tv_content.setText("至少补仓" + num + ",请修改增补数量");
        tv_bottom_btn.setOnClickListener(v -> {
            if (btnListener != null) {
                btnListener.onClick(num);
            }
        });

        return view;
    }

    /**
     * 弹窗按钮点击事件
     */
    public interface OnDialogBtnListener {
        void onClick(String str);
    }

}
