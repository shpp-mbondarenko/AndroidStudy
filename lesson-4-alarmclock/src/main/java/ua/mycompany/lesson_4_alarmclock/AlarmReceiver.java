package ua.mycompany.lesson_4_alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Maxim on 07.02.2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in receiver","YAY");

        //fetch extra strings from the intent
        String getStringFromIntentMainActivity = intent.getExtras().getString("extra");
        Log.e("What is the key?", getStringFromIntentMainActivity);

        //create intent to ringtone service
        Intent intentToRingtoneService = new Intent(context, RingtonePlayingServise.class);
        //pass the extra string from MainActivity to the ringtone Service
        intentToRingtoneService.putExtra("extra", getStringFromIntentMainActivity);
        //start ringtone Service
        context.startService(intentToRingtoneService);
    }
}
