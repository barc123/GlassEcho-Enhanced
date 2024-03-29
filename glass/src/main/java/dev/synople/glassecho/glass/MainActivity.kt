package dev.synople.glassecho.glass

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import androidx.fragment.app.FragmentActivity
import dev.synople.glassecho.common.models.EchoNotificationAction
import dev.synople.glassecho.glass.utils.Constants
import dev.synople.glassecho.glass.utils.GlassGesture
import dev.synople.glassecho.glass.utils.GlassGestureDetector
import org.greenrobot.eventbus.EventBus


private val TAG = MainActivity::class.java.simpleName

class MainActivity : FragmentActivity() {

    private lateinit var gestureDetector: GlassGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector =
            GlassGestureDetector(this, object : GlassGestureDetector.OnGestureListener {
                override fun onGesture(gesture: GlassGestureDetector.Gesture?): Boolean {
                    val isHandled = when (gesture) {
                        GlassGestureDetector.Gesture.SWIPE_FORWARD -> true
                        GlassGestureDetector.Gesture.SWIPE_BACKWARD -> true
                        GlassGestureDetector.Gesture.SWIPE_UP -> true
                        GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_FORWARD -> true
                        GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_BACKWARD -> true
                        GlassGestureDetector.Gesture.TAP -> true
                        else -> false
                    }

                    if (isHandled) {
                        EventBus.getDefault().post(GlassGesture(gesture!!))
                    }

                    return isHandled
                }
            })

        val notificationAction = intent?.getParcelableExtra<EchoNotificationAction>(Constants.EXTRA_NOTIFICATION_ACTION)
        if(notificationAction != null){
            write(notificationAction as Parcelable)
            finish()
        }
    }

    // For Glass XE
    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        event?.let {
            return gestureDetector.onTouchEvent(it)
        }
        return super.onGenericMotionEvent(event)
    }

    // For Glass EE 2
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            return gestureDetector.onTouchEvent(event)
        }
        return super.dispatchTouchEvent(event)
    }

    private fun write(message: Parcelable) {
        startService(Intent(this, EchoService::class.java).apply {
            putExtra(
                Constants.EXTRA_NOTIFICATION_ACTION, message
            )
        })
    }
}