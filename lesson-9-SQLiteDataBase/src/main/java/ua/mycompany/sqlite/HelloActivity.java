package ua.mycompany.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Maxim on 19.03.2016.
 */
public class HelloActivity extends Activity {

    final String USER_NAME = "userName";
    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER_NAME);
        hello = (TextView)findViewById(R.id.tvHello);


        hello.setTextSize(25);
        hello.setText("Hello, " + userName + " , nice to meet you!");

    }
}
