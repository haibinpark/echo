package com.sctuopuyi.echo.data


import android.annotation.SuppressLint
import android.content.Context
import com.sctuopuyi.echo.data.db.DatabaseHelper
import com.sctuopuyi.echo.data.http.HttpHelper
import com.sctuopuyi.echo.data.local.SharedPreferenceHelper
import io.reactivex.Observable
import android.telephony.TelephonyManager

class DatamanagerHelper(
    val httpHelper: HttpHelper,
    val sharedPreferenceHelper: SharedPreferenceHelper,
    val databaseHelper: DatabaseHelper
) {

    //region http method



    fun logout(): Observable<com.sctuopuyi.echo.data.http.response.BaseHttpResponse<Boolean>> {
        return httpHelper.logout()

    }

    //region local method
    fun getLocationName(): String {
        return ""
    }


    fun getIsLogin(): Boolean {
        return sharedPreferenceHelper.isLogin()
    }


    fun setIsLogin(status: Boolean) {
        sharedPreferenceHelper.saveIsLogin(status)
    }


    fun getHeadImg(): String? {
        return sharedPreferenceHelper.getUserHead()
    }

    fun getUserName(): String? {
        return sharedPreferenceHelper.getAccountName()
    }

    fun getSecretKey(): String? {
        return sharedPreferenceHelper.getSecretKey()
    }


    fun getPhone(): String? {
        return sharedPreferenceHelper.getPhoneNum()
    }

    fun getPhoneNoHidden(): String? {
        return sharedPreferenceHelper.getPhoneNoHidden()
    }

    @SuppressLint("MissingPermission")
    fun getLocalPhone(context: Context): String? {
        val mTelephonyMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return mTelephonyMgr.line1Number
    }

    fun saveOtherExtInfoUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferenceHelper.saveOtherExtInfoUpdateTimestamp(currentStampStr)
    }


    fun getOtherDeviceInfoUpdateTimestamp(): Long {
        return this.sharedPreferenceHelper.getOtherDeviceInfoUpdateTimestamp().toLong()
    }

    fun saveLocationInfoUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferenceHelper.saveLocationInfoUpdateTimestamp(currentStampStr)

    }

    fun getLocationInfoUpdateTimestamp(): Long {
        return this.sharedPreferenceHelper.getLocationInfoUpdateTimestamp().toLong()

    }

    private fun savePrivateMsgUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferenceHelper.savePrivateMsgUpdateTimestamp(currentStampStr)
    }

    private fun getPrivateMessageUpdateTimestamp(): Long {
        return this.sharedPreferenceHelper.getPrivateMessageUpdateTimestamp().toLong()

    }

    fun generateDataToken() {
        sharedPreferenceHelper.generateDataToken()
    }

    //endregion

    //region 手机app，手机联系人，手机短信，通话记录

    //region 获取链接地址


    fun clearCache() {
        sharedPreferenceHelper.clearUserInfo()
    }

    fun firstLoad(): Boolean {
        return sharedPreferenceHelper.firstLoad()
    }

    fun saveFirstLoad(status: Boolean) {
        this.sharedPreferenceHelper.saveFirstLoad(status)
    }

    fun setUserLowScoreType(lowScore: Boolean) {
        this.sharedPreferenceHelper.setUserLowScoreType(lowScore)
    }

    fun getUserLowScoreType(): Boolean {
        return this.sharedPreferenceHelper.getUserLowScoreType()
    }

    fun setApproveExpiredTag(tag: Boolean) {
        this.sharedPreferenceHelper.setApproveExpiredTag(tag)
    }

    fun getApproveExpiredTag(): Boolean {
        return this.sharedPreferenceHelper.getApproveExpiredTag()
    }

    fun setMainOutListenTag(tag: Boolean) {
        this.sharedPreferenceHelper.setMainOutListenTag(tag)
    }

    fun getMainOutListenTag(): Boolean {
        return this.sharedPreferenceHelper.getMainOutListenTag()
    }

    fun saveAccountName(userName: String?) {
        this.sharedPreferenceHelper.saveAccountName(userName ?: "")
    }

    fun getPasswd(): String {
        return this.sharedPreferenceHelper.getPasswd()
    }

    fun savePasswd(passwd:String) {
       this.sharedPreferenceHelper.savePasswd(passwd)
    }

    //endregion

}