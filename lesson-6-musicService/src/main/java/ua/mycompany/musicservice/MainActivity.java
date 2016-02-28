package ua.mycompany.musicservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static final String LOG = "log";
    public final static String POS = "Position";
    public final static String SONGLIST = "Songlist";
    public final static String BROADCAST_ACTION = "dada";


    boolean bound = false;
    ServiceConnection connection;
    Intent intent;
    MusicService musicService;
    String[] items;
    ListView lv;
    ArrayList<File> songsList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = (ListView) findViewById(R.id.songsListView);


        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG, "MainActivity onServiceConnected");
                musicService = ((MusicService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };

        intent = new Intent(getApplicationContext(), MusicService.class);
//        File root = Environment.getRootDirectory();
         songsList = musicService.getSongs(Environment.getExternalStorageDirectory());
//        Intent intentFromService = getIntent();
//        songsList = (ArrayList) intentFromService.getParcelableArrayListExtra(SONGLIST);
//        String b = intentFromService.getStringExtra(POS);
//        Log.d(LOG, b.toString());
        items = new String[songsList.size()];
        for (int i = 0; i < songsList.size(); i++) {
            items[i] = songsList.get(i).getName().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                items);
        lv.setAdapter(adapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onStart() {
        super.onStart();
        bindService(intent, connection, BIND_AUTO_CREATE);
        Log.d(LOG, "On start");
    }

    public void onClickPrevious(View view) {
        musicService.previous();
    }

    public void onClickPause(View view) {
        musicService.pause();
    }

    public void onClickForward(View view) {
        musicService.forward();
    }

    public void onClickStopService(View view) {
        unbindService(connection);
    }

    private ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File oneFile : files) {
            if (oneFile.isDirectory() && !oneFile.isHidden()) {
                al.addAll(findSongs(oneFile));
            } else {
                if (oneFile.getName().endsWith(".mp3")) {
                    al.add(oneFile);
                }
            }
        }
        return al;
    }

    
}
