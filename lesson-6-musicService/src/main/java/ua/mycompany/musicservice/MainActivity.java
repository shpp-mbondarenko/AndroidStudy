package ua.mycompany.musicservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static final String LOG = "log";
    public final static File ROOT = Environment.getExternalStorageDirectory();

    Button stp;
    boolean bound = false;
    ServiceConnection connection;
    Intent intent;
    MusicService musicService = new MusicService();
    String[] items;
    ListView lv;
    ArrayList<File> songsList;
    public static SeekBar seekBar;

    MyTask mt;
    Thread updateSeekBar;
    boolean isMTRuning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = (ListView) findViewById(R.id.songsListView);
        stp = (Button) findViewById(R.id.btnStopService);


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

        seekBar = (SeekBar) findViewById(R.id.seekBarMusic);
        //set list of music
        intent = new Intent(getApplicationContext(), MusicService.class);
        songsList = musicService.getSongs(ROOT);
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

                if(isMTRuning) {
                    mt.cancel(false);
                }

                mt = new MyTask();
                musicService.play(pos);
                mt.execute();

            }
        });
    }



    public void onStart() {
        super.onStart();
        bindService(intent, connection, BIND_AUTO_CREATE);
        Log.d(LOG, "On start");
    }

    public void onClickPrevious(View view) {
        if(isMTRuning) {
            mt.cancel(false);
        }
        musicService.playerPrevious();
//        mt.execute();
    }

    public void onClickPause(View view) {
        musicService.playerPause();
    }

    public void onClickForward(View view) {
        if(isMTRuning) {
            mt.cancel(false);
        }
        musicService.playerForward();
//        mt.execute();

    }

    public void onClickStopService(View view) {
        unbindService(connection);
        musicService.stopSelf();
    }


    class MyTask extends AsyncTask<Void, Integer, Void> {
        int progress;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            seekBar.setProgress(0);
            Log.d(LOG, "On onPreExecute()");
            progress = 0;
        }

        //do handler to seek bar
        @Override
        protected Void doInBackground(Void... params) {
            isMTRuning = true;
            int duration = musicService.serviceDuration;
            int onePercent = duration / 100;
            Log.d(LOG, "DURATION = " + duration + " ONE PERSENT = " + onePercent);

            for (int progress = 1; progress <= 100; progress++) {
                try {
                    Thread.sleep(onePercent);
                    Log.d(LOG, "TRY IS WORK");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(progress);
                Log.d(LOG, "PERCENT = " + progress);
                if (isCancelled()) return null; //this row interrupt doInBackground if it is need

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            seekBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d(LOG, "On onPostExecute()");

        }
    }

}

