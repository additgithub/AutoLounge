package com.aprilinnovations.autoloungeindia.classes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.ChatActivity;
import com.aprilinnovations.autoloungeindia.activity.GetOtpActivity;
import com.aprilinnovations.autoloungeindia.activity.MainActivity;
import com.aprilinnovations.autoloungeindia.activity.NotificationDetails;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;


@RequiresApi(api = Build.VERSION_CODES.O)
public class NotificationHelper extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.aprilinnovations.autolounge";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";

    private UniversalHelper helper;
    Context context;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);
    }

    NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


    // below code changed
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotificationOreo(String discription,String articleTitle, Bitmap image,  String articleId,String TrueOrFalse) {
        context = this;
        helper = new UniversalHelper(this);
        Intent intent = null;
        if (helper.loadPreferences("userIsLogin").equals("Yes")){

            Log.d("TrueOrFalse",TrueOrFalse);
            if (TrueOrFalse.equals("true")){
                intent = new Intent(this, NotificationDetails.class);
                intent.putExtra("AnotherActivity", TrueOrFalse);
                intent.putExtra("notificationID", articleId);
            }
            else if (TrueOrFalse.equals("false")){
                intent = new Intent(this, MainActivity.class);
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

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // I would suggest that you use IMPORTANCE_DEFAULT instead of IMPORTANCE_HIGH
            NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID, ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            channel.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/"),
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build());
            //channel.canShowBadge();
            // Did you mean to set the property to enable Show Badge?
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle(articleTitle)
                .setContentText(articleTitle)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image).setSummaryText(discription))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        // Removed .build() since you use it below...no need to build it twice

        // Don't forget to set the ChannelID!!
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationBuilder.setChannelId(ANDROID_CHANNEL_ID);
        }

        notificationManager.notify(ANDROID_CHANNEL_ID, 1, notificationBuilder.build());
    }



    }

