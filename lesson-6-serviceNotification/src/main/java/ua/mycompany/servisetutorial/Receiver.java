package ua.mycompany.servisetutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Maxim on 19.02.2016.
 */
public class Receiver extends BroadcastReceiver{
    String TAG = "yay";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "In receiver!");
        Intent intent1 = new Intent(context, NotificstionService.class);
        context.startService(intent1);
    }
}
