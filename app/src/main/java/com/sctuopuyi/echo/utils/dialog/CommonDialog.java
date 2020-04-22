package com.sctuopuyi.echo.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sctuopuyi.echo.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


/**
 * 网络配置Dialog
 *
 * @author chain
 */
public class CommonDialog extends Dialog {


    //单选
    public static int TYPE_SINGLE_SELECT = 0;
    //多选
    public static int TYPE_MULTI_SELECT = 1;

    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 每次使用必须new一个builder，因为初始化界面在builder构造函数里
     */
    public static class Builder {
        private CommonDialog mDialog;
        private Context context;
        private View mView;//dialog视图
        private TextView mTitle;//标题
        private Button mButton;//按钮
        private LinearLayout mContainer;
        private RecyclerView rcv_item; //选项
        private TextView tv_unit; //单位
        private boolean canBack = true;//是否可以返回键取消，默认可以
        private boolean canTouch = true;//是否可以点击外部取消，默认可以
        private int selectType = 0;
        private CommonDialogAdapter commonDialogAdapter;
        private ArrayList<CommonDialogAdapter.ItemBean> itemBeans;
        private ArrayList<PickItemBean> pickItems;
        private ArrayList<CommonDialogAdapter.ItemBean> items;
        private ClickListener clickListener;
        private String unit; //单位

        public Builder(Context context) {
            this.context = context;
            initView();
        }

        /**
         * 初始化视图
         */
        private void initView() {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = mInflater.inflate(R.layout.dialog_common_checkbox, null);
            mTitle = mView.findViewById(R.id.tv_title);
            mContainer = mView.findViewById(R.id.dialog_common_one_content);
            rcv_item = mView.findViewById(R.id.rcv_item);
            mButton = mView.findViewById(R.id.dialog_common_one_btn);
//            itemBeans = new ArrayList<>();
            pickItems = new ArrayList<>();
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.click(getItems());
                    closeDialog();
                }
            });
        }

        private ArrayList<PickItemBean> getItems() {
            pickItems.clear();
            PickItemBean pickItemBean;
            if (items != null)
                for (CommonDialogAdapter.ItemBean itemBean :
                        items) {
                    if (itemBean.isSelected()) {
                        pickItemBean = new PickItemBean(itemBean.getItemId(), itemBean.getItemCode(), itemBean.getItemName());
                        pickItems.add(pickItemBean);
                    }
                }
            return pickItems;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            mTitle.setText(title);
            return this;
        }


        public Builder setBtnTitle(String tile) {
            mButton.setText(tile);
            return this;
        }


        /**
         * 设置监听
         *
         * @param clickListener
         * @return
         */
        public Builder setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }


        /**
         * 设置数据
         *
         * @param itemBeans
         * @return
         */
        public Builder setItemDatas(ArrayList<CommonDialogAdapter.ItemBean> itemBeans) {
            this.itemBeans = itemBeans;
            return this;
        }


        /**
         * 设置单位
         *
         * @param unit
         * @return
         */
        public Builder setUnit(String unit) {
            this.unit = unit;
            return this;
        }


        /**
         * 设置选择类型
         *
         * @param selectType
         * @return
         */
        public Builder setSelectType(int selectType) {
            this.selectType = selectType;
            return this;
        }

        /**
         * 设置按钮
         *
         * @param text
         * @param listener
         * @return
         */
//        public Builder setButton(String text, View.OnClickListener listener) {
//            mButton.setText(text);
//            mButton.setOnClickListener(listener);
//            return this;
//        }

        /**
         * 设置内容
         *
         * @return
         */
//        public Builder setContainer(View view) {
//            mContainer.addView(view);
//            return this;
//        }

        /**
         * 设置不可以返回键取消
         *
         * @return
         */
        public Builder noCancleBack() {
            canBack = false;
            return this;
        }

        /**
         * 设置不可以点击外部取消
         *
         * @return
         */
        public Builder noCancleTouch() {
            canTouch = false;
            return this;
        }

        public Dialog getmDialog() {
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

        public CommonDialog create() {
            mDialog = new CommonDialog(context, R.style.ComWinTransBackHalfDia);
            if (selectType == CommonDialog.TYPE_SINGLE_SELECT) {
                mButton.setVisibility(View.VISIBLE);
            } else if (selectType == CommonDialog.TYPE_MULTI_SELECT) {
                mButton.setVisibility(View.VISIBLE);
            }

            commonDialogAdapter = new CommonDialogAdapter(itemBeans, selectType, new CommonDialogAdapter.ClickListener() {
                @Override
                public void pickItem(@NotNull CommonDialogAdapter.ItemBean itemBean, @NotNull ArrayList<CommonDialogAdapter.ItemBean> itemList, int typE_MULTI_SELECT) {
                    items = itemList;
//                    ToastUtil.shortShow("选中个数: " + itemList.size());
                    commonDialogAdapter.updateItem(itemBean, typE_MULTI_SELECT);
//                    commonDialogAdapter.notifyDataSetChanged();
                }
            });
            rcv_item.setLayoutManager(new LinearLayoutManager(context));
            rcv_item.setAdapter(commonDialogAdapter);
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


    public interface ClickListener {
        void click(ArrayList<PickItemBean> items);
    }


    public static class PickItemBean {
        private int id;
        private String itemCode;
        private String itemName;

        public PickItemBean(int id, String itemName) {
            this.id = id;
            this.itemName = itemName;
        }

        public PickItemBean(int id, String itemCode, String itemName) {
            this.id = id;
            this.itemCode = itemCode;
            this.itemName = itemName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }
    }
}
