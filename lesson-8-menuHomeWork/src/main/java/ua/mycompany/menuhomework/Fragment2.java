package ua.mycompany.menuhomework;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Maxim on 21.03.2016.
 */
public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, null);
        LinearLayout my = (LinearLayout) v.findViewById(R.id.fragmentLayout2);

        return v;
//        return inflater.inflate(R.layout.fragment2, null);
    }
}
