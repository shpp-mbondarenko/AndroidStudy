package ua.mycompany.servisetutorial;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Maxim on 19.02.2016.
 */
public class NotificstionService extends Service{
    public static final int NOTIFICATION_ID = 1;
    MediaPlayer player;
    String TAG = "yay";
    @Nullable
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "We in Service!!!");
        player = MediaPlayer.create(this, R.raw.cat);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"In onStartCommand Function");
        sendNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendNotification() {

        /** Create an intent that will be fired when the user clicks the notification.*/
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /** Use NotificationCompat.Builder to set up our notification.         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         * Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commonly used*/
        builder.setContentTitle("Tea break!!!");
        builder.setContentText("Time to relax your mind!");
        builder.setSubText("Go and make tea for yourself!");
//        builder.setSound(R.raw.cat);
//        builder.setVibrate();

        //start play music
        player.start();
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
