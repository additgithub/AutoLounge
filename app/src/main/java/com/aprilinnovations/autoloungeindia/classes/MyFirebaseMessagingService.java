package com.aprilinnovations.autoloungeindia.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;

import android.util.Log;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.activity.ChatActivity;
import com.aprilinnovations.autoloungeindia.activity.GetOtpActivity;
import com.aprilinnovations.autoloungeindia.activity.MainActivity;
import com.aprilinnovations.autoloungeindia.activity.NotificationDetails;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Akshay on 16-02-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    private NotificationHelper notificationHelper;
    Bitmap bitmap, icon;

    private UniversalHelper helper;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        //
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        //for oreo notificaation

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Messagedatapayload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "MessageNotificationBody: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        //imageUri will contain URL of the image to be displayed with Notification
        String imageUri = remoteMessage.getData().get("articleImage");
//        Log.d("ArticleImage==>", imageUri);

        String articleTitle = remoteMessage.getData().get("title");
        Log.d("articleTitle==>", articleTitle);
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");
        Log.d("trueOrFalse==> ", TrueOrFlase);

        String articleId = remoteMessage.getData().get("articleId");
        Log.d("articleId==>", articleId);

        String discription = remoteMessage.getData().get("description");
//        Log.d("articleId==>", discription);

        //To get a Bitmap image from the URL received
        bitmap = getBitmapfromUrl(imageUri);

        if (Build.VERSION.SDK_INT >= 28) {
//            Long tsLong = System.currentTimeMillis()/1000;
//            Integer i = (int) (long) tsLong;
//            Log.d("uniqueint",">>"+i);

            notificationHelper = new NotificationHelper(this);
            Log.d("OREO","OREO");
           // Notification.Builder nb = notificationHelper.sendNotificationOreo(articleTitle, articleId, bitmap,i); // code comment
            //notificationHelper.getManager().notify(i, nb.build()); // code comment
            notificationHelper.sendNotificationOreo(discription,articleTitle, bitmap, articleId,TrueOrFlase); // code changed
        }
        else {
            sendNotification(discription,articleTitle, bitmap, articleId, TrueOrFlase);
        }

    }



    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String discription,String articleTitle, Bitmap image, String TrueOrFalse, String articleId) {

        context = this;
        helper = new UniversalHelper(this);
        Intent intent = null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (helper.loadPreferences("userIsLogin").equals("Yes")){
            if (TrueOrFalse.equals("true")){
                intent = new Intent(this, NotificationDetails.class);
                intent.putExtra("AnotherActivity", TrueOrFalse);
                intent.putExtra("notificationID", articleId);
            }
            else if (TrueOrFalse.equals("false")){
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("AnotherActivity", TrueOrFalse);
                intent.putExtra("notificationID", articleId);
            }
            else if (TrueOrFalse.equals("chatActivity")){
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("activity", TrueOrFalse);
                intent.putExtra("userChatId", articleId);
            }

        }
        if (helper.loadPreferences("userIsLogin").equals("No")){
            intent = new Intent(this, GetOtpActivity.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)

                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle(articleTitle)
                .setContentText(articleTitle)
                .setStyle(new Notification.BigPictureStyle()
                .bigPicture(image).setSummaryText(discription))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Long tsLong = System.currentTimeMillis()/1000;
        Integer i = (int) (long) tsLong;
        Log.d("uniqueint",">>"+i);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotificationDetails.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(i, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);

        notificationManager.notify(i /* ID of notification */, notificationBuilder.build());
    }

    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
