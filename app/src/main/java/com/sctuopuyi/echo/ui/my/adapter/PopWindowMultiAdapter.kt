package com.sctuopuyi.echo.ui.my.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.ui.my.widget.PopWindowItemBean

class PopWindowMultiAdapter(
    private val itemList: ArrayList<PopWindowItemBean>
    , private val context: Activity
) : RecyclerView.Adapter<PopWindowMultiAdapter.PopWindowViewHolder>() {

    private var callback: PopWindowAdapterInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopWindowViewHolder {
        return PopWindowViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_popup_picker_multi,
                parent,
                false
            ),
            itemList,
            this.callback,
            this,
            context
        )
    }


    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: PopWindowViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun setCallback(callback: PopWindowAdapterInterface?) {
        this.callback = callback
    }


    class PopWindowViewHolder(
        itemView: View
        , private val itemList: ArrayList<PopWindowItemBean>
        , private val callback: PopWindowAdapterInterface?
        , private val adapter: PopWindowMultiAdapter
        , private val context: Activity
    ) : RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView? = null
        var productNameTextView: TextView? = null
        var itemTelativeLayout: RelativeLayout? = null
        var ivSelectImageView: ImageView? = null
        var eduTextView: TextView? = null
        fun bindData(position: Int) {
            productImageView = itemView.findViewById(R.id.iv_product_image)
            productNameTextView = itemView.findViewById(R.id.tv_product_name)
            itemTelativeLayout = itemView.findViewById(R.id.rl_item)
            ivSelectImageView = itemView.findViewById(R.id.iv_select)
            eduTextView = itemView.findViewById(R.id.tv_edu)
            val bean = itemList[position]
            com.sctuopuyi.echo.utils.ImageUtil.setImage(context, bean.productImageUrl, productImageView, false)
            productNameTextView?.text = bean.productName
            eduTextView?.text = bean.amountDesc
            itemTelativeLayout?.setOnClickListener {
                callback?.onItemChildClick(adapter, it, bean?.id ?: "", bean)
            }
            if (bean.isSelected == true) {
//                ivSelectImageView?.background = context.resources.getDrawable(R.mipmap.btn_selected)
            } else {
//                ivSelectImageView?.background = context.resources.getDrawable(R.mipmap.btn_select)
            }
        }

    }


    interface PopWindowAdapterInterface {
        fun onItemChildClick(
            adapter: PopWindowMultiAdapter,
            view: View,
            position: String,
            bean: PopWindowItemBean
        )
    }
}