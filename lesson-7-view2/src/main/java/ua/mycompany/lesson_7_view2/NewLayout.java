package ua.mycompany.lesson_7_view2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Maxim on 09.03.2016.
 */
public class NewLayout extends LinearLayout {
//   String TAG = "log";
//    int SIZE_OF_ARR;
//    float[] items;
    public NewLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        final NewLayout ll = NewLayout.this;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ll.getChildCount(); ++i) {
                    View child = ll.getChildAt(i);
                    TranslateAnimation animation = new TranslateAnimation(0, 0,
                            0,
                            ll.getHeight() - child.getHeight() - child.getY());

                    animation.setDuration(2000);
                    animation.setFillAfter(true);
                    child.startAnimation(animation);

                }
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "NEXT " + ll.getChildCount(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < ll.getChildCount(); ++i) {
                    View child = ll.getChildAt(i);
                    TranslateAnimation animation = new TranslateAnimation(0, 0,
                            ll.getHeight() - child.getHeight() - child.getY(),
                            0);
                    animation.setDuration(2000);
                    animation.setFillAfter(true);
                    child.startAnimation(animation);
                }
                return true;
            }
        });
    }

    public NewLayout(Context context) {
        super(context);
    }
}
