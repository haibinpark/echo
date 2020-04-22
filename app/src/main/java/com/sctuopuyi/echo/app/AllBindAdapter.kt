package com.sctuopuyi.echo.app

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.ui.my.domain.bean.VisitHistoryItemBean


/**
 * Created by ChenGY on 2018-08-20.
 */

//region common

@BindingAdapter(value = ["graphCode"], requireAll = false)
fun showGraphCode(imageView: ImageView, codeImage: String?) {
    if (!codeImage.isNullOrEmpty() && codeImage.startsWith("")
        || codeImage?.startsWith("data:image/*;base64,") == true
        || codeImage?.startsWith("data:image/jpg;base64,") == true
    ) {
        var decode: ByteArray? = null
        var url = codeImage.split(",")[1]
        decode = Base64.decode(url, Base64.DEFAULT)

        var bitmapTypeRequest = Glide.with(imageView.context).load(
            decode ?: decode
        )
        bitmapTypeRequest.placeholder(R.drawable.img_touxiang)
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        bitmapTypeRequest.dontAnimate()
        bitmapTypeRequest.into(imageView)
    }
}

@BindingAdapter("textSpanStyle")
fun setTextStyle(v: TextView, s: String) {
    val span = SpannableString(s)
    span.setSpan(
        ForegroundColorSpan(v.context.resources.getColor(R.color.red_f25e28)),
        3,
        s.length - 3,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    v.text = span
}

@BindingAdapter(value = ["app:playGif"], requireAll = false)
fun playGif(imageView: ImageView, gifUrl: Drawable?) {
//    Glide
//            .with(imageView.context)
//            .load(R.drawable.home_loan)
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            .into(imageView)
//    com.sctuopuyi.echo.utils.GifLoadOneTimeGif.loadOneTimeGif(imageView.context, R.drawable.home_loan, imageView, 1)
}


@BindingAdapter(
    value = ["app:imageUrl", "app:imageError", "app:placeHolderImg", "app:isCircle", "app:isLocal"],
    requireAll = false
)
fun loadImage(
    imageView: ImageView,
    url: String?,
    errorDw: Drawable?,
    placeHolder: Int?,
    isCircle: Boolean? = false,
    isLocal: Boolean? = false
) {
    if (errorDw != null) {
        if (isCircle != null && isCircle) {
            if (isLocal == true) {
                Glide.with(imageView.context)
                    .load(url?.toInt())
                    .error(errorDw)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .circleCrop()
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(url)
                    .error(errorDw)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .circleCrop()
                    .into(imageView)
            }
        } else {
            if (isLocal == true) {
                Glide.with(imageView.context)
                    .load(url?.toInt())
                    .error(errorDw)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .into(imageView)
            } else {

                Glide.with(imageView.context)
                    .load(url)
                    .error(errorDw)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .into(imageView)
            }
        }
    } else {
        if (isCircle != null && isCircle) {
            if (isLocal == true) {
                Glide.with(imageView.context)
                    .load(url?.toInt())
                    .error(R.drawable.icon_product)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .circleCrop()
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(url)
                    .error(R.drawable.icon_product)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .circleCrop()
                    .into(imageView)
            }
        } else {
            if (isLocal == true) {
                Glide.with(imageView.context)
                    .load(url?.toInt())
                    .error(R.drawable.icon_product)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(url)
                    .error(R.drawable.icon_product)
                    .placeholder(placeHolder ?: R.drawable.icon_product)
                    .into(imageView)
            }
        }
    }
}
//
//@BindingAdapter(value = ["imageUrl", "imageError", "isCircle"], requireAll = false)
//fun loadImage(imageView: ImageView, url: String?, errorDw: Drawable?, isCircle: Boolean?) {
//    if (errorDw != null) {
//        if (isCircle != null && isCircle) {
//            Glide.with(imageView.context)
//                    .load(url)
//                    .error(errorDw)
//                    .placeholder(R.drawable.img_touxiang)
//                    .transform(GlidCircleTransform(imageView.context))
//                    .into(imageView)
//        } else {
//            Glide.with(imageView.context)
//                    .load(url)
//                    .error(errorDw)
//                    .placeholder(R.drawable.img_touxiang)
//                    .into(imageView)
//        }
//    } else {
//        if (isCircle != null && isCircle) {
//            Glide.with(imageView.context)
//                    .load(url)
//                    .error(R.drawable.img_touxiang)
//                    .placeholder(R.drawable.img_touxiang)
//                    .transform(GlidCircleTransform(imageView.context))
//                    .into(imageView)
//        } else {
//            Glide.with(imageView.context)
//                    .load(url)
//                    .error(R.drawable.img_touxiang)
//                    .placeholder(R.drawable.img_touxiang)
//                    .into(imageView)
//        }
//    }
//}

@BindingAdapter(value = ["picUrl", "addIcon", "isAdd"], requireAll = false)
fun showImage(imageView: ImageView, picUrl: String?, addIcon: Int?, isAdd: Boolean?) {
    if (isAdd == true) {
        Glide.with(imageView.context)
            .load(R.drawable.img_shangchuan)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(picUrl)
            .into(imageView)
    }
}

@BindingAdapter(value = ["setTextBackground"], requireAll = false)
fun setTextBackground(textView: TextView, authed: Int? = 0) {
    when (authed) {
        0 -> {
            //未认证
            textView.setTextColor(textView.resources.getColor(R.color.red_f25e28))
        }
        1 -> {
            //已经认证
            textView.setTextColor(textView.resources.getColor(R.color.gray_cc))
        }
        2 -> {
            //已经失效
            textView.setTextColor(textView.resources.getColor(R.color.black_393431))
        }
    }
}


/**
 * 时间戳Str  转为  yyyy-MM-dd HH:mm:ss
 */
@BindingAdapter("bindStampStrToYMDHMS")
fun bindStampStrToYMDHMS(tv: TextView, time: String?) {
    time?.let {
        val stampStrToYMDHMS = com.sctuopuyi.echo.utils.DateUtil.stampStrToYMDHMS(time)
        tv.text =
            if (stampStrToYMDHMS == "发布时间: 1970-01-01 08:00:00") "无" else "发布时间: $stampStrToYMDHMS"
    }
}


/**
 * 时间戳Str  转为 MM-dd HH:mm（如果是今天，MM-dd去掉）
 */
@BindingAdapter("bindstampStrToYMDHM2")
fun bindstampStrToYMDHM2(tv: TextView, time: String?) {
    val stampStrToYMDHMS = com.sctuopuyi.echo.utils.DateUtil.stampStrToYMDHM2(time)
    tv.text = if (stampStrToYMDHMS == "01-01 08:00") "无" else stampStrToYMDHMS
}

/**
 * 时间戳Str  转为  yyyy-MM-dd HH:mm（如果是今年，去掉yy-, 如果是今天，去掉MM-dd，显隐  “发布时间 ： ”）
 */
@BindingAdapter(value = ["bindstampStrToYMDHM2Plus", "isShowTip"], requireAll = false)
fun bindstampStrToYMDHM2Plus(tv: TextView, time: String?, isShowTip: Boolean?) {
    if (isShowTip != null && isShowTip) {
        val stampStrToYMDHMS = com.sctuopuyi.echo.utils.DateUtil.stampStrToYMDHM2(time)
        tv.text = "发布时间 ：${if (stampStrToYMDHMS == "1970-01-01 08:00") "无" else stampStrToYMDHMS}"
    } else {
        val stampStrToYMDHMS = com.sctuopuyi.echo.utils.DateUtil.stampStrToYMDHM2(time)
        tv.text = if (stampStrToYMDHMS == "1970-01-01 08:00") "无" else stampStrToYMDHMS
    }
}

/**
 * 时间戳Str  转为  yyyy-MM-dd HH:mm（如果是今年，去掉yy-, 如果是今天，去掉MM-dd，如果是已下架，显示 “已下架”）
 */
@BindingAdapter(value = ["bindstampStrToYMDHMAndSoldOut", "statusSoloOut"])
fun bindstampStrToYMDHMAndSoldOut(tv: TextView, time: String?, statusSoloOut: String?) {
    if (statusSoloOut != null) {
        tv.text = statusSoloOut
    } else {
        val stampStrToYMDHMS = com.sctuopuyi.echo.utils.DateUtil.stampStrToYMDHM2(time)
        tv.text = if (stampStrToYMDHMS == "1970-01-01 08:00") "无" else stampStrToYMDHMS
    }
}

/**
 * 保留两位小数
 */
@BindingAdapter(
    value = ["keepTwoDecimal", "keepTwoDecimalFloat", "keepTwoDecimalStatus"],
    requireAll = false
)
fun keepTwoDecimal(tv: TextView, resString: String?, resFloat: Float?, status: String?) {
    //都为空就不往下进行了
    if (resString.isNullOrBlank() && resFloat == null) return
    //如果解析出错，（抛开0.0），就显示原String
    if (!resString.isNullOrBlank() && resString != "0.0" && com.sctuopuyi.echo.utils.StrNumUtil.Str2Float(resString) == 0f) {
        tv.text = resString
        return
    }
    //统一格式化为Float数据
    val res = if (resString.isNullOrBlank()) resFloat else com.sctuopuyi.echo.utils.StrNumUtil.Str2Float(resString)
    if (status == null) {
        tv.text = com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res)
    } else {
        tv.text = when (status) {
            "rmb" -> "￥ " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res)
            "dingjin" -> "定金金额：" + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res) + "元"
            "wan" -> com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res) + "万"
            "rmbWan" -> "￥ " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res) + "万"
            "usd" -> "＄ " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res)
            "hjWan" -> "合计： " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res) + "万"
            "bjWan" -> "报价： " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res) + "万"
            "divideWan" -> com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
            "divideWanWithPrefixQWJG" -> "期望价格: " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
            "rmbDivideWan" -> "￥ " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
            "bjDivideWan" -> "报价： " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
            "hjDivideWan" -> "合计： " + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
            "zdjDivideWan" -> {
                val content = "指导价：" + com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimalDivideWan(res) + "万"
                val spannableString = SpannableString(content)
                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,
                    content.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString
            }
            else -> com.sctuopuyi.echo.utils.StrNumUtil.keepTwoDecimal(res)
        }
    }
}

/**
 * 虚拟货币保留八位小数
 */
@BindingAdapter("keepEightDecimal")
fun bindKeepEightDecimal(tv: TextView, res: String?) {
    res?.let {
        tv.text = com.sctuopuyi.echo.utils.StrNumUtil.keepEightDecimal(res)
    }
}

/**
 * 根据色值给色块设背景色
 */
@BindingAdapter("backColorByValue")
fun bindBackColorByValue(view: View, res: String?) {
    view.setBackgroundColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("$res"))
}

/**
 * 根据intRes给ImageView设置src
 */
@BindingAdapter("imgSrcDraw")
fun bindImgSrcIntRes(img: ImageView, drawable: Drawable) {
    img.setImageDrawable(drawable)
}

/**
 * 限制输入两位小数
 */
@BindingAdapter("limitInputDecimal")
fun limitInputeTwoDecimal(et: EditText, place: Int) {
    com.sctuopuyi.echo.utils.InputFilterUtil.limitXDecimal(et, place)
}


/**
 * 限制输入中文，emoj，与标点符号
 */
@BindingAdapter("onlyNumberAndLetter")
fun getWithOutLetterAndNumberFilter(et: EditText, space: Int) {
    et.filters = com.sctuopuyi.echo.utils.InputFilterUtil.getWithOutLetterAndNumberFilter()
}
/**
 * 限制输入emoj
 */
@BindingAdapter("rejectEmojCharacter")
fun getEmojiFilter(et: EditText, space: Int) {
    et.filters = com.sctuopuyi.echo.utils.InputFilterUtil.getEmojiFilter()
}

//endregion

//region other

@BindingAdapter(value = ["idCardUrl", "isFront"])
fun loadIdCardImage(imageView: ImageView, url: String?, isFront: Boolean) {
//    if (isFront) {
//        Glide.with(imageView.context)
//            .load(url)
//            .error(R.drawable.img_renxiangmian)
//            .into(imageView)
//    } else {
//        Glide.with(imageView.context)
//            .load(url)
//            .error(R.drawable.img_guohuimian)
//            .into(imageView)
//    }
}

@BindingAdapter("textBackground")
fun setTextBackground(view: TextView, isMaxLimit: Boolean) {
    val activity = view.context as Activity

    if (isMaxLimit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.background = activity.getDrawable(R.drawable.shape_solid_fc9b50_r8)
        } else {
            view.background = activity.resources.getDrawable(R.drawable.shape_solid_fc9b50_r8)
        }
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.background = activity.getDrawable(R.drawable.shape_solid_bfbfbf_r8)
        } else {
            view.background = activity.resources.getDrawable(R.drawable.shape_solid_bfbfbf_r8)
        }
    }
}

@BindingAdapter("app:onFocusChange")
fun onFocusChange(text: EditText, listener: View.OnFocusChangeListener) {
    text.onFocusChangeListener = listener
}

@BindingAdapter("selectStatus")
fun onSelectStatus(view: View, status: Boolean) {
    view.isSelected = status
}

@BindingAdapter("tvIsSelected")
fun onSelectStatus(tv: TextView, status: Boolean) {
    if (status) {
        tv.textSize = 18f
        tv.setTextColor(Color.parseColor("#333333"))
        tv.typeface = Typeface.DEFAULT_BOLD
    } else {
        tv.textSize = 16f
        tv.setTextColor(Color.parseColor("#707070"))
        tv.typeface = Typeface.DEFAULT
    }
}
//
//@BindingAdapter("isDanhang")
//fun isDanHang(ll: LinearLayout, status: Boolean) {
//    if (status) {
//        ll.setBackgroundResource(R.drawable.selector_press_white_gray)
//    } else {
//        ll.setBackgroundResource(R.drawable.selector_press_gray_white)
//    }
//}



//
//@BindingAdapter("orderListBtnLeft")
//fun doOrderListBtnLeft(tv: TextView, status: String) {
//    when (status) {
//        "2" -> tv.visibility = View.GONE
//        else -> {
//            tv.visibility = View.VISIBLE
//            when (status) {
//                "0", "1" -> doOrderListBtnStyle(tv, "取消订单")
//                else -> doOrderListBtnStyle(tv, "删除订单")
//            }
//        }
//    }
//}

//orderType 0购车1寻车----isCompleted 0，未完善，1，已完善-----type 0采购，1销售
//@BindingAdapter("orderTextColorStyle")
//fun doOrderListBtnStyle(tv: TextView, s: String) {
//    when (s) {
//        "取消订单" -> {
//            tv.setBackgroundResource(R.drawable.selector_press_stro_gray586_r3)
//            tv.setTextColor(tv.context.resources.getColor(R.color.gray_586))
//        }
//        "删除订单" -> {
//            tv.setBackgroundResource(R.drawable.selector_press_stro_redfe_r3)
//            tv.setTextColor(tv.context.resources.getColor(R.color.red_fe))
//        }
//        "立即支付", "修改金额" -> {
//            tv.setBackgroundResource(R.drawable.selector_press_stro_yellowfd_r3)
//            tv.setTextColor(tv.context.resources.getColor(R.color.yellow_fd))
//        }
//        "完善订单", "接单办理" -> {
//            tv.setBackgroundResource(R.drawable.selector_press_stro_green00_r3)
//            tv.setTextColor(tv.context.resources.getColor(R.color.green_00))
//        }
//        else -> {
//            tv.setBackgroundResource(R.drawable.selector_press_stro_redfe_r3)
//            tv.setTextColor(tv.context.resources.getColor(R.color.red_fe))
//        }
//    }
//}

@BindingAdapter("colorByStatusGray33")
fun colorByStatusGray33(tv: TextView, status: String?) {
    if (status.isNullOrBlank()) {
        tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#333333"))
    } else {
        if (status == "0") {
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#333333"))
        } else {
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#888888"))
        }
    }
}

@BindingAdapter("colorByStatusGreen08")
fun colorByStatusGreen08(tv: TextView, status: String?) {
    if (status.isNullOrBlank()) {
        tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#08ccbb"))
    } else {
        if (status == "0") {
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#08ccbb"))
        } else {
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#888888"))
        }
    }
}

@BindingAdapter("findcarStatus")
fun findcarStatus(tv: TextView, status: String?) {
    if (!status.isNullOrBlank()) {
        if (status == "0") {
            tv.text = "报价中"
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#08ccbb"))
            tv.setBackgroundResource(R.drawable.shape_solid_green_stro_green_r2)
        } else {
            tv.setTextColor(com.sctuopuyi.echo.utils.StrNumUtil.colorStr2ColorInt("#888888"))
            tv.setBackgroundResource(R.drawable.shape_solid_gray_stro_gray_r2)
            tv.text = when (status) {
                "2" -> "已结束"
                "3" -> "已删除"
                else -> "已取消"
            }
        }
    }
}

@BindingAdapter("textAddImg")
fun textAddImg(tv: TextView, str: String?) {
//    if (!str.isNullOrBlank()) {
//        val placeholder = "a"
//        val kong = " "
//        val strRes = "$str$kong$placeholder$kong$placeholder"
//        val spanStr = SpannableString(strRes)
//        val imageSpan1 = ImageSpan(tv.context, R.drawable.icon_card_qiye)
//        val imageSpan2 = ImageSpan(tv.context, R.drawable.icon_card_sihiming)
//        val len = strRes.length
//        spanStr.setSpan(imageSpan1, len - 3, len - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spanStr.setSpan(imageSpan2, len - 1, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        tv.text = spanStr
//    }
}
//
//@BindingAdapter(value = ["pickStatus"], requireAll = false)
//fun pickStatus(v: View, status: Boolean) {
//    if (status) {
//        v.setBackgroundResource(R.color.red_e3)
//    } else {
//        v.setBackgroundResource(R.color.transparent)
//    }
//}
//
//@BindingAdapter("bindSubItemAdapter")
//fun bindSubItemAdapter(view: RecyclerView, itemBean: DataProveBean) {
//
//
//    val gridItems: ObservableArrayList<GridItem> = ObservableArrayList()
//
//    itemBean.gridItems.forEach {
//        gridItems.add(it)
//    }
//}

@BindingAdapter(
    value = ["content", "hasDeleteLine", "startPosition", "endPosition"],
    requireAll = false
)
fun addDeleteLineOnTextview(
    v: TextView,
    content: String?,
    hasDeleteLine: Boolean?,
    startPosition: Int?,
    endPosition: Int?
) {
    val spannableString = SpannableString(content)
    if (hasDeleteLine == true) {
        if (startPosition == null && endPosition == null) {
            spannableString.setSpan(
                StrikethroughSpan(), 0, content?.length
                    ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            spannableString.setSpan(
                StrikethroughSpan(), startPosition ?: 0, endPosition ?: 0,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    v.text = spannableString
}

@BindingAdapter(value = ["setTextColor"], requireAll = true)
fun setTextColor(v: TextView, textColor: String?) {
    v.setTextColor(Color.parseColor(textColor))
}

@BindingAdapter("setTextColorByStatus")
fun setTextColorByStatus(v: TextView, selected: Boolean) {
    if (selected) {
        v.setTextColor(Color.parseColor("#08CCBB"))
    } else {
        v.setTextColor(Color.parseColor("#333333"))
    }
}
//
//@BindingAdapter("prepaymentModeImg")
//fun prepaymentModeImg(v: ImageView, mode: String?) {
//    mode?.let {
//        if (mode == "0") {
//            v.setImageResource(R.drawable.img_bg_tag02)
//        } else {
//            v.setImageResource(R.drawable.img_bg_tag_01)
//        }
//    }
//}
//
//@BindingAdapter("backgroundbystatus")
//fun setBackgroundByStatus(v: ImageView, selected: Boolean) {
//    if (selected) {
//        v.setBackgroundResource(R.drawable.icon_dian)
//    } else {
//        v.setBackgroundResource(R.drawable.icon_quan)
//    }
//}

@BindingAdapter("setGridTextColorByStatus")
fun setGridTextColorByStatus(v: TextView, selected: Boolean) {
    if (selected) {
        v.setTextColor(Color.parseColor("#FFFFFF"))
    } else {
        v.setTextColor(Color.parseColor("#00D3C0"))
    }
}
//
//@BindingAdapter("setGridBackgroundByStatus")
//fun setGridBackgroundByStatus(v: View, selected: Boolean) {
//    if (selected) {
//        v.setBackgroundResource(R.drawable.shape_stroke0_solid_green_00d3c0_r3)
//    } else {
//        v.setBackgroundResource(R.drawable.shape_stroke1_green_solid_white_r3)
//    }
//}


@BindingAdapter("setOnlyNumber")
fun setOnlyNumber(view: EditText, onlyNumber: Boolean) {
    if (onlyNumber) {
        view.inputType = InputType.TYPE_CLASS_NUMBER
    } else {
        view.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }
}


//@BindingAdapter("messageBackground")
//fun setMessageBackground(view: LinearLayout, bean: MyMessagesItemBean) {
//    if (bean.hasExpanded && bean.hasReaded) {
//        view.background = view.context.resources.getDrawable(R.mipmap.img_background_msg3)
////        view.cardElevation = SystemUtil.dp2px(2.0f).toFloat()
////        view.useCompatPadding = true
//    } else if (bean.hasExpanded && !bean.hasReaded) {
//        view.background = view.context.resources.getDrawable(R.mipmap.img_background_msg3)
////        view.cardElevation = SystemUtil.dp2px(2.0f).toFloat()
////        view.useCompatPadding = true
//    } else if (!bean.hasExpanded && bean.hasReaded) {
//        view.background = view.context.resources.getDrawable(R.mipmap.img_background_msg2)
////        view.cardElevation = 0.0f
////        view.useCompatPadding = false
//    } else if (!bean.hasExpanded && !bean.hasReaded) {
//        view.background = view.context.resources.getDrawable(R.mipmap.img_background_msg1)
////        view.cardElevation = SystemUtil.dp2px(2.0f).toFloat()
////        view.useCompatPadding = true
//    } else {
//        view.background = view.context.resources.getDrawable(R.mipmap.img_background_msg1)
////        view.cardElevation = SystemUtil.dp2px(2.0f).toFloat()
////        view.useCompatPadding = true
//    }
//}


@BindingAdapter("historyBtnStyle")
fun setVisitHistoryTextViewColor(tv: TextView, bean: VisitHistoryItemBean) {
    if (bean.hasOff == true) {
        tv.setTextColor(tv.context.resources.getColor(R.color.white_60))
        tv.background = tv.context.resources.getDrawable(R.drawable.shape_solid_gray_cc_r15)
//        tv.background = tv.context.resources.getDrawable(R.drawable.shape_solid_red_r15)
    } else {
        if (bean.isApi == true) {
            tv.setTextColor(tv.context.resources.getColor(R.color.color_api_item_text))
            tv.background = tv.context.resources.getDrawable(R.drawable.shape_solid_red_r15)
        } else {
            tv.setTextColor(tv.context.resources.getColor(R.color.color_h5_item_text))
            tv.background = tv.context.resources.getDrawable(R.drawable.shape_stroke_red_r15)
        }
    }
}

@BindingAdapter("historySectionBackground")
fun setVisitHistorySectionBackground(ll: LinearLayout, hasOff: Boolean?) {
//    if (hasOff == true) {
//        ll.background = ll.context.resources.getDrawable(R.drawable.img_background_alpha_60)
////        ll.background = ll.context.resources.getDrawable(R.drawable.cover_item_bg)
//    } else {
//        ll.background = ll.context.resources.getDrawable(R.drawable.cover_item_bg)
//    }
}


@BindingAdapter("textUnderLine")
fun setTextUnderLine(v: TextView, value: String?) {
    if (value.isNullOrEmpty()) {

    } else {
        val span = SpannableString(value)
        span.setSpan(UnderlineSpan(), 0, value?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        v.text = span
    }
}


//endregion