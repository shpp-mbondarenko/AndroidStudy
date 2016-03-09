package ua.mycompany.lesson_7_view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Maxim on 09.03.2016.
 */
public class CustView extends View {

    Paint paint;
    Random random;

    public CustView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        random = new Random();
        setMinimumHeight(200);
        setMinimumWidth(200);
    }

    public CustView(Context context) {
        super(context);
        paint = new Paint();
        random = new Random();
        setMinimumHeight(200);
        setMinimumWidth(200);
    }

    public CustView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        random = new Random();
        setMinimumHeight(200);
        setMinimumWidth(200);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        paint.setColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        canvas.drawCircle(0, 0, 250, paint);
    }
}
