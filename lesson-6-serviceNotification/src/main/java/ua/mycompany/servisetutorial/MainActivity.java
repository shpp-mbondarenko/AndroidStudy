package ua.mycompany.servisetutorial;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Button btnStartService;
    Button btnCloseService;
    TextView tVStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnCloseService = (Button) findViewById(R.id.btnCloseService);
        tVStatus = (TextView) findViewById(R.id.tVStatus);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.btnStartService:
                        intent = new Intent(getApplicationContext(), Receiver.class);
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis(),
                                1000 * 60 * 20,
                                pendingIntent);
                        tVStatus.setText("Service is running now!");
                        break;
                    case R.id.btnCloseService:

                        alarmManager.cancel(pendingIntent);
                        tVStatus.setText("Service is stopped!");
                        break;
                }

            }
        };


        btnStartService.setOnClickListener(listener);
        btnCloseService.setOnClickListener(listener);
    }


}
