package ua.mycompany.maxplayplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PlayerService extends Service {

    String POS = "Position";
    String SONGLIST = "Songlist";
    MediaPlayer mPlayer;
    ArrayList<File> songsList;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана", Toast.LENGTH_SHORT).show();
    }

    public PlayerService() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "Служба запущена",
                Toast.LENGTH_SHORT).show();
        mPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle b = intent.getExtras();
        songsList = (ArrayList) b.getParcelableArrayList(SONGLIST);
        int position = b.getInt(POS, 0);

        Uri uri = Uri.parse(songsList.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        mPlayer.stop();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
