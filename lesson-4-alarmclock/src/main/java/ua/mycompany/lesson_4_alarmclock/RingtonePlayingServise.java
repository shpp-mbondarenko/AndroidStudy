package ua.mycompany.lesson_4_alarmclock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Maxim on 07.02.2016.
 */
public class RingtonePlayingServise extends Service {
    MediaPlayer player;
    boolean isRunning;
    int startId;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Local service", "received start id " + startId + ":" + intent);

        //fetch extra strings from the intent
        String status = intent.getExtras().getString("extra");

        Log.e("Ringtone state: extra is ", status);



        assert status != null;
        switch (status) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        //if else statements
        //if there is no music playing, and user pressed "alarm on"
        //music should starm playing
        if (!this.isRunning && startId == 1) {
            Log.e("there is no music", " and you want to start");
            //create instance of media player
            player = MediaPlayer.create(this, R.raw.ex);
            player.start();

            this.isRunning = true;
            this.startId = 0;

            //notification
            //set up notification service
            NotificationManager simpNotifications = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            //set up an intent that goes to the MainActivity
            Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            //set up Pending intent
            PendingIntent pendingMainActivity = PendingIntent.getActivity(this, 0,
                    intentMainActivity,0);
            //make the notification parameters
            Notification notification;
            notification = new Notification.Builder(this)
                    .setContentTitle("An Alarm is going")
                    .setContentText("Click me!")
                    .setContentIntent(pendingMainActivity)
                    .setAutoCancel(true)
                    .build();
            simpNotifications.notify(1, notification);


        }
        //if there is music playing, and the user pressed "alarm off"
        //music should stop
        else if (this.isRunning && startId == 0) {
            Log.e("there is  music", " and you want to end");
            //stop ringtone
            player.stop();
            player.reset();

            this.isRunning = false;
            this.startId = 0;
    }
    //these are if the user presses random buttons
        //just do bug-proof the app
        //if there is no music playing AND the user pressed "alarm off"
        //do nothing
        else if (!this.isRunning && startId == 0) {
            Log.e("there is no music", " and you want to end");
            this.isRunning = false;
            this.startId = 0;
        }
        //if there is music playing and user press "alarm on"
        //do nothing
        else if (this.isRunning && startId == 1) {
            Log.e("there is  music", " and you want to start");
            this.isRunning = true;
            this.startId = 1;
        }
        //catch odd event
        else {
            Log.e("else", " somehow you reached here");
        }


        return START_NOT_STICKY;
    }
    public void onDestroy(){
    Log.e("on destroy","TADA");
        super.onDestroy();
        this.isRunning = false;
    }
}
