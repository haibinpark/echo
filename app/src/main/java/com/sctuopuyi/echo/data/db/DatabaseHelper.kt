package com.sctuopuyi.echo.data.db

import android.content.Context

//import org.jetbrains.anko.db.*


//测试
data class Customer(val id: Int, val name: String) {
    companion object {
        val COLUMN_ID: String
            get() = "id"
        val COLUMN_NAME = "name"
        val TABLE_NAME = "customer"
    }
}


data class Message(val id: Int,
                   val messageId: String,
                   val orderId: String,
                   val content: String,
                   val dateTime: String,
                   val accountId: String,
                   val readStatus: Boolean) {
    companion object {
        val COLUMN_ID: String
            get() = "id"
        val COLUMN_MESSAGEID = "message_id"
        val COLUMN_ORDERID = "order_id"
        val COLUMN_CONTENT = "content"
        val COLUMN_DATETIME = "date_time"
        val COLUMN_ACCOUNTID = "account_id"
        val COLUMN_READSTATUS = "read_status"
        val TABLE_NAME = "message"
    }
}


class DatabaseHelper(context: Context) {
//    private val database = DatabaseUtil.getInstance(context)

//    fun save(customer: Customer) {
//        database.use {
//            this.insert(Customer.TABLE_NAME, Customer.COLUMN_NAME to customer.name)
//        }
//    }
//
//
//    fun getAll(): List<Customer>? {
//        var customers: List<Customer>? = null
//        database.use {
//            this.select(Customer.TABLE_NAME).columns(Customer.COLUMN_ID, Customer.COLUMN_NAME).exec {
//                customers = this.parseList(object : RowParser<Customer> {
//                    override fun parseRow(columns: Array<Any?>): Customer {
//                        return Customer((columns[0] as Long).toInt(), columns[1] as String)
//                    }
//                })
//            }
//        }
//        return customers
//    }
//
//
//    /**
//     * 保存消息数据
//     */
//    fun saveMessages(messages: List<Message>): Boolean {
//        var result: Boolean = true
//        database.use {
//            this.transaction {
//                for (message: Message in messages) {
//                    if (getMessagesByAccountId(message.accountId) == null) {
//                        if (this.insert(
//                                Message.TABLE_NAME,
//                                        Message.COLUMN_MESSAGEID to message.messageId,
//                                        Message.COLUMN_ORDERID to message.orderId,
//                                        Message.COLUMN_CONTENT to message.content,
//                                        Message.COLUMN_DATETIME to message.dateTime,
//                                        Message.COLUMN_READSTATUS to if (message.readStatus) 1 else 0,
//                                        Message.COLUMN_ACCOUNTID to message.accountId) < 0) {
//                            result = false
//                            break
//                        }
//                    }
//                }
//            }
//        }
//        return result
//    }
//
//
//    /**
//     * 根据消息id获取消息对象。
//     */
//    fun messageByMessageId(messageId: String): Message? {
//        var message: Message? = null
//        database.use {
//            this.select(Message.TABLE_NAME)
//                    .columns(
//                        Message.COLUMN_ID,
//                        Message.COLUMN_MESSAGEID,
//                        Message.COLUMN_ORDERID,
//                        Message.COLUMN_CONTENT,
//                        Message.COLUMN_DATETIME,
//                        Message.COLUMN_ACCOUNTID,
//                        Message.COLUMN_READSTATUS
//                    )
//                    .whereArgs("{key0} = {val0}",
//                            "key0" to Message.COLUMN_MESSAGEID,
//                            "val0" to messageId)
//                    .exec {
//                        message = this.parseSingle(object : RowParser<Message> {
//                            override fun parseRow(columns: Array<Any?>): Message {
//                                return Message((columns[0] as Long).toInt(),
//                                        columns[1] as String,
//                                        columns[2] as String,
//                                        columns[3] as String,
//                                        columns[4] as String,
//                                        columns[5] as String,
//                                        columns[6] as Boolean)
//                            }
//
//                        })
//                    }
//        }
//        return message
//    }
//
//
//    /**
//     * 根据accountId查询消息列表
//     */
//    fun getMessagesByAccountId(accountId: String): List<Message>? {
//        var messages: List<Message>? = null
//        database.use {
//            this.select(Message.TABLE_NAME)
//                    .columns(
//                        Message.COLUMN_ID,
//                        Message.COLUMN_MESSAGEID,
//                        Message.COLUMN_ORDERID,
//                        Message.COLUMN_CONTENT,
//                        Message.COLUMN_DATETIME,
//                        Message.COLUMN_ACCOUNTID,
//                        Message.COLUMN_READSTATUS
//                    )
//                    .whereArgs("{key0} = {val0}",
//                            "key0" to Message.COLUMN_ACCOUNTID,
//                            "val0" to accountId)
//                    .exec {
//                        messages = this.parseList(object : RowParser<Message> {
//                            override fun parseRow(columns: Array<Any?>): Message {
//                                return Message(
//                                        (columns[0] as Long).toInt(),
//                                        columns[1] as String,
//                                        columns[2] as String,
//                                        columns[3] as String,
//                                        columns[4] as String,
//                                        columns[5] as String,
//                                        columns[6] as Boolean
//                                )
//                            }
//                        })
//                    }
//        }
//        return messages
//    }
//
//
//    /**
//     * 移除该账号的消息
//     */
//    fun removeMessagesByAccountId(accountId: String): Boolean {
//        var result = true
//        database.use {
//            if (this.delete(
//                    Message.TABLE_NAME, "{key0} = {val0}",
//                            "key0" to Message.COLUMN_ACCOUNTID,
//                            "val0" to accountId) < 0) {
//                result = false
//            }
//        }
//        return result
//    }
//
//
//    /**
//     * 更新消息阅读状态
//     */
//    fun updateMessageStatus(status: Boolean, messageId: String, accountId: String): Boolean {
//        var result = true
//        database.use {
//            if (this.update(
//                    Message.TABLE_NAME,
//                            Message.COLUMN_READSTATUS to if (status) 1 else 0)
//                            .whereArgs(
//                                    "{key0} = {val0} and {key1} = {val1}",
//                                    "key0" to Message.COLUMN_MESSAGEID,
//                                    "val0" to messageId,
//                                    "key1" to Message.COLUMN_ACCOUNTID,
//                                    "val1" to accountId
//                            ).exec() < 0) {
//                result = false
//            }
//        }
//        return result
//    }
//
//}
//
//
//class DatabaseUtil(ctx: Context) : ManagedSQLiteOpenHelper(ctx, BuildConfig.APPLICATION_ID, null, 1) {
//    companion object {
//        private var instance: DatabaseUtil? = null
//
//        @Synchronized
//        fun getInstance(ctx: Context): DatabaseUtil {
//            if (instance == null) {
//                instance = DatabaseUtil(ctx.applicationContext)
//            }
//            return instance!!
//        }
//    }
//
//
//    override fun onCreate(p0: SQLiteDatabase?) {
//        //创建用户表
//        p0?.createTable("Customer", true,
//                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//                "name" to TEXT)
//
//        //创建消息表
//        p0?.createTable(
//            Message.TABLE_NAME, true,
//                Message.COLUMN_ID to INTEGER + PRIMARY_KEY + UNIQUE,
//                Message.COLUMN_MESSAGEID to TEXT,
//                Message.COLUMN_ORDERID to TEXT,
//                Message.COLUMN_CONTENT to TEXT,
//                Message.COLUMN_DATETIME to TEXT,
//                Message.COLUMN_ACCOUNTID to TEXT,
//                Message.COLUMN_READSTATUS to INTEGER)
//
//    }
//
//    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        //更新数据库
//    }

    // Access property for Context
//    val Context.database: DatabaseUtil
//        get() = getInstance(applicationContext)
}