package ua.mycompany.menuhomework;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Maxim on 21.03.2016.
 */
public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment1, null);
        LinearLayout my = (LinearLayout) v.findViewById(R.id.fragmentLayout1);


        return v;
//        return inflater.inflate(R.layout.fragment1, null);
    }
}
