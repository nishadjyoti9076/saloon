package com.shashank.platform.saloon.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.ui.AcceptNotificationActivity
import com.shashank.platform.saloon.ui.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var user_id = "0"
        var date = "0"
        var hal_id = "0"
        var M_view = "0"


        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            user_id = remoteMessage.data["user_id"] ?: "0"
            date = remoteMessage.data["date"] ?: "0"
            hal_id = remoteMessage.data["hal_id"] ?: "0"
            M_view = remoteMessage.data["M_view"] ?: "0"
        }

        val click_action = remoteMessage.notification?.clickAction
        // Calling method to generate notification
        sendNotification(
            remoteMessage.notification?.body ?: "",
            remoteMessage.notification?.title ?: "",
            remoteMessage.notification?.sound ?: "",
            remoteMessage.notification?.clickAction?:/*.split("="[0])?.get(1)?.split("&"[0])?.get(0) ?*/ "",
            user_id,
            date,
            hal_id,
            M_view,
            click_action ?: ""
        )

    }

    private fun sendNotification(
        messageBody: String,
        messageTitle: String,
        sound: String,
        channelId: String,
        user_id: String,
        date: String,
        hal_id: String,
        M_view: String,
        click_action: String
    ) {
        val intent = Intent(this, AcceptNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("user_id", user_id)
        intent.putExtra("date", date)
        intent.putExtra("hal_id", hal_id)
        intent.putExtra("M_view", M_view)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val confirmPendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val cancelPendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri: Uri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_login)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.baseline_done_24,"Accept",confirmPendingIntent)
                .addAction(R.drawable.baseline_cancel_24,"Cancel",cancelPendingIntent)
                .build()

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder)

    }
}






