package com.sctuopuyi.echo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sctuopuyi.echo.BuildConfig
import com.sctuopuyi.echo.app.App
import com.sctuopuyi.echo.app.Constants
import java.util.*

class SharedPreferenceHelper(private var context: Context) {


    /**
     * 获取用户Id
     */
    fun getAccountId(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_ID,
            ""
        )
    }

    /**
     * 保存用户Id
     */
    fun saveAccountId(accountId: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_ID, accountId).apply()
    }

    /**
     * 获取用户token
     */
    fun getUserToken(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_TOKEN,
            ""
        )
    }

    /**
     * 保存用户token
     */
    fun saveUserToken(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_TOKEN, str).apply()
    }

    /**
     * 获取公钥
     */
    fun getSecretKey(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_SECREKEY,
            ""
        )
    }

    /**
     * 保存公钥
     */
    fun saveSecretKey(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_SECREKEY, str).apply()
    }

    fun getDeviceToken(): String {
        val result = sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_DEVICE_TOKEN,
            ""
        )
        if (result.isBlank()) {
            saveDeviceToken(com.sctuopuyi.echo.utils.SystemUtil.deviceId(App.getInstance()))
        }
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_DEVICE_TOKEN,
            ""
        )
    }

    private fun saveDeviceToken(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_DEVICE_TOKEN, str).apply()
    }

    /**
     * 获取用户头像
     */
    fun getUserHead(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_HEADER_URL,
            ""
        )
    }

    /**
     * 保存用户头像
     */
    fun saveUserHead(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_HEADER_URL, str).apply()
    }

    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_LOGIN_STATUS,
            false
        )
    }

    fun saveIsLogin(b: Boolean) {
        sharedPreferences.edit()
            .putBoolean(com.sctuopuyi.echo.app.Constants.UserKey.KEY_LOGIN_STATUS, b).apply()
    }

    fun getAccountEmail(): String {
        return sharedPreferences.getString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_EMAIL, "")
    }

    fun saveAccountEmail(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_EMAIL, str).apply()
    }

    fun getPhoneNum(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE,
            ""
        )
    }

    fun getPhoneNoHidden(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE_NO_HIDDEN,
            ""
        )
    }

    fun savePhoneNum(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE, str).apply()
    }

    fun getIsAuthenId(): Boolean {
        return sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_ID,
            false
        )
    }

    fun saveIsAuthenId(b: Boolean) {
        sharedPreferences.edit()
            .putBoolean(com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_ID, b).apply()
    }

    fun getIsAuthenBank(): Boolean {
        return sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_BANK,
            false
        )
    }

    fun saveIsAuthenBank(b: Boolean) {
        sharedPreferences.edit()
            .putBoolean(com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_BANK, b).apply()
    }

    fun getDataTokenCgy(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_DATA_TOKEN,
            UUID.randomUUID().toString()
        )
    }

    fun newDataTokenCgy() {
        sharedPreferences.edit()
            .putString(
                com.sctuopuyi.echo.app.Constants.UserKey.KEY_DATA_TOKEN,
                UUID.randomUUID().toString()
            ).apply()
    }

    fun getAccountName(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_NAME,
            ""
        )
    }

    fun saveAccountName(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_NAME, str).apply()
    }


    fun saveRealName(realName: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_REAL_NAME, realName).apply()
    }

    fun getRealName(): String {
        return sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_REAL_NAME,
            ""
        )

    }


    /**
     * 退出登录时，清除用户信息
     */
    fun clearUserInfo() {
        sharedPreferences.edit().apply {
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_LOGIN_STATUS)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_ID)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_TOKEN)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_EMAIL)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_HEADER_URL)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_ACCOUNT_NAME)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_ID)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_IS_AUTHEN_BANK)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_FIRST_RECOMMEND)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_SOCRE_TYPE)
            remove(com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE_NO_HIDDEN)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_EXTRA_DEVICE_INFO)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_DEVICE_INFO)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_DEVICE_NUM)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_IS_SIMULATOR)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_PLATFORM)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO_TIMESTAMP)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_LOCATION_INFO_TIMESTAMP)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_PRIVATE_MSG_INFO_TIMESTAMP)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_APPROVE_STATUS_TAG)
            remove(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_MAIN_OUT_LISTEN_TAG)
        }.apply()
    }
//
//    /**
//     * 获取用户对象
//     */
//    fun getUserDtoFromLocal(): UserDto? {
//        val userDtoJson =
//            this.sharedPreferences?.getString(KEY_USER_DTO, "")
//        return if (userDtoJson.isNullOrBlank()) {
//            null
//        } else {
//            Gson().fromJson(userDtoJson, UserDto::class.java)
//        }
//    }
//
//    /**
//     * 保存用户对象
//     */
//    fun saveUserDtoToLocal(userDto: UserDto?) {
//        val editor = this.sharedPreferences?.edit()
//        editor?.putString(KEY_USER_DTO, Gson().toJson(userDto))
//        editor?.apply()
//    }

    fun getDataToken(): String? {
        return if (this.sharedPreferences?.getBoolean(TAG_CREATE_DATA_TOKEN, false)!!) {
            UUID.randomUUID().toString()
        } else {
            this.sharedPreferences?.edit()?.apply {
                putBoolean(TAG_CREATE_DATA_TOKEN, false)
            }
            this.sharedPreferences?.getString(TAG_DATA_TOKEN, UUID.randomUUID().toString())
        }
    }

    fun getExtraInfo(): String? {
        return this.sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_EXTRA_DEVICE_INFO,
            ""
        )
    }

    fun saveExtraInfo(toJson: String?) {
        this.sharedPreferences.edit().apply {
            putString(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_EXTRA_DEVICE_INFO, toJson)
        }.apply()
    }

    fun getOtherDeviceInfo(): String? {
        return this.sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO,
            ""
        )
    }

    fun saveOtherDeviceInfo(toJson: String?) {
        this.sharedPreferences.edit().apply {
            putString(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO, toJson)
        }.apply()
    }

    fun saveOtherExtInfoUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferences.edit().apply {
            putString(
                com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO_TIMESTAMP,
                currentStampStr
            )
        }.apply()
    }

    fun getOtherDeviceInfoUpdateTimestamp(): String {
        return this.sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_OTHER_DEVICE_INFO_TIMESTAMP,
            "0"
        )
    }

    fun saveLocationInfoUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferences.edit().apply {
            putString(
                com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_LOCATION_INFO_TIMESTAMP,
                currentStampStr
            )
        }.apply()
    }

    fun getLocationInfoUpdateTimestamp(): String {
        return this.sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_LOCATION_INFO_TIMESTAMP,
            "0"
        )

    }

    fun savePrivateMsgUpdateTimestamp(currentStampStr: String?) {
        this.sharedPreferences.edit().apply {
            putString(
                com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_PRIVATE_MSG_INFO_TIMESTAMP,
                currentStampStr
            )
        }.apply()
    }

    fun getPrivateMessageUpdateTimestamp(): String {
        return this.sharedPreferences.getString(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_PRIVATE_MSG_INFO_TIMESTAMP,
            "0"
        )

    }

    fun generateDataToken() {
        this.sharedPreferences.edit()
            .putString(
                com.sctuopuyi.echo.app.Constants.UserKey.KEY_DATA_TOKEN,
                UUID.randomUUID().toString()
            ).apply()
    }

    fun firstLoad(): Boolean {
        return this.sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_FIRST_RECOMMEND,
            true
        )
    }

    fun saveFirstLoad(status: Boolean) {
        this.sharedPreferences.edit().apply {
            putBoolean(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_FIRST_RECOMMEND, status)
        }.apply()
    }

    fun setUserLowScoreType(lowScore: Boolean) {
        this.sharedPreferences.edit().apply {
            putBoolean(com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_SOCRE_TYPE, lowScore)
        }.apply()
    }

    fun getUserLowScoreType(): Boolean {
        return this.sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.UserKey.KEY_USER_SOCRE_TYPE,
            false
        )
    }

    fun setApproveExpiredTag(tag: Boolean) {
        this.sharedPreferences.edit().apply {
            putBoolean(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_APPROVE_STATUS_TAG, tag)
        }.apply()
    }

    fun getApproveExpiredTag(): Boolean {
        return this.sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_APPROVE_STATUS_TAG,
            false
        )
    }

    fun setMainOutListenTag(tag: Boolean) {
        this.sharedPreferences.edit().apply {
            putBoolean(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_MAIN_OUT_LISTEN_TAG, tag)
        }.apply()
    }

    fun getMainOutListenTag(): Boolean {
        return this.sharedPreferences.getBoolean(
            com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_MAIN_OUT_LISTEN_TAG,
            false
        )
    }

    fun savePhoneNo(str: String) {
        sharedPreferences.edit()
            .putString(com.sctuopuyi.echo.app.Constants.UserKey.KEY_MOBILE_PHONE_NO_HIDDEN, str)
            .apply()
    }

    fun savePasswd(passwd: String) {
        this.sharedPreferences.edit().apply {
            putString(com.sctuopuyi.echo.app.Constants.ExtraInfo.KEY_MAIN_PASSWD, passwd)
        }.apply()
    }

    fun getPasswd(): String {
        return this.sharedPreferences.getString(Constants.ExtraInfo.KEY_MAIN_PASSWD, "") ?: ""
    }
//    fun getRefToken(): String? {
//        return getUserDtoFromLocal()?.refreshToken
//    }

//    fun saveTokenToLocal(newToken: String?) {
//        if (!newToken.isNullOrBlank()) {
//            val userDto = getUserDtoFromLocal()
//            if (userDto != null) {
//                val newUserDto = UserDto(nickName = userDto.nickName,
//                    mobilePhone = userDto.mobilePhone,
//                    id = userDto.id,
//                    gender = userDto.gender,
//                    comment = userDto.comment,
//                    token = userDto.token,
//                    refreshToken = newToken)
//                saveUserDtoToLocal(newUserDto)
//            }
//        }
//    }

    private val sharedPreferences: SharedPreferences
    private val KEY_USER_DTO = "userDtoKey"
    private val TAG_DATA_TOKEN = "dataTokenTag"
    private val TAG_CREATE_DATA_TOKEN = "createDataToken"

    init {
        sharedPreferences = context.getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Context.MODE_PRIVATE
        )
    }
}