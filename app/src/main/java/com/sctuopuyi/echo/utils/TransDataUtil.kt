package com.sctuopuyi.echo.utils

/**
 * Created by ChenGY on 2018-09-04.
 */
class TransDataUtil {

    companion object {

        fun <T, R> transListResp(res: List<T>?, trans: (T) -> R): MutableList<R> {
            var result: MutableList<R> = ArrayList()
            res?.let {
                result = it.map {
                    trans(it)
                }.toMutableList()
            }
            return result
        }

        fun <T, R> transListRespFilter(res: List<T>?, filter: (T) -> Boolean, trans: (T) -> R): MutableList<R> {
            var result: MutableList<R> = ArrayList()
            res?.let {
                result = it
                        .filter {
                            filter(it)
                        }.map {
                            trans(it)
                        }.toMutableList()
            }
            return result
        }

        fun <T, R> transListRespIndex(res: List<T>?, trans: (T, Int) -> R): MutableList<R> {
            var result: MutableList<R> = ArrayList()
            res?.let {
                result = it.mapIndexed { index, item ->
                    trans(item, index)
                }.toMutableList()
            }
            return result
        }

        /**
         * 拼接车的参数信息
         */
        fun spellCarParameters(carType: String?, comeStr: String?, colorStr: String = "", province: String = "", city: String = ""): String {
            val color = if (colorStr.isNullOrBlank()) "" else " | $colorStr"
            val area = if (province.isNullOrBlank()) "" else " | 销$province$city"
            var exist = ""
            val come = if (comeStr.isNullOrBlank()) {
                exist = if (carType.isNullOrBlank()) {
                    "不限"
                } else {
                    when (carType) {
                        "0" -> "现车"
                        "1" -> "期车"
                        else -> "不限"
                    }
                }
                ""
            } else {
                exist = if (carType.isNullOrBlank()) {
                    " | 不限"
                } else {
                    when (carType) {
                        "0" -> " | 现车"
                        "1" -> " | 期车"
                        else -> " | 不限"
                    }
                }
                if (comeStr == "0") "中规车" else "进口平行车"
            }
            return "$come$exist$color$area"
        }

        /**
         * 拼接车的优惠信息
         */
        fun spellCarDiscountsTip(priceType: String?, priceParams: String): String {
            val keepTwoDecimal = StrNumUtil.keepTwoDecimal(priceParams)
            return if (priceType.isNullOrBlank()) {
                "${keepTwoDecimal}万"
            } else {
                when (priceType) {
                    "1" -> {
                        "优惠${keepTwoDecimal}点"
                    }
                    "2" -> "优惠${keepTwoDecimal}万"
                    "3" -> "加价${keepTwoDecimal}万"
                    else -> "${keepTwoDecimal}万"
                }
            }
        }

        /**
         * 电议或确切价格
         */
        fun transOfferMoneyType(expectationPrice: String, priceType: String = "0"): String {
            return if (priceType == "0") {
                expectationPrice
            } else {
                "电议"
            }
        }

        /**
         * 金融产品额度限制
         */
        fun transFinancialProductMoneyLimit(min: Float, max: Float): String {
            return "${StrNumUtil.keepTwoDecimal(min)}万~${StrNumUtil.keepTwoDecimal(max)}万"
        }
    }
}