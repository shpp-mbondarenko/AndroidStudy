package ua.mycompany.lesson_4_alarmclock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
//to make our alarm manager
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView updateText;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;

        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //initialize timePicker
        alarmTimePicker = (TimePicker) findViewById(R.id.timePickerForAlarm);
        //init text view updateText and buttons
        updateText = (TextView) findViewById(R.id.updateText);
        Button alarmOn = (Button)findViewById(R.id.btnTurnOn);
        Button alarmOff = (Button)findViewById(R.id.btnTurnOff);

        //create a calendar
        final Calendar calendar = Calendar.getInstance();

        //create intent to te alarm receiver class
        final Intent intentToAlarmReceiver = new Intent(this, AlarmReceiver.class);

        //create onClick listener for buttons
        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                int hour = alarmTimePicker.getCurrentHour();
                int minute = alarmTimePicker.getCurrentMinute();
                String hourStr = String.valueOf(hour);
                String minuteStr = String.valueOf(minute);
                if (minute < 10)
                    minuteStr = "0" + String.valueOf(minute);
                setAlarmText("Alarm on! Set to:" + hourStr + " : " + minuteStr);

                //put in extra string into intentToAlarmReceiver
                //tells the clock that you pressed the "Alarm on" button
                intentToAlarmReceiver.putExtra("extra", "alarm on");

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intentToAlarmReceiver, PendingIntent.FLAG_UPDATE_CURRENT);
                //set alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent );
            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarmText("Alarm off!");

                //cancel the alarm (pending intent)
                alarmManager.cancel(pendingIntent);
                //put in extra string into intentToAlarmReceiver
                //tells the clock that you pressed the "Alarm off" button
                intentToAlarmReceiver.putExtra("extra", "alarm off");

                //stop ringtone
                sendBroadcast(intentToAlarmReceiver);
            }
        });

    }

    private void setAlarmText(String s) {
        updateText.setText(s);
    }
}
