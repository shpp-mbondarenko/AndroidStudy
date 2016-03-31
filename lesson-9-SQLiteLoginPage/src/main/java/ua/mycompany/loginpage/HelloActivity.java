package ua.mycompany.loginpage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Maxim on 19.03.2016.
 */
public class HelloActivity extends Activity {

    private static final String S_PREF_NAME = "name";
    private static final String S_PREF_PASSWORD = "pass";
    private static final String MY_PREF = "myPref" ;
    final String USER_NAME = "userName";

    final String LOG_TAG = "myLogs";

    SharedPreferences sPref;

    TextView tvHello, tvIsland;
    Button btnLogOut;

    SQLiteDatabase db;
    DBHelper dbHelper;
    LoginDB loginDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        // create object for creating and control DB
        dbHelper = new DBHelper(getApplicationContext());
        // connecting to DB
        db = dbHelper.getWritableDatabase();
        loginDB = new LoginDB();

        sPref = getSharedPreferences(MY_PREF,MODE_PRIVATE);
        String name = sPref.getString(S_PREF_NAME, "");
        String password  = sPref.getString(S_PREF_PASSWORD, "");
        Log.d(LOG_TAG, "Name - " + name + " " + password);

        String userLocation;
        userLocation = loginDB.findUserLocation(db, name, password);
        Log.d(LOG_TAG, "USER LOCAT - " + userLocation);

        tvHello = (TextView)findViewById(R.id.tvHello);
        tvIsland = (TextView)findViewById(R.id.tvIsland);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);




        tvHello.setTextSize(25);
        tvIsland.setTextSize(25);
        tvHello.setText("Hello, " + name + ", nice to meet you!");
        tvIsland.setText("You live at " + userLocation + " island!");

    }

    public void btnLogOutOnClick(View view) {
        Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        clearSPref();
        startActivity(toMainActivity);

    }

    private void clearSPref(){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(S_PREF_NAME, "");
        ed.putString(S_PREF_PASSWORD, "");

        ed.commit();
        Log.d(LOG_TAG, "--- PREFERENCES CLEARED ---");
    }
}
