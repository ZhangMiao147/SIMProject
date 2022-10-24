package com.zhangmiao.simproject

import android.app.Application
import android.content.Context

class SIMApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }

}