import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

object PaziņojumuParvaldnieks {

    fun abonētTēmu(tēma: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(tēma)
    }

    fun atceltAbonēšanuNoTēmas(tēma: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(tēma)
    }

    fun nosūtītPaziņojumu(nosaukums: String, ziņojums: String, tēma: String) {
        val builder = RemoteMessage.Builder(tēma)
        builder.setMessageId(java.lang.String.valueOf(System.currentTimeMillis()))
            .setMessageType("notification")
            .setNotification(
                RemoteMessage.Notification.Builder(nosaukums, ziņojums).build()
            )
        FirebaseMessaging.getInstance().send(builder.build())