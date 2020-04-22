package com.sctuopuyi.echo.utils.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sctuopuyi.echo.R


class CommonDialogAdapter(private val itemList: ArrayList<ItemBean>
                          , val selectType: Int
                          , val clickListener: ClickListener) : RecyclerView.Adapter<CommonDialogAdapter.CommonDialogViewHolder>() {


    companion object {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonDialogViewHolder {
        return CommonDialogViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_layout_pick, parent, false),
                selectType,
                itemList
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommonDialogViewHolder, position: Int) {
        holder.bindData(itemList[position], clickListener)
    }


    fun updateItem(itemBean: ItemBean, typE_MULTI_SELECT: Int) {
        val it = itemList.iterator()
        when (typE_MULTI_SELECT) {
            com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_MULTI_SELECT -> {
                while (it.hasNext()) {
                    val bean = it.next()
                    if (bean.itemId == itemBean.itemId) {
                        bean.isSelected = !itemBean.isSelected
                        break
                    }
                }
            }
            com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_SINGLE_SELECT -> {
                while (it.hasNext()) {
                    val bean = it.next()
                    if (bean.itemId == itemBean.itemId) {
                        bean.isSelected = true
                    } else {
                        bean.isSelected = false
                    }
                }
            }
        }
        notifyDataSetChanged()
    }


    fun setUnit(unit: String) {
        val it = itemList.iterator()
        while (it.hasNext()) {
            val bean = it.next()
            bean.unit = unit
        }
        notifyDataSetChanged()
    }


    class CommonDialogViewHolder(val itemview: View?, val selectType: Int, val itemList: ArrayList<ItemBean>) : RecyclerView.ViewHolder(itemview!!) {

        var iv_status: ImageView? = null
        var tv_left_value: TextView? = null
        var rl_select: RelativeLayout? = null
        var tv_unit: TextView? = null
        fun bindData(itemBean: ItemBean, clickListener: ClickListener) {
            iv_status = itemView.findViewById(R.id.iv_status)
            tv_left_value = itemView.findViewById(R.id.tv_left_value)
            rl_select = itemView.findViewById(R.id.rl_select)
            tv_unit = itemview?.findViewById(R.id.tv_unit)
            tv_left_value?.text = itemBean.itemName
            if (itemBean.unit.isNullOrBlank()) {
                tv_unit?.visibility = View.GONE
            } else {
                tv_unit?.visibility = View.VISIBLE
                tv_unit?.text = itemBean.unit
            }
            when (selectType) {
                com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_MULTI_SELECT -> {
                    if (itemBean.isSelected) {
                        iv_status?.background = itemView.resources.getDrawable(R.drawable.icon_fuxuan)
                    } else {
                        iv_status?.background = itemView.resources.getDrawable(R.drawable.icon_fuxuan_weixuan)
                    }
                    rl_select?.setOnClickListener(View.OnClickListener {
                        clickListener.pickItem(itemBean, itemList, com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_MULTI_SELECT)
                    })
                }
                com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_SINGLE_SELECT -> {
                    if (itemBean.isSelected) {
                        iv_status?.background = itemView.resources.getDrawable(R.drawable.icon_yixuan)
                    } else {
                        iv_status?.background = itemView.resources.getDrawable(R.drawable.icon_daixuan_bigger)
                    }
                    rl_select?.setOnClickListener(View.OnClickListener {
                        clickListener.pickItem(itemBean, itemList, com.sctuopuyi.echo.utils.dialog.CommonDialog.TYPE_SINGLE_SELECT)
                    })
                }
            }
        }
    }

    class ItemBean {


        constructor(itemId: Int, itemName: String, isSelected: Boolean) {
            this.itemId = itemId
            this.itemName = itemName
            this.isSelected = isSelected
        }

        constructor(itemId: Int, itemName: String, isSelected: Boolean, unit: String) {
            this.itemId = itemId
            this.itemName = itemName
            this.isSelected = isSelected
            this.unit = unit
        }

        constructor()

        constructor(itemId: Int, itemName: String, isSelected: Boolean, itemCode: String, unit: String) {
            this.itemId = itemId
            this.itemName = itemName
            this.isSelected = isSelected
            this.itemCode = itemCode
            this.unit = unit
        }


        var itemId: Int = -1
        var itemName: String = ""
        var isSelected: Boolean = false
        var itemCode: String = ""
        var unit: String = ""
    }


    interface ClickListener {
        fun pickItem(itemBean: ItemBean, itemList: ArrayList<ItemBean>, typE_MULTI_SELECT: Int)
    }

}