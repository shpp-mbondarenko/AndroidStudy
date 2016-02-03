package ua.mycompany.lesson_3_readtextfromfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView myText;
    String poem = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myText = (TextView) findViewById(R.id.textView);
        try {
            poem = poem.concat(getStringFromAssetFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        myText.setText(poem);
    }

    String getStringFromAssetFile() throws IOException {
        String text = "cat.txt";
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_data = new String(buffer);
        return str_data;
    }

}
