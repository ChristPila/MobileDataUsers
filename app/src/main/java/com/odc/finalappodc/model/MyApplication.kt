package com.odc.finalappodc.model

import android.app.Application
import android.util.Log
import com.odc.finalappodc.DB.MyDataBase

class MyApplication : Application() {
    val db by lazy {
        MyDataBase.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("APPLICATION", "App starting")
    }
}