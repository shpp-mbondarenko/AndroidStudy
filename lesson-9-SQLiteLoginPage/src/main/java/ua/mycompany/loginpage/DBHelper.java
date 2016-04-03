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
    private static final int DB_VERSION = 1;
    final String LOG_TAG = "myLogs";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        //constructor of superclass
        super(context, DATA_BASE_NAME, null, DB_VERSION);
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
                db.execSQL("create temporary table Users_tmp ("
                        + "id integer primary key autoincrement,"+ "island text," + "userName text," + "password text" + ");");

                db.execSQL("insert into Users_tmp select id, island, userName, password from Users;");
                db.execSQL("drop table Users;");

                db.execSQL("create table Users ("
                        + "id integer primary key autoincrement," + "island text," + "userName text," + "password text" + ");");

                db.execSQL("insert into Users select id, name, posid from Users_tmp;");
                db.execSQL("drop table Users_tmp;");
                //add new table
                db.execSQL("create table " + ISLAND_TABLE + " ("
                        + "id integer primary key autoincrement," + "island text" + ");");


                db.setTransactionSuccessful();
                Log.d(LOG_TAG, " --- ADD successful ---");
            } finally {
                db.endTransaction();
            }
        }
    }
}