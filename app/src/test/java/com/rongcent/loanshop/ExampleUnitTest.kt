package com.sctuopuyi.echo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import kotlin.collections.HashMap

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun hello() {
        val datas = arrayListOf("1", "2", "3")
        val result = datas.asSequence().joinToString(",")
        assertEquals(result, "1,2,3")
    }

    @Test
    fun testArrays() {
        val datas = arrayOf("e", "c", "a", "g")
        val result = datas.sortedArray()
        val content = result.joinToString("")
        assertEquals(content, "aceg")
    }

    @Test
    fun mobileTotal() {
        var versionCount: MutableMap<String, Int> = HashMap()
        var modelCount: MutableMap<String, Int> = HashMap()
        var brandCount: MutableMap<String, Int> = HashMap()
        var memCount: MutableMap<String, Int> = HashMap()
        var storageCount: MutableMap<String, Int> = HashMap()
        val file = File("/Users/haibin/Downloads/mDeviceInfo.json").readLines()
        var vStr = "[${file.joinToString("")}]"
        vStr = vStr.replace(Regex("\t"), "")
        val type = object : TypeToken<List<PhoneInfoItemBean>>() {}.type
        val datas = Gson().fromJson<List<PhoneInfoItemBean>>(vStr, type)
        val it = datas.iterator()
        while (it.hasNext()) {
            val bean = it.next()
            var v = bean.androidVer
            if (versionCount.containsKey(v)) {
                var count = versionCount[v] ?: 0
                count += 1
                versionCount[v] = count
            } else {
                versionCount[v] = 1
            }
            v = bean.deviceModel
            if (modelCount.containsKey(v)) {
                var count = modelCount[v] ?: 0
                count += 1
                modelCount[v] = count
            } else {
                modelCount[v] = 1
            }
            v = bean.phoneBrand
            if (brandCount.containsKey(v)) {
                var count = brandCount[v] ?: 0
                count += 1
                brandCount[v] = count
            } else {
                brandCount[v] = 1
            }
            v = bean.memSize
            if (memCount.containsKey(v)) {
                var count = memCount[v] ?: 0
                count += 1
                memCount[v] = count
            } else {
                memCount[v] = 1
            }
            v = bean.storageSize
            if (storageCount.containsKey(v)) {
                var count = storageCount[v] ?: 0
                count += 1
                storageCount[v] = count
            } else {
                storageCount[v] = 1
            }
        }
        System.out.println("版本")
        versionCount.asSequence().sortedByDescending { it.value }.map {
            if (it.value >= 100)
                System.out.println("${
                "${it.key}||${it.value}"
                }")
        }.toList()
        System.out.println("型号")
        modelCount.asSequence().sortedByDescending { it.value }.map {
            if (it.value >= 100)
                System.out.println("${
                "${it.key}||${it.value}"
                }")
        }.toList()
        System.out.println("厂家")
        brandCount.asSequence().sortedByDescending { it.value }.map {
            if (it.value >= 100)
                System.out.println("${
                "${it.key}||${it.value}"
                }")
        }.toList()
        System.out.println("内存")
        memCount.asSequence().sortedByDescending { it.value }.map {
            if (it.value >= 100)
                System.out.println("${
                "${it.key}||${it.value}"
                }")
        }.toList()
        System.out.println("机身存储")
        storageCount.asSequence().sortedByDescending { it.value }.map {
            if (it.value >= 100)
                System.out.println("${
                "${it.key}||${it.value}"
                }")
        }.toList()
    }
}

//data class PhoneInfoBean(val _id: PhoneInfoItemBean)

data class PhoneInfoItemBean(val _id: String, val androidVer: String, val deviceModel: String, val phoneBrand: String, val memSize: String, val storageSize: String)
