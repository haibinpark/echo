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

class PopWindowAdapter(
    private val itemList: ArrayList<PopWindowItemBean>
    , private val context: Activity
) : RecyclerView.Adapter<PopWindowAdapter.PopWindowViewHolder>() {

    private var callback: PopWindowAdapterInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopWindowViewHolder {
        return PopWindowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_popup_picker, parent, false),
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
        , private val adapter: PopWindowAdapter
        , private val context: Activity
    ) : RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView? = null
        var productNameTextView: TextView? = null
        var itemTelativeLayout: RelativeLayout? = null
        fun bindData(position: Int) {
            productImageView = itemView.findViewById(R.id.iv_product_image)
            productNameTextView = itemView.findViewById(R.id.tv_product_name)
            itemTelativeLayout = itemView.findViewById(R.id.rl_item)
            val bean = itemList[position]
            com.sctuopuyi.echo.utils.ImageUtil.setImage(context, bean.productImageUrl, productImageView, false)
            productNameTextView?.text = bean.productName
            itemTelativeLayout?.setOnClickListener {
                callback?.onItemChildClick(adapter, it, bean?.id ?: "", bean)
            }
            if (bean.isShowImage == true) {
                productImageView?.visibility = View.VISIBLE
            } else {
                productImageView?.visibility = View.GONE
            }
        }

    }


    interface PopWindowAdapterInterface {
        fun onItemChildClick(
            adapter: PopWindowAdapter,
            view: View,
            position: String,
            bean: PopWindowItemBean
        )
    }
}