package com.montwell.showcase.utilities

import android.content.Context

object SharedPrefUtils {

    private const val SHARED_PREFS_FILE_KEY = "shared_prefs_file_key"

    fun getLong(context: Context, key: String, defaultValue: Long): Long {

        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFS_FILE_KEY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getLong(key, defaultValue)
    }

    fun setLong(context: Context, key: String, value: Long) {

        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFS_FILE_KEY,
            Context.MODE_PRIVATE
        )

        sharedPreferences.edit().putLong(key, value).apply()
    }
}