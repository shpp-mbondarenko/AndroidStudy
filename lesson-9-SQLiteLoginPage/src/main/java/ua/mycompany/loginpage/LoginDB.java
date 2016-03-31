package ua.mycompany.loginpage;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Maxim on 30.03.2016.
 */
public class LoginDB {
    private static final String TABLE_NAME = "Users";
    private static final String ISLAND_TABLE = "islandTable";


    // variables for query
    String[] columns = null;
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;

    //Cursor
    Cursor c = null;

    final String LOG_TAG = "myLogs";


    public boolean logIn(SQLiteDatabase db, String userName, String password){
        if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            Log.d(LOG_TAG, "--- Break LOGiN ---");
            return false;
        } else {
            Log.d(LOG_TAG, "--- LOGiN CHECK ---");
            selection = "userName = ?";
            selectionArgs = new String[] { userName };
            c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            return true;
        }
    }

    public void registrateUseer(SQLiteDatabase db, String userName, String password, String island){
        if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            Log.d(LOG_TAG, "--- Break Registrarion ---");
        } else {
            // create object for data  !!!READ MORE
            ContentValues cv = new ContentValues();

            Log.d(LOG_TAG, "--- Insert in mytable: ---");

            // prepare the data to be inserted, in the form of pairs: Column name - value
            cv.put("userName", userName);
            cv.put("password", password);
            cv.put("island", island);

            // insert one record and retrieving ID
            long rowID = db.insert(TABLE_NAME, null, cv);
            Log.d(LOG_TAG, "Row inserted, ID = " + rowID);
        }
    }

    public void viewUsers(SQLiteDatabase db){
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // make query to DB retrieving all users
        c = db.query(TABLE_NAME, null, null, null, null, null, null);

        //place cursor on first position
        // if c is null return false
        if (c != null) {
            if (c.moveToFirst()) {
                //defining column's number by theirs name
                int idColIndex = c.getColumnIndex("id");
                int islandIndex = c.getColumnIndex("island");
                int nameColIndex = c.getColumnIndex("userName");
                int passwordColIndex = c.getColumnIndex("password");

                do {
                    // retrieving values by number of column
                    Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) +
                            ", island = " + c.getString(islandIndex) +
                            ", userName = " + c.getString(nameColIndex) +
                            ", password = " + c.getString(passwordColIndex));
                    // go to next string
                    // if she don't exists  then false - out from cycle
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
        }
    }

    public Cursor getCursor(){
        return c;
    }

    public void clearDB(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- Clear mytable: ---");
        // delete all users
        int clearCount = db.delete(TABLE_NAME, null, null);
        Log.d(LOG_TAG, "Deleted rows count = " + clearCount);
    }


    public void addIsland(SQLiteDatabase db, String island) {
        if (island == null) {
            Log.d(LOG_TAG, "--- Break Registrarion ISLANDS ---");
        } else {
            // create object for data  !!!READ MORE
            ContentValues cv = new ContentValues();

            Log.d(LOG_TAG, "--- Insert in ISLANDS: ---");

            // prepare the data to be inserted, in the form of pairs: Column name - value
            cv.put("island", island);

            // insert one record and retrieving ID
            long rowID = db.insert(ISLAND_TABLE, null, cv);
            Log.d(LOG_TAG, "Row inserted in ISLANDS, ID = " + rowID);
        }
    }

    public void viewIslands(SQLiteDatabase db){
        Log.d(LOG_TAG, "--- Rows in mytable ISLANDS: ---");
        // make query to DB retrieving all users
        c = db.query(ISLAND_TABLE, null, null, null, null, null, null);

        //place cursor on first position
        // if c is null return false
        if (c != null) {
            if (c.moveToFirst()) {
                //defining column's number by theirs name
                int idColIndex = c.getColumnIndex("id");
                int islandIndex = c.getColumnIndex("island");

                do {
                    // retrieving values by number of column
                    Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) +
                            ", island = " + c.getString(islandIndex));
                    // go to next string
                    // if she don't exists  then false - out from cycle
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
        }
    }

    public String[] getIslandsArray(SQLiteDatabase db){
        ArrayList<String> tmp = new ArrayList<String>();

        c = db.query(ISLAND_TABLE, null, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                //defining column's number by theirs name
                int islandIndex = c.getColumnIndex("island");

                do {
                    // retrieving values by number of column
                    tmp.add(c.getString(islandIndex));
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
        }
        Log.d(LOG_TAG, "SIZESIZE - " +tmp.size());

        String[] items = new String[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            items[i] = tmp.get(i);
        }
        return items;
    }

    public String findUserLocation(SQLiteDatabase db, String userName, String password) {
        String res = "";
        selection = "userName = ?";
        selectionArgs = new String[] { userName };
        c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        //place cursor on first position
        // if c is null return false
        if (c != null) {
            if (c.moveToFirst()) {
                //defining column's number by theirs name
                int islandIndex = c.getColumnIndex("island");
                int nameColIndex = c.getColumnIndex("userName");
                int passwordColIndex = c.getColumnIndex("password");

                do {
                    Log.d(LOG_TAG, "--- IN FINDISLANDuSER ---");
                    if (userName.equals(c.getString(nameColIndex)) && password.equals(c.getString(passwordColIndex))) {
                        Log.d(LOG_TAG, "--- IN FINDISLANDuSER 2 ---");
                        res = c.getString(islandIndex);
                    }
                    // go to next string
                    // if she don't exists  then false - out from cycle
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "User live Nowhere!");
            }
        }

        return res;
    }
}
