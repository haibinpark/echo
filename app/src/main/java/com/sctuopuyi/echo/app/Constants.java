package com.sctuopuyi.echo.app;

import android.os.Environment;

import org.jetbrains.annotations.Nullable;


import java.io.File;

/**
 * 全局变量，常量定义类
 * Created on 25/07/2017 16:01.
 */

public interface Constants {

    //PATH
    String PATH_DATA = App.Companion.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = PATH_DATA + "/demo";
    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demo" + File.separator + "MvpTemplate";

    boolean isNeedHttpJson = true;//是否需要打印json格式的http数据
    boolean isNeedTestData = false;//是否需要假数据
    boolean isGoTestData = false;//已有接口，强行走假数据判断

    //    #注意:请填写:距离过期时间多长时间下载license文件,单位:天
    public static final int DAYS_BEFORE_LIC_EXPIRED = 5;
    //    #注意:请填写:json文件地址
    @Nullable
    String TAG = "TAG_DEMO";

    long LOOP_GET_DATA = 10000L;
    long LOOP_DELAY = 500L;


    interface ComponentType {
        Integer COMPONENT_TYPE_SELECT = 1; //选择
        Integer COMPONENT_TYPE_EDIT = 0; //输入
        Integer COMPONENT_TYPE_BLANK = 2; //空格
        Integer COMPONENT_TYPE_ADDRESS = 3; //地址
        Integer COMPONENT_TYPE_CONTACT = 4; //地址
    }

    interface OrderPageType {
        Integer ORDER_ITEM_TYPE_NORMAL = 0;
        Integer ORDER_ITEM_TYPE_BLANK = 1;
        Integer ORDER_ITEM_TYPE_CHANGE_BANK1 = 2;
        Integer ORDER_ITEM_TYPE_CHANGE_BANK2 = 3;
        Integer ORDER_ITEM_TYPE_QUITY = 4; //权益
    }

    interface PageType {
        String PAGE_TYPE_HOME = "homePageType"; //首页
        String PAGE_TYPE_SETTING = "settingPageType"; //设置页面
    }

    //用户信息
    interface UserKey {
        String KEY_LOGIN_STATUS = "loginStatusKey";
        String KEY_ACCOUNT_ID = "accountIdKey";
        String KEY_USER_TOKEN = "userTokenKey";
        String KEY_EMAIL = "emailKey";
        String KEY_HEADER_URL = "headerUrlKey";
        String KEY_MOBILE_PHONE = "mobilePhoneKey";
        String KEY_MOBILE_PHONE_NO_HIDDEN = "mobilePhoneNoHiddenKey";
        String KEY_ACCOUNT_NAME = "accountNameKey";
        String KEY_REAL_NAME = "realNameKey";
        String KEY_IS_AUTHEN_ID = "isAuthenIdKey";
        String KEY_IS_AUTHEN_BANK = "isAuthenBankKey";
        String KEY_SECREKEY = "SecretKeyKey";
        String KEY_DEVICE_TOKEN = "DeviceTokenKey";
        String KEY_DATA_TOKEN = "DataTokenKey";
        String KEY_SEX = "sexKey";
        String KEY_USER_ADDRESS = "user_address";
        String KEY_USER_FIRST_RECOMMEND = "user_address";
        String KEY_USER_SOCRE_TYPE = "user_score_type";
    }

    interface ExtraInfo {
        String KEY_EXTRA_DEVICE_INFO = "extraDeviceInfoKey";
        String KEY_OTHER_DEVICE_INFO = "otherDeviceInfoKey";
        String KEY_DEVICE_INFO = "deviceInfoKey";
        String KEY_DEVICE_NUM = "deviceNumKey";
        String KEY_IS_SIMULATOR = "isSimulatorKey";
        String KEY_PLATFORM = "platformKey";
        String KEY_OTHER_DEVICE_INFO_TIMESTAMP = "otherDeviceInfoTimestampKey";
        String KEY_LOCATION_INFO_TIMESTAMP = "locationInfoTimestampKey";
        String KEY_PRIVATE_MSG_INFO_TIMESTAMP = "privateMsgInfoTimestampKey";
        String KEY_APPROVE_STATUS_TAG = "approveStatusTagKey";
        String KEY_MAIN_OUT_LISTEN_TAG = "mainOutListenTagKey";
        String KEY_MAIN_PASSWD = "mainPasswdKey";

    }

    //活体识别
    String SINGLEIMG = "singleImg";
    String MULTIIMG = "multiImg";
    String VIDEO = "video";
    String FULLVIDEO = "fullVideo";
    String BLINK = "BLINK";
    String NOD = "NOD";
    String MOUTH = "MOUTH";
    String YAW = "YAW";
    String EASY = "easy";
    String NORMAL = "normal";
    String HARD = "hard";
    String HELL = "hell";

    interface OSS {
        //        String ENDPOINT = BuildConfig.ALIYUN_ENDPOINT;
//        String BUCKET_NAME = BuildConfig.ALIYUN_BUCKET_NAME;
        int DOWNLOAD_SUC = 1;
        int DOWNLOAD_Fail = 2;
        int UPLOAD_SUC = 3;
        int UPLOAD_Fail = 4;
        int UPLOAD_PROGRESS = 5;
        int LIST_SUC = 6;
        int HEAD_SUC = 7;
        int RESUMABLE_SUC = 8;
        int SIGN_SUC = 9;
        int BUCKET_SUC = 10;
        int GET_STS_SUC = 11;
        int MULTIPART_SUC = 12;
        int STS_TOKEN_SUC = 13;
        int FAIL = 9999;
    }

    interface Camera {
        float HeightPer = 0.6f;
        float WIDTH_DIVIDE_HEIGHT = 0.6571f;
        int COMPRESS_SIZE = 200;
        int COMPRESS_IMAGE_SIZE = 512;
    }

    interface ImagePicker {
        Integer IMAGE_PICKER = 1; //图片选择
        Integer MAX_PICTURES_NUMBER = 10; //最大选择图片数量
    }

    interface SendMsgCode {
        String register = "0";
        String reset_password = "1";
        String tibi = "2";
        String find_password = "3";
        String mortgage_register = "4";
    }

    interface OrderListFraStatus {
        String order_bucang = "0";
        String order_daibucha = "1";
        String order_daifangkuan = "2";
        String order_daihuankuan = "3";
        String order_daiqueren = "4";
        String order_daituikuan = "5";
        String order_diyazhong = "6";
        String order_fangkuanzhong = "7";
        String order_pingcang = "8";
        String order_wancheng = "9";
        String order_yiquxiao = "10";
    }

    interface FinanceRecordStatus {
        String caiwu_tibi = "0";
        String caiwu_chong = "1";
        String caiwu_diya = "2";
        String caiwu_huankuan = "3";
        String caiwu_quxiao = "4";
        String caiwu_jujue = "5";
        String caiwu_bucang = "6";
    }

    /**
     * 充币记录、提币记录、充币详情、提币详情状态值
     */
    interface ProcessCoinHistoryStatus {
        int daiheshi = 0;
        int weidaozhang = 1;
        int yidaozhang = 2;
    }


}
