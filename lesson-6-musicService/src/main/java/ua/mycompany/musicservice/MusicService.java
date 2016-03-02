package ua.mycompany.musicservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MusicService extends Service {

    MyBinder binder = new MyBinder();
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    int position = -1;
    Uri uri;
    int quantitySongs;
    int serviceDuration;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        songs = new ArrayList<File>();
//        songs = findSongs(MainActivity.ROOT);
        Log.d(MainActivity.LOG, "In Service onCreate");
    }


    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void playerPrevious() {
        if (position <= 0) {
            position = quantitySongs;
        } else {
            position -= 1;
        }
        mediaPlayer.pause();
        mediaPlayer.release();
        uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
    }

    public void playerPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void playerForward() {
        if (position > quantitySongs) {
         position = 0;
        } else {
            position += 1;
        }
        mediaPlayer.pause();
        mediaPlayer.release();
        uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

    }

    public ArrayList<File> getSongs(File root) {
        songs = findSongs(root);
        return songs;
    }

    public void play(int pos) {
        songs = getSongs(Environment.getExternalStorageDirectory());
        quantitySongs = songs.size() - 1;
        if (pos == 0){
            Log.d(MainActivity.LOG, "NULL Pos");
        } else {
            Log.d(MainActivity.LOG, "NE NULL " + pos);
        }
        position = pos;
        if (mediaPlayer.isPlaying()) {
            Log.d(MainActivity.LOG, "IS playing 1");
            mediaPlayer.pause();
            mediaPlayer.release();
            uri = Uri.parse(songs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            serviceDuration = mediaPlayer.getDuration();
            Log.d(MainActivity.LOG, "SERVICE DURATION = " + serviceDuration );
        } else {
            Log.d(MainActivity.LOG, "IS playing 2 size - " + songs.size());
            uri = Uri.parse(songs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            serviceDuration = mediaPlayer.getDuration();
            Log.d(MainActivity.LOG, "SERVICE DURATION = " + serviceDuration);
        }
    }

    /*
     * Find all .mp3 files on sdCard and put it to ArrayList
     * @return - ArrayList<File> list of songs
     */
    private ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File oneFile : files) {
            if (oneFile.isDirectory() && !oneFile.isHidden()) {
                al.addAll(findSongs(oneFile));
            }else {
                if (oneFile.getName().endsWith(".mp3")) {
                    al.add(oneFile);
                }
            }
        }
        return al;
    }

    class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        Log.d(MainActivity.LOG, "OnDestroy Service!!");
    }


}
