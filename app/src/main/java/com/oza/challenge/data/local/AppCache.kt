package com.oza.challenge.data.local

import android.content.Context
import android.content.SharedPreferences
import com.oza.challenge.App
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Class used to cache data using SharedPreferences
 */
@Singleton
class AppCache @Inject constructor() {

    private var sharedPreferences: SharedPreferences =
        App.getAppContext().getSharedPreferences(CACHE_KEY, Context.MODE_PRIVATE)

    fun saveToken(token: String) =
        sharedPreferences.edit().putString(TOKEN, token).apply()

    val token: String?
        get() {
            return sharedPreferences.getString(TOKEN, null)
        }

    companion object {
        const val CACHE_KEY = "cache"
        const val TOKEN = "TOKEN"
    }

}