package ua.mycompany.hidebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnHide;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);

        //find view elements
        btnHide = (Button) findViewById(R.id.hideButton);
        btnShow = (Button) findViewById(R.id.showButton);


        //create onClick handler
        final View.OnClickListener onClickHideButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHide.setVisibility(View.INVISIBLE);
            }
        };

        View.OnClickListener onClickShowButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHide.setVisibility(View.VISIBLE);
            }
        };

        //assign handler to button
        btnHide.setOnClickListener(onClickHideButton);
        btnShow.setOnClickListener(onClickShowButton);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
