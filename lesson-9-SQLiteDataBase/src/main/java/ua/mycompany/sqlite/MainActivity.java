package ua.mycompany.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DATA_BASE_NAME = "usersDB" ;
    private static final String TABLE_NAME = "Users";
    final String USER_NAME = "userName";

    final String LOG_TAG = "myLogs";

    Button btnRegistration, btnLogin, btnClear, btnViewUsers;
    EditText eTUserName, eTPassword;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findObjects();

        // create object for creating and control DB
        dbHelper = new DBHelper(this);

        btnRegistration.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnViewUsers.setOnClickListener(this);
    }

    //find buttons and edit text area
    private void findObjects() {
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnLogin  = (Button) findViewById(R.id.btnLogin);
        btnClear  = (Button) findViewById(R.id.btnClear);
        btnViewUsers = (Button) findViewById(R.id.btnViewUsers);
        eTUserName = (EditText) findViewById(R.id.eTUserName);
        eTPassword = (EditText) findViewById(R.id.eTPassword);

    }

    @Override
    public void onClick(View v) {
        // create object for data
        ContentValues cv = new ContentValues();

        // receive data from the input fields
        String userName = eTUserName.getText().toString();
        String password = eTPassword.getText().toString();

        // variables for query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // connecting to DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Cursor
        Cursor c = null;

        switch (v.getId()) {
            case R.id.btnLogin:
                if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
                    Log.d(LOG_TAG, "--- Break LOGiN ---");
                    break;
                }
                String controlUserNameCheck = eTUserName.getText().toString();
                String controlPasswordCheck = eTPassword.getText().toString();


                Log.d(LOG_TAG, "--- LOGiN CHECK ---");
                selection = "userName = ?";
                selectionArgs = new String[] { userName };
                c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);


                if (c != null) {
                    if (c.moveToFirst()) {
                        Log.d(LOG_TAG, "---TO TRANSFER ON HELLO ---");
                        // determine the number of columns
                        int idColIndex = c.getColumnIndex("id");
                        int nameColIndex = c.getColumnIndex("userName");
                        int passwordColIndex = c.getColumnIndex("password");

                        if (controlUserNameCheck.equals(c.getString(nameColIndex)) &&
                                controlPasswordCheck.equals(c.getString(passwordColIndex))) {
                            Log.d(LOG_TAG, "---GO INTENT!!! ---");
                            Intent intent = new Intent(getApplicationContext(), HelloActivity.class);
                            intent.putExtra(USER_NAME,c.getString(nameColIndex));
                            startActivity(intent);
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Wrong user name, or password!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegistration:
//              Toast.makeText(getApplicationContext(),"Registration",Toast.LENGTH_SHORT).show();
                if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
                    Log.d(LOG_TAG, "--- Break LOGiN ---");
                    break;
                }
                Log.d(LOG_TAG, "--- Insert in mytable: ---");

                // prepare the data to be inserted, in the form of pairs: Column name - value
                cv.put("userName", userName);
                cv.put("password", password);

                // insert one record and retrieving ID
                long rowID = db.insert(TABLE_NAME, null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnViewUsers:
//              Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // make querry to DB retrieving all users
                c = db.query(TABLE_NAME, null, null, null, null, null, null);


                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // delete all users
                int clearCount = db.delete(TABLE_NAME, null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;

        }
        //place cursor on first position
        // if c is null return false
        if (c != null) {
            if (c.moveToFirst()) {
                //defining column's number by theirs name
                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("userName");
                int passwordColIndex = c.getColumnIndex("password");

                do {
                    // retrieving values by number of column
                    Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) +
                                    ", userName = " + c.getString(nameColIndex) +
                                    ", password = " + c.getString(passwordColIndex));
                    // go to next string
                    // if she don't exists  then false - out from cycle
                } while (c.moveToNext());
            } else
                Log.d(LOG_TAG, "0 rows");
            c.close();
        }

    }


    class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBHelper(Context context) {
            //constructor of superclass
            super(context, DATA_BASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // create table with fields
            db.execSQL("create table " + TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "userName text,"
                    + "password text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // close connection to DataBase
        dbHelper.close();
    }
}
