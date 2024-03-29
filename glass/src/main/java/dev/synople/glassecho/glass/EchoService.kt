package dev.synople.glassecho.glass

 import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import android.os.PowerManager
import android.util.Log
import androidx.annotation.Nullable
import com.google.android.glass.widget.CardBuilder
import com.google.gson.Gson
import dev.synople.glassecho.common.glassEchoUUID
import dev.synople.glassecho.common.models.EchoNotification
import dev.synople.glassecho.common.models.EchoNotificationAction
import dev.synople.glassecho.glass.utils.Constants
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread
import kotlin.random.Random


private val TAG = EchoService::class.java.simpleName

class EchoService : Service() {

    private lateinit var bluetoothDevice: BluetoothDevice
    private var phoneConnection: ConnectedThread? = null

    private val notifications: HashMap<String, Int> = HashMap<String, Int>()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (phoneConnection == null || phoneConnection?.isConnected() == false) {
            intent?.getParcelableExtra<BluetoothDevice>(Constants.EXTRA_BLUETOOTH_DEVICE)?.let {
                bluetoothDevice = it
                phoneConnection = ConnectedThread(it).apply {
                    start()
                }
            } ?: run {
                Log.v(TAG, "No BluetoothDevice found in extras")
            }
        } else {
            intent?.getParcelableExtra<EchoNotificationAction>(Constants.EXTRA_NOTIFICATION_ACTION)
                ?.let {
                    phoneConnection?.write(it)
                } ?: run {
                phoneConnection?.broadcastDeviceStatus()
            }
        }

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        phoneConnection?.cancel()
    }

    @Nullable
    fun sendGlassNotification(echoNotification: EchoNotification) {

        val notificationManager: NotificationManager  =
            getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        if(echoNotification.isRemoved){
            val notificationId: Int? = notifications[echoNotification.id]

            if(notificationId != null){
                val cancel = Intent(this, NotificationCancelReceiver::class.java)
                cancel.putExtra("NOTIFICATION_ID", notificationId)
                val cancelP = PendingIntent.getActivity(this, notificationId, cancel, PendingIntent.FLAG_CANCEL_CURRENT)
                cancelP.send()
                notifications.remove(echoNotification.id)
            }
            return
        }

        var notification: Notification? = null

        val card: CardBuilder
        val notificationId: Int

        //Trying to handle
        if(echoNotification.id.contains("com.google.android.apps.maps")){
            if(echoNotification.isRemoved){
                val cancel = Intent(this, NotificationCancelReceiver::class.java)
                cancel.putExtra("NOTIFICATION_ID", 69)
                val cancelP = PendingIntent.getActivity(this, 69, cancel, PendingIntent.FLAG_CANCEL_CURRENT)
                cancelP.send()
                return
            }
            card = CardBuilder(
                this,
                CardBuilder.Layout.COLUMNS
            )
                .setText(echoNotification.title)
                .setFootnote(echoNotification.text)
                .setIcon(echoNotification.getLargeIconBitmap())

            notificationId = 69
        }
        else {

            var text = echoNotification.text
            if(echoNotification.id.contains("com.google.android.calendar"))
                text = echoNotification.title


            val timestamp: CardBuilder = CardBuilder(
                this,
                CardBuilder.Layout.TEXT
            )
                .setText(text)
                .setFootnote(echoNotification.appName)
                .setAttributionIcon(echoNotification.getAppIconBitmap())

            card = timestamp.showStackIndicator(false)

            notificationId = Random.nextInt()
        }


        val gson = Gson()

        val cancel = Intent(this, MainActivity::class.java)
        cancel.putExtra("NOTIFICATION_ID", notificationId)
        val echoNotificationAction : EchoNotificationAction = EchoNotificationAction(echoNotification.id, true, "test")
        //val json = gson.toJson(echoNotificationAction)
        cancel.putExtra(Constants.EXTRA_NOTIFICATION_ACTION, echoNotificationAction as Parcelable)
        val cancelP = PendingIntent.getActivity(this, notificationId, cancel, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = Notification.Builder(this)
            .setLargeIcon(echoNotification.getLargeIconBitmap())
            .setTicker(echoNotification.appName)
            .setContent(card.getRemoteViews())
            //.setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_delete, "Erledigt",
                cancelP)


        for (test in echoNotification.actions){

            val echoNotificationAction : EchoNotificationAction = EchoNotificationAction(echoNotification.id, false, test)
            val json : String = gson.toJson(echoNotificationAction)
            val intent = Intent(this, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("ReplyJson", json)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            builder.addAction(R.drawable.ic_check, test,
                pendingIntent);
        }

        notification = builder.build()

        notificationManager.notify(notificationId, notification);

        notifications.put(echoNotification.id, notificationId)


    }

    private fun handleNotification(echoNotification: EchoNotification) {
        (applicationContext as EchoApplication).getRepository()
            .handleNotification(echoNotification)

        if (!echoNotification.isRemoved) {
            val audio = applicationContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audio.playSoundEffect(Constants.GLASS_SOUND_TAP)
        }

        if (echoNotification.isWakeScreen or true) {
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager

            @Suppress("DEPRECATION")
            if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && !powerManager.isInteractive)
                || (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT && !powerManager.isScreenOn)
            ) {
                val wakelock = powerManager.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "glassecho:wakelocktag"
                )
                wakelock.acquire(3000L)
                //val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
                //mainActivityIntent.flags =
                //    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //startActivity(mainActivityIntent)
            }
        }

        sendGlassNotification(echoNotification)
    }

    inner class ConnectedThread(private val bluetoothDevice: BluetoothDevice) : Thread() {
        private val TAG = "ConnectedThread"
        private var isRunning = AtomicBoolean(true)
        private var bluetoothSocket: BluetoothSocket? = null

        override fun run() {
            var isConnected = init()

            while (isConnected && isRunning.get()) {
                try {
                    val objectInputStream = ObjectInputStream(bluetoothSocket?.inputStream)
                    val echoMessage = objectInputStream.readObject()

                    if (echoMessage is EchoNotification) {
                        handleNotification(echoMessage)
                    } else {
                        Log.v(
                            TAG,
                            "Received unknown message of type ${echoMessage.javaClass.simpleName}: $echoMessage"
                        )
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Potential socket disconnect. Attempting to reconnect", e)
                    // Attempt to reconnect
                    isConnected = init()
                }
            }

            cancel()
        }

        fun write(message: Serializable) {
            thread {
                if (isRunning.get()) {
                    try {
                        Log.v(TAG, "Writing: $message")
                        bluetoothSocket?.let {
                            val objectOutputStream = ObjectOutputStream(it.outputStream)
                            objectOutputStream.writeObject(message)
                        }
                    } catch (e: IOException) {
                        Log.e(
                            TAG,
                            "Write (bluetoothSocket isConnected: ${bluetoothSocket?.isConnected}",
                            e
                        )
                        bluetoothSocket = establishConnection()
                    }
                }
            }
        }

        private fun init(): Boolean {
            val isConnected: Boolean = establishConnection()?.let {
                bluetoothSocket = it
                bluetoothSocket?.isConnected
            } ?: run {
                false
            }

            broadcastDeviceStatus()

            return isConnected
        }

        private fun establishConnection(): BluetoothSocket? {
            try {
                val socket = bluetoothDevice.createRfcommSocketToServiceRecord(glassEchoUUID)
                socket.connect()
                Log.v(
                    TAG,
                    "Connection status to ${socket.remoteDevice.name}: ${socket.isConnected}"
                )
                return socket
            } catch (e: IOException) {
                Log.v(TAG, "Failed to accept", e)
            }

            return null
        }

        fun cancel() {
            try {
                bluetoothSocket?.close()
                isRunning.set(false)
                Log.v(TAG, "Cancelling ConnectedThread")
            } catch (e: IOException) {
                Log.e(TAG, "cancel", e)
            } catch (e: RuntimeException) {
                Log.e(TAG, "cancel", e)
            }
        }

        fun isConnected() = isRunning.get() && bluetoothSocket?.isConnected == true

        fun broadcastDeviceStatus() {
            Intent().also { statusIntent ->
                Log.v(TAG, "broadcastDeviceStatus: ${bluetoothSocket?.isConnected}")
                statusIntent.action = Constants.INTENT_FILTER_DEVICE_CONNECT_STATUS
                statusIntent.putExtra(
                    Constants.EXTRA_DEVICE_IS_CONNECTED,
                    bluetoothSocket?.isConnected
                )
                if (bluetoothSocket?.isConnected == true) {
                    statusIntent.putExtra(
                        Constants.EXTRA_DEVICE_NAME,
                        bluetoothSocket?.remoteDevice?.name
                    )
                    statusIntent.putExtra(
                        Constants.EXTRA_DEVICE_ADDRESS,
                        bluetoothSocket?.remoteDevice?.address
                    )
                }
                sendBroadcast(statusIntent)
            }
        }
    }
}
