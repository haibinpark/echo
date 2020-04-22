# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 萤石混淆设置 开始
-dontwarn com.ezviz.player.**
-keep class com.ezviz.player.** { *;}

-dontwarn com.ezviz.statistics.**
-keep class com.ezviz.statistics.** { *;}

-dontwarn com.ezviz.stream.**
-keep class com.ezviz.stream.** { *;}

-dontwarn com.hik.**
-keep class com.hik.** { *;}

-dontwarn com.hikvision.**
-keep class com.hikvision.** { *;}

-dontwarn com.videogo.**
-keep class com.videogo.** { *;}

-dontwarn com.videogo.**
-keep class org.MediaPlayer.PlayM4.** { *;}

#Gson混淆配置
-keepattributes Annotation
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.*
-keep class com.google.gson.stream.* { *; }

# banner 的混淆代码
-keep class com.youth.banner.** {*;}

# EASEMOB
 -keep class com.hyphenate.** {*;}
 -dontwarn  com.hyphenate.**

#qq sdk
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

#pgy
#-libraryjars libs/pgyer_sdk_x.x.jar
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }

# oss
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

# 微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
# -optimizationpasses 5s

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*

#保留内部类，异常，签名
-keepattributes Exceptions,InnerClasses

# 避免混淆泛型
-keepattributes Signature

#保留方法参数名
-keepparameternames

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
-keep class androidx.** {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# for Retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# for okhttp
-dontwarn okhttp3.**
#-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}

# for okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

## for litepal
#-keep class org.litepal.** {*;}
#-keep class * extends org.litepal.crud.DataSupport {*;}
#-dontwarn javax.annotation.**
#-dontwarn javax.inject.**

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
#-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# for AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

# Dagger2
-dontwarn com.google.errorprone.annotations.*

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# 微信支付
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

# 支付宝钱包
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

# easeui
-keep class com.hyphenate.easeui.** {*;}
-keep class com.hianalytics.** {*;}
-keep class com.superrtc.** {*;}
-dontwarn com.hianalytics.**

# zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }

# 环信
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#2.0.9后的不需要加下面这个keep
-keep class org.xbill.DNS.** {*;}
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}
#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

# shareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

# banner 的混淆代码
-keep class com.youth.banner.** {*;}
-dontwarn com.youth.**

# fragmentation
-keep class me.yokeyword.** {*;}
-dontwarn me.yokeyword.**

# GSYVideoPlayer
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
-keep class com.shuyu.gsyvideoplayer.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.**

# QQ互联
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-dontwarn com.tencent.**

-keep class io.github.xiong_it.easypay.pay.PrePayInfo {*;}
-keep class cn.qqtheme.framework.entity.** {*;}

-keep class com.huawei.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.google.protobuf.micro.** {*;}
-dontwarn org.apache.**
-dontwarn com.huawei.**
-dontwarn dagger.**
-dontwarn android.**

# 删除系统日志语句
-assumenosideeffects class android.util.Log {
     public static boolean isLoggable(java.lang.String, int);
     public static int v(...);
     public static int i(...);
     public static int w(...);
     public static int d(...);
     public static int e(...);
}

-assumenosideeffects class com.orhanobut.logger.Logger {
     public static *** v(...);
     public static *** i(...);
     public static *** w(...);
     public static *** d(...);
     public static *** e(...);
}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

#umeng
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#百度
-keep class com.baidu.ocr.sdk.**{*;}
-dontwarn com.baidu.ocr.**
# 小米
-keep class org.greenrobot.eventbus.** {*;}
-keep class com.xiaomi.*** {*;}
-keep class com.wali.*** {*;}
-keep class com.google.zxing.** {*;}
-keep class com.tencent.*** {*;}
-keep class com.umeng.***{*;}
-keep class miui.net.***{*;}

-keep class com.ta.** {*;}
-keep class com.ut.** {*;}
-keep class rx.** {*;}
-keep class com.google.gson.examples.android.model.** { *; }
-keep class uk.co.** {*;}
-keepattributes Signature,InnerClasses,*Annotation*,...

# 应用更新
-keep class com.chejin360.updatelibrary.httpbean.BaseHttpResponse* {*;}
-keep class com.chejin360.updatelibrary.httpbean.CheckUpdateRequest* {*;}
-keep class com.chejin360.updatelibrary.httpbean.CheckUpdateResponse* {*;}

############################## 和包名有关的---开始

#保留使用js交互的类中所有注入接口（将所有web有关的都放入一个包，直接保留整个包即可）
-keep class com.sctuopuyi.dsport.ui.web..** {*;}
#如果交互中有传某个bean的json格式，bean也不能混淆
#-keepclassmembers class com.chejin360.digitalcoins.ui.web$UserInfoForWeb{*;}
#保留JavascriptInterface中的方法
-keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}
#保留webViewClient中的配置
-keepclassmembers class * extends android.webkit.webViewClient {*;}
#保留annotation， 例如 @JavascriptInterface 等 annotation
-keepattributes *Annotation*
#保留跟 javascript相关的属性
-keepattributes *JavascriptInterface*
-keep public class com.sctuopuyi.dsport.pd.R$*{
public static final int *;
}

# 删除自定义的日志提示类提示语句
-assumenosideeffects class com.sctuopuyi.dsport.utils.LogUtil {
     public static *** v(...);
     public static *** i(...);
     public static *** w(...);
     public static *** d(...);
     public static *** e(...);
}


# 活体检测
-keep class com.sensetime.** { *; }

# http Bean
-keep class com.sctuopuyi.echo.data.http.request.** {*;}
-keep class com.sctuopuyi.echo.data.http.response.** {*;}
-dontwarn com.sctuopuyi.echo.data.http.request.**
-dontwarn com.sctuopuyi.echo.data.http.response.**
-dontwarn com.sctuopuyi.echo.**

############################## 和包名有关的---结束

# baidu location
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

# 新颜
-keep class com.xinyan.**{*;}