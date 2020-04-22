package com.sctuopuyi.echo.utils.dialog

object CommonDialogHelper {

    fun getDataFromArray(datas: ArrayList<String>?): ArrayList<CommonDialogAdapter.ItemBean>? {
        val itembeans = ArrayList<CommonDialogAdapter.ItemBean>()
        var index: Int = 0
        datas?.forEach {
            itembeans.add(CommonDialogAdapter.ItemBean(index, it, false))
            index++
        }
        return itembeans
    }

//    fun getDataFromArrayCoinList(datas: List<CoinListRow>?): java.util.ArrayList<CommonDialogAdapter.ItemBean>? {
//        val itembeans = ArrayList<CommonDialogAdapter.ItemBean>()
//        var index: Int = 0
//        datas?.forEach {
//            itembeans.add(CommonDialogAdapter.ItemBean(index, "${it.coinCode}(${it.coinName})", false, it.coinId, ""))
//            index++
//        }
//        return itembeans
//    }
//
//    fun getDataFromArrayMortgageCycles(datas: List<MortgageCycleRow>?): java.util.ArrayList<CommonDialogAdapter.ItemBean>? {
//        val itembeans = ArrayList<CommonDialogAdapter.ItemBean>()
//        var index: Int = 0
//        datas?.forEach {
//            itembeans.add(CommonDialogAdapter.ItemBean(index, "${it.time}", false, it.id, it.unit))
//            index++
//        }
//        return itembeans
//    }
//
//    fun getDataFromArrayPlatforAccounts(data: List<PlatformAccountListRow>?): java.util.ArrayList<CommonDialogAdapter.ItemBean>? {
//        val itembeans = ArrayList<CommonDialogAdapter.ItemBean>()
//        var index: Int = 0
//        data?.forEach {
//            itembeans.add(CommonDialogAdapter.ItemBean(index, "${it.bankName}(${it.bankAccount})", false, it.bankAccount, ""))
//            index++
//        }
//        return itembeans
//    }


}