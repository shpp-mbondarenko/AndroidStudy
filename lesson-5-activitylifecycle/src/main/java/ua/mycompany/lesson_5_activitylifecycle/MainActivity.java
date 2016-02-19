package ua.mycompany.lesson_5_activitylifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnTwo;
    Button btnThree;

    final String TAG = "ActivityState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnTwo = (Button) findViewById(R.id.btnActTwo);
        btnThree = (Button) findViewById(R.id.btnActThree);
        btnTwo.setOnClickListener(onClickListener);
        btnThree.setOnClickListener(onClickListener);

        Log.i(TAG, "MainActivity: onCreate()");
        Log.i(TAG, "The activity is being created.");


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnActTwo:
                    Intent intentTwo = new Intent(getApplicationContext(), ActivityTwo.class);
                    startActivity(intentTwo);
                    break;
                case R.id.btnActThree:
                    Intent intentThree = new Intent(getApplicationContext(), ActivityThree.class);
                    startActivity(intentThree);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "MainActivity: onRestart()");
    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "MainActivity: onStart()");
        Log.i(TAG, "The activity is about to become visible.");

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "MainActivity: onResume()");
        Log.i(TAG, "The activity has become visible (it is now \"resumed\").");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "MainActivity: onPause()");
        Log.i(TAG, "Another activity is taking focus (this activity is about to be \"paused\")");

    }


    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "MainActivity: onStop()");
        Log.i(TAG, "The activity is no longer visible (it is now \"stopped\")");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "MainActivity: onDestroy()");
        Log.i(TAG, "The activity is about to be destroyed.");

    }
}
