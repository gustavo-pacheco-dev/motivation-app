package com.example.motivationapp.infra

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val security: SharedPreferences =
        context.getSharedPreferences("Motivation:username", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        security.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return security.getString(key, "") ?: ""
    }
}