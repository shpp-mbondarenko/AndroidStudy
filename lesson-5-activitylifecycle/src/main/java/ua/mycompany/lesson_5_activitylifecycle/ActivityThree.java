package ua.mycompany.lesson_5_activitylifecycle;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Maxim on 10.02.2016.
 */
public class ActivityThree extends Activity {
    private String TAG = "ActivityState";
    int DIALOG_TIME = 1;
//    Calendar calendar = Calendar.getInstance();

    int myHour = Calendar.HOUR_OF_DAY;
    int myMinute = Calendar.MINUTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);
        Button workBtn = (Button) findViewById(R.id.workBtn);
        workBtn.setOnClickListener(onClickListener);
        Log.i(TAG, "ActivityThree: onCreate()");

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.workBtn:
                    showDialog(DIALOG_TIME);
                    break;
                default:
                    break;
            }

        }
    };

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            Toast.makeText(ActivityThree.this, "Time is " + myHour + " hours " + myMinute + " minutes", Toast.LENGTH_LONG).show();
//            tvTime.setText("Time is " + myHour + " hours " + myMinute + " minutes");
        }
    };



    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "ActivityThree: onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "ActivityThree: onResume()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "ActivityThree: onRestart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "ActivityThree: onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "ActivityThree: onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "ActivityThree: onDestroy()");
    }
}

