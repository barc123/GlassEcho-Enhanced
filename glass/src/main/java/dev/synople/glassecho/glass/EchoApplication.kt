package dev.synople.glassecho.glass

import androidx.multidex.MultiDexApplication

class EchoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getRepository() = NotificationRepository.getInstance()
}