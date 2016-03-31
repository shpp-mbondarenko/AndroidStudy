package ua.mycompany.loginpage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Maxim on 30.03.2016.
 */
class DBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "usersDB";
    private static final String TABLE_NAME = "Users";
    private static final String ISLAND_TABLE = "islandTable";
    final int DB_VERSION = 2;
    final String LOG_TAG = "myLogs";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        //constructor of superclass
        super(context, DATA_BASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // create table with fields
        db.execSQL("create table " + TABLE_NAME + " ("
                + "id integer primary key autoincrement,"
                + "island text,"
                + "userName text,"
                + "password text" + ");");
        db.execSQL("create table " + ISLAND_TABLE + " ("
                + "id integer primary key autoincrement,"
                + "island text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion
                + " to " + newVersion + " version --- ");
        if (oldVersion == 1 && newVersion == 2) {
            db.beginTransaction();
            try {
                db.execSQL("create table " + ISLAND_TABLE + " ("
                        + "id integer primary key autoincrement,"
                        + "island text"
                        + ");");
                Log.d(LOG_TAG, " --- ADD successful ---");
            } finally {
                db.endTransaction();
            }
        }
    }
}