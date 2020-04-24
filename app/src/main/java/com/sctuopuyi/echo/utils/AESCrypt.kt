package com.sctuopuyi.echo.utils

import android.util.Base64
import android.util.Base64.DEFAULT
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESCrypt {
    //加密
    fun encrypt(inpute: String, passworld: String = "pwdkey"): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过秘钥工厂生产秘钥
        val keySpec: SecretKeySpec = SecretKeySpec(passworld.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        //加密、解密
        val encrypt = cipher.doFinal(inpute.toByteArray())
        return Base64.encodeToString(encrypt, DEFAULT)
    }

    //解密
    fun decrypt(inpute: String, passworld: String = "pwdkey"): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过秘钥工厂生产秘钥
        val keySpec: SecretKeySpec = SecretKeySpec(passworld.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        //加密、解密
        val encrypt = cipher.doFinal(Base64.decode(inpute, DEFAULT))
        return String(encrypt)
    }
}