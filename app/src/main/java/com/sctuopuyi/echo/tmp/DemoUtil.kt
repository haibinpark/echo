package com.sctuopuyi.echo.tmp

import com.sctuopuyi.echo.ui.my.domain.bean.MyMessagesItemBean
import com.sctuopuyi.echo.ui.my.widget.PopWindowBean
import com.sctuopuyi.echo.ui.my.widget.PopWindowInterface
import com.sctuopuyi.echo.ui.my.widget.PopWindowItemBean

object DemoUtil {

//    fun getHomes(): List<HomeItemBean> {
//        return (0..4).asSequence().map {
//            HomeItemBean(
//                it.toString(),
//                "name$it",
//                "name$it",
//                "3000~5000",
//                "额度范围(元)",
//                "14小时放款$it",
//                "月费率(1%~3%)$it",
//                "贷款期限3~12月"
//            )
//        }.toList()
//    }
//
//    fun getBannerInfo(): List<BannerInfoItemBean> {
//        return (0..2).asSequence().map {
//            BannerInfoItemBean(
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564141571&di=bf5730719d1200e4f86e8ecae838d605&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01090c554273960000019ae9858bfa.jpg%401280w_1l_2o_100sh.jpg",
//                "http://www.baidu.com"
//            )
//        }.toList()
//    }

    fun getMessages(): List<MyMessagesItemBean> {
        return (0..10).asSequence().mapIndexed { index, i ->
            MyMessagesItemBean(
                index.toString(),
                "消息标题$index",
                "消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容$index",
                "2019-10-${index + 1}",
                false,
                false
            )
        }.toList()
    }

    fun getPopWindowBean(callback: PopWindowInterface): PopWindowBean {
        return PopWindowBean(
            "",
            (0..4).asSequence().mapIndexed { index, i ->
                PopWindowItemBean(index.toString(), "", "产品名称$index")
            }.toList(),
            callback
        )
    }

    fun getPopWindowItemBeans(): List<PopWindowItemBean> {
        return (0..2).asSequence().mapIndexed { index, i ->
            PopWindowItemBean(index.toString(), null, "支付${index}")
        }.toList()
    }

}