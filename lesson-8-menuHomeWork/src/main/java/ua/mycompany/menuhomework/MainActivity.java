package ua.mycompany.menuhomework;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    final String LOG_TAG = "myLogs";

    int ID_FOR_ACTION_CONTEXT_MENU = -1;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    FragmentTransaction fragmentTransaction;

    FrameLayout frameLayout1;
    FrameLayout frameLayout2;
    FrameLayout frameLayout3;

    boolean fr1IsChecked = false;
    boolean fr2IsChecked = false;
    boolean fr3IsChecked = false;

    Random random;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        LinearLayout my = (LinearLayout) findViewById(R.id.mainLayout);
//        my.setBackgroundColor(Color.RED);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        frameLayout1 = (FrameLayout) findViewById(R.id.fragment1);
        frameLayout2 = (FrameLayout) findViewById(R.id.fragment2);
        frameLayout3 = (FrameLayout) findViewById(R.id.fragment3);

        registerForContextMenu(frameLayout1);
        registerForContextMenu(frameLayout2);
        registerForContextMenu(frameLayout3);

        random = new Random();
    }

    //CONTEXT MENU

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         if (fr3IsChecked || fr2IsChecked || fr1IsChecked) {
            switch (v.getId()) {
                case R.id.fragment1:
                    menu.add(0, 1, 0, "First Color").setIcon(R.drawable.ic_explore_black_24dp);
                    menu.add(0, 2, 0, "Second Color");
                    ID_FOR_ACTION_CONTEXT_MENU = 1;
                    break;
                case R.id.fragment2:
                    menu.add(0, 1, 0, "First Color");
                    menu.add(0, 2, 0, "Second Color");
                    ID_FOR_ACTION_CONTEXT_MENU = 2;
                    break;
                case R.id.fragment3:
                    menu.add(0, 1, 0, "First Color");
                    menu.add(0, 2, 0, "Second Color");
                    ID_FOR_ACTION_CONTEXT_MENU = 3;
                    break;
            }
        } else {
             Log.d(LOG_TAG, "no items for context menu");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        View tmp = item.getActionView();
//            tmp.toString();
        switch (item.getItemId()) {
            case 1:
                Log.d(LOG_TAG, "first context item selected");
                color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
                switch (ID_FOR_ACTION_CONTEXT_MENU) {
                    case 1:
                        fragment1.getActivity().findViewById(R.id.fragmentLayout1).setBackgroundColor(color);
                        break;
                    case 2:
                        fragment2.getActivity().findViewById(R.id.fragmentLayout2).setBackgroundColor(color);
                        break;
                    case 3:
                        fragment3.getActivity().findViewById(R.id.fragmentLayout3).setBackgroundColor(color);
                        break;
                }
                break;
            case 2:
                Log.d(LOG_TAG, "second context item selected");
                color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
                switch (ID_FOR_ACTION_CONTEXT_MENU) {
                    case 1:
                        fragment1.getActivity().findViewById(R.id.fragmentLayout1).setBackgroundColor(color);
                        break;
                    case 2:
                        fragment2.getActivity().findViewById(R.id.fragmentLayout2).setBackgroundColor(color);
                        break;
                    case 3:
                        fragment3.getActivity().findViewById(R.id.fragmentLayout3).setBackgroundColor(color);
                        break;
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    //END CONTEXT MENU

    //MAIN MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        Animation anim = null;
        switch (item.getItemId()) {
            case R.id.fr1:
                Log.d(LOG_TAG, "fr1 pressed");
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fr1IsChecked = true;
                } else {
                    item.setChecked(false);
                    fr1IsChecked = false;
                }
                if (item.isChecked()) {
                    Log.d(LOG_TAG, "Set fragment 2");
                    //show fragment in main layout
                    fragmentTransaction.add(R.id.fragment1, fragment1);
                    //add animation to framelayout
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame);
                    frameLayout1.startAnimation(anim);
                } else {
                    Log.d(LOG_TAG, "UnSet fragment 1");
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame_reverse);
                    anim.setFillAfter(true);
                    frameLayout1.startAnimation(anim);
                    fragmentTransaction.remove(fragment1);
                }
                break;

            case R.id.fr2:
                Log.d(LOG_TAG, "fr2 pressed");
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fr2IsChecked = true;
                } else {
                    item.setChecked(false);
                    fr2IsChecked = false;
                }
                if (item.isChecked()) {
                    Log.d(LOG_TAG, "Set fragment 2");
                    fragmentTransaction.add(R.id.fragment2, fragment2);
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame);
                    frameLayout2.startAnimation(anim);
                } else {
                    Log.d(LOG_TAG, "UnSet fragment 2");
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame_reverse);
                    fragmentTransaction.remove(fragment2);
                    frameLayout2.startAnimation(anim);
                }
                break;

            case R.id.fr3:
                Log.d(LOG_TAG, "fr3 pressed");
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fr3IsChecked = true;
                } else {
                    item.setChecked(false);
                    fr3IsChecked = false;
                }
                if (item.isChecked()) {
                    Log.d(LOG_TAG, "Set fragment 3");
                    fragmentTransaction.add(R.id.fragment3, fragment3);
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame);
                    frameLayout3.startAnimation(anim);
                } else {
                    Log.d(LOG_TAG, "UnSet fragment 3");
                    anim = AnimationUtils.loadAnimation(this, R.anim.alpha_frame_reverse);
                    fragmentTransaction.remove(fragment3);
                    frameLayout3.startAnimation(anim);
                }
                break;
        }
        fragmentTransaction.commit();
        return super.onOptionsItemSelected(item);
    }
    //END MAIN MENU
}
