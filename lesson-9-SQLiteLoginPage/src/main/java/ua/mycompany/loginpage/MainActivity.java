package ua.mycompany.loginpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String S_PREF_NAME = "name";
    private static final String S_PREF_PASSWORD = "pass";
    private static final String S_PREF_ISLAND = "addIslands";
    String userLocation;

    private static final String MY_PREF = "myPref" ;
    final String USER_NAME = "userName";

    final String LOG_TAG = "myLogs";

    Button btnRegistration, btnLogin, btnClear, btnViewUsers;
    EditText eTUserName, eTPassword;
    Spinner spinner;

    SQLiteDatabase db;
    DBHelper dbHelper;
    LoginDB loginDB;
    Cursor cursor = null;
    SharedPreferences sPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // create object for creating and control DB
        dbHelper = new DBHelper(getApplicationContext());
        // connecting to DB
        db = dbHelper.getWritableDatabase();
        loginDB = new LoginDB();

        checkPrefs();
        if(sPref.getBoolean(S_PREF_ISLAND, true)) readIslands();

        findObjects();
        btnRegistration.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnViewUsers.setOnClickListener(this);
    }

    //read islands from file. add islands in DataBase. one time.
    private void readIslands() {
        sPref = getSharedPreferences(MY_PREF ,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(S_PREF_ISLAND, false);
        ed.apply();
        try {
            Log.d(LOG_TAG, "--- IN READiSLANDS ---");
            InputStream in = this.getAssets().open("islands.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                loginDB.addIsland(db, line);
                Log.d(LOG_TAG, "--- ISLAND --- " + line);
            }
            loginDB.viewIslands(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkPrefs() {
        sPref = getSharedPreferences(MY_PREF,MODE_PRIVATE);
        String name = sPref.getString(S_PREF_NAME, "");
        String password  = sPref.getString(S_PREF_PASSWORD, "");
        if ( name == "" && password == "") {

        } else {
            Intent intent = new Intent(getApplicationContext(), HelloActivity.class);
            intent.putExtra(USER_NAME, "BLA BLA");
            startActivity(intent);
        }
    }

    //find buttons and edit text area
    private void findObjects() {
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnLogin  = (Button) findViewById(R.id.btnLogin);
        btnClear  = (Button) findViewById(R.id.btnClear);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnViewUsers = (Button) findViewById(R.id.btnViewUsers);
        eTUserName = (EditText) findViewById(R.id.eTUserName);
        eTPassword = (EditText) findViewById(R.id.eTPassword);

        final String[] items = loginDB.getIslandsArray(db);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userLocation = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userLocation = "Nowhere";
            }
        });


    }

    @Override
    public void onClick(View v) {

        String userName = eTUserName.getText().toString();
        String password = eTPassword.getText().toString();
//        String island = "Island";

        switch (v.getId()) {
            case R.id.btnLogin:
                if(loginDB.logIn(db, userName, password)) {
                    cursor = loginDB.getCursor();
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            Log.d(LOG_TAG, "--- TO TRANSFER ON HELLO ---");
                            // determine the index of columns
                            int nameColIndex = cursor.getColumnIndex("userName");
                            int passwordColIndex = cursor.getColumnIndex("password");

                            if (userName.equals(cursor.getString(nameColIndex)) &&
                                    password.equals(cursor.getString(passwordColIndex))) {
                                Log.d(LOG_TAG, "--- GO INTENT!!! ---");
                                Intent intent = new Intent(getApplicationContext(), HelloActivity.class);
                                intent.putExtra(USER_NAME, cursor.getString(nameColIndex));
                                saveSPreferences(userName, password);
                                startActivity(intent);
                            }  else {
                                Log.d(LOG_TAG, "Wrong user name, or password!");
                            }
                        }
                    }
                }
                break;
            case R.id.btnRegistration:
                loginDB.registrateUseer(db, userName, password, userLocation);
                break;
            case R.id.btnViewUsers:
                loginDB.viewUsers(db);
                break;
            case R.id.btnClear:
                loginDB.clearDB(db);
                break;
        }
    }

    void saveSPreferences(String userName, String password) {
        sPref = getSharedPreferences(MY_PREF ,MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(S_PREF_NAME, userName);
        ed.putString(S_PREF_PASSWORD, password);
//        ed.commit();
        ed.apply();
        Log.d(LOG_TAG, "--- PREFERENCES SAVED ---" + sPref.getString(S_PREF_NAME, "") + " PASS -"
                + sPref.getString(S_PREF_PASSWORD, ""));
    }
}
