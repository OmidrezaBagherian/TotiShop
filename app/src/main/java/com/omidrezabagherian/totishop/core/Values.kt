package com.omidrezabagherian.totishop.core

import android.content.SharedPreferences

object Values {
    const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
    const val CUSTOMER_KEY = "ck_bcf8c67d33d9a531d8065ef94e3167f51930913f"
    const val CUSTOMER_SECRET = "cs_6ff44b5edda9080d77640880afc58beefbeea132"
    const val DELAY_MS: Long = 2000
    const val PERIOD_MS: Long = 3000
    const val SHARED_PREFERENCES = "TotiApp"
    const val ID_SHARED_PREFERENCES = "ID"
    const val NAME_SHARED_PREFERENCES = "Name"
    const val FAMILY_SHARED_PREFERENCES = "Family"
    const val EMAIL_SHARED_PREFERENCES = "Email"
    const val Address_SHARED_PREFERENCES = "Address"
    const val PASSWORD_SHARED_PREFERENCES = "Password"
    const val ID_ORDER_SHARED_PREFERENCES = "IDOrder"
    const val IS_ORDER_ENABLE_SHARED_PREFERENCE = "IsOrderEnable"
    const val ID_FILTER_SHARED_PREFERENCES = "IDFilter"
    const val ID_LAST_PRODUCT_SHARED_PREFERENCES = "IDLastProduct"
    const val ID_TIME_WORK_SHARED_PREFERENCES = "IDTime"
    const val CHANNEL_ID = "TotiShopNotification"
    const val NOTIFICATION_ID = 0
    const val DATA_NAME = "TimeWorkManager"
    lateinit var mainSharedPreferences: SharedPreferences
}