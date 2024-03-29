package dev.synople.glassecho.glass

import android.app.Activity
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity


class NotificationCancelReceiver : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent?.extras
        if (extras != null) {

            if (extras.containsKey("NOTIFICATION_ID")) {
                val notificationId = extras.getInt("NOTIFICATION_ID", -1)

                if (this != null) {
                    NotificationManagerCompat.from(this).cancel(null, notificationId)
                }
            }

        }

        finish()
    }

}