package com.example.list

import android.app.Application
import android.content.Context
import com.example.list.repository.AppRepository

class ApplicationList1:  Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.getInstance().loadData()

    }

    init {
        instance= this
    }
    companion object {
        private var instance: Application?=null
        val context
            get()=applicationContext()


        private fun applicationContext(): Context {
            return instance!!.applicationContext

        }
    }



}