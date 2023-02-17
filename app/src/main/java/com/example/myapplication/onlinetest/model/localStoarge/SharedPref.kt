package com.example.myapplication.onlinetest.model.localStoarge

import android.content.Context
import java.util.concurrent.TimeUnit

object SharedPref {
    private const val APP_PREF = "AppSharePref"
    private const val Key_Time = "KeyTime"
    private fun default(context: Context) = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

    fun put(context: Context, pair: Pair<String, Any>) {
        val pref = default(context)
        val editor = pref.edit()
        val key = pair.first
        val value = pair.second

        editor.apply {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                else -> error("Only primitive types can be stored in SharedPreferences")
            }
            apply()
        }
    }

    fun getJsonString(context: Context, key: String): String? {
        return default(context).getString(key, null)
    }

    fun saveCurrentTime(context: Context) {
        put(
            context, Pair<String, Any>(Key_Time, System.currentTimeMillis())
        )
    }

    fun isOver30Min(context: Context):Boolean {
        val lastRecordTime = default(context).getLong(Key_Time, System.currentTimeMillis())
        val elapsedTime = System.currentTimeMillis() - lastRecordTime
        return TimeUnit.MILLISECONDS.toSeconds(elapsedTime) >= 30 * 60
    }

}
