package ua.mycompany.lesson_5_activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Maxim on 10.02.2016.
 */
public class ActivityTwo extends Activity {

    private String TAG = "ActivityState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
       
        Log.i(TAG, "ActivityTwo: onCreate()");

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "ActivityTwo: onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "ActivityTwo: onResume()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "ActivityTwo: onRestart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "ActivityTwo: onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "ActivityTwo: onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "ActivityTwo: onDestroy()");
    }
}
