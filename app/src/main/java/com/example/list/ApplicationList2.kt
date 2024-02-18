package com.example.list

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.list.repository.AppRepository

class ApplicationList2 : Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.getInstance().loadData()

    }

    init {
        instance= this
    }
    companion object {
        private var instance: ApplicationList2? = null

        private var _isAdmin: Boolean = false

        val isAdmin
            get() = _isAdmin

        fun setIsAdmin(value: Boolean) {
            _isAdmin = value
        }

        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun makeToast(text: String) {
            Toast.makeText(applicationContext(), text, Toast.LENGTH_SHORT).show()
        }
    }
}