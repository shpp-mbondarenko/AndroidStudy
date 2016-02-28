package ua.mycompany.musicservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MusicService extends Service {

    MyBinder binder = new MyBinder();
    private ArrayList<File> songs;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.LOG, "In Service");
//        songs = getSongs();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra(MainActivity.SONGLIST, songs);
//        intent.putExtra(MainActivity.POS, "333");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
    }



    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void previous() {
        
    }

    public void pause() {
    }

    public void forward() {

    }

    public ArrayList<File> getSongs(File root) {
        return findSongs(root);
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
}
