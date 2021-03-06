package com.sctuopuyi.echo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sctuopuyi.echo.R;


public class CommonDialogTwoBtn extends Dialog {

    public CommonDialogTwoBtn(Context context) {
        super(context);
    }

    public CommonDialogTwoBtn(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CommonDialogTwoBtn(Context context, int theme) {
        super(context, theme);
    }


    /**
     * 每次使用必须new一个builder，因为初始化界面在builder构造函数里
     */
    public static class Builder {
        private CommonDialogTwoBtn mDialog;
        private Context context;
        private View mView;//dialog视图
        private TextView mTextView_title;//标题
        private TextView mTextView_content;//内容
        private TextView mBottom;//按钮
        private TextView mBottom2;//按钮
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
            mView = mInflater.inflate(R.layout.dialog_common_two_btn, null);
            mTextView_title = mView.findViewById(R.id.tv_title);
            mTextView_content = mView.findViewById(R.id.tv_content);
            mBottom = mView.findViewById(R.id.tv_bottom_btn);
            mBottom2 = mView.findViewById(R.id.tv_bottom_btn2);
        }

        /**
         * 设置标题和内容
         */
        public Builder setTitleAndContent(String title, String content) {
            mTextView_title.setText(title);
            mTextView_content.setText(content);

            return this;
        }

        /**
         * 设置按钮
         */
        public Builder setButton(String text, View.OnClickListener listener) {
            mBottom.setText(text);
            mBottom.setOnClickListener(listener);
            return this;
        }

        /**
         * 设置按钮2
         */
        public Builder setButton2(String text, View.OnClickListener listener) {
            mBottom2.setText(text);
            mBottom2.setOnClickListener(listener);
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

        /**
         * 关闭dialog
         */
        public void closeDialog() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }

        public CommonDialogTwoBtn create() {
            mDialog = new CommonDialogTwoBtn(context, R.style.ComWinTransBackHalfDia);
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
}
