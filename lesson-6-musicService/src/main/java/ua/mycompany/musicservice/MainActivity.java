package ua.mycompany.musicservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static final String LOG = "log";
    public final static String POS = "Position";
    public final static String SONGLIST = "Songlist";
    public final static String PROGRESS = "Progress";


    boolean bound = false;
    ServiceConnection connection;
    Intent intent;
    MusicService musicService = new MusicService();
    String[] items;
    ListView lv;
    ArrayList<File> songsList;
    int position = -1;

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

        //set list of music
        intent = new Intent(getApplicationContext(), MusicService.class);
        songsList = musicService.getSongs(Environment.getExternalStorageDirectory());
        items = new String[songsList.size()];
        for (int i = 0; i < songsList.size(); i++) {
            items[i] = songsList.get(i).getName().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                items);
        lv.setAdapter(adapter);

        //set listener on list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                position = pos;
                musicService.play(pos);
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                Uri uri = Uri.parse(songsList.get(position).toString());
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//                mediaPlayer.start();

            }
        });

    }

    public void onStart() {
        super.onStart();
        bindService(intent, connection, BIND_AUTO_CREATE);
        Log.d(LOG, "On start");
    }

    public void onClickPrevious(View view) {
        musicService.playerPrevious();
    }

    public void onClickPause(View view) {
        musicService.playerPause();
    }

    public void onClickForward(View view) {
        musicService.playerForward();
    }

    public void onClickStopService(View view) {
        unbindService(connection);
    }




}
