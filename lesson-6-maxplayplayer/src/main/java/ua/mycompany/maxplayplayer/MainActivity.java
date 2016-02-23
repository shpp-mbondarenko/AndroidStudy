package ua.mycompany.maxplayplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnPlay, btnStop;
    ListView lv;
    String[] items;
    String POS = "Position";
    String SONGLIST = "Songlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv = (ListView) findViewById(R.id.songsListView);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        //find songs in device
        final ArrayList<File> songsList = findSongs(Environment.getExternalStorageDirectory());

        items = new String[songsList.size()];
        for (int i = 0; i < songsList.size(); i++) {
            items[i] = songsList.get(i).getName().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PlayerService.class);
                intent.putExtra(POS, position);
                intent.putExtra(SONGLIST, songsList);
                startService(intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), PlayerService.class));
            }
        });
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
}
