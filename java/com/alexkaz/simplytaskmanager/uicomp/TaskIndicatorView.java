package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TaskIndicatorView extends View {

    public TaskIndicatorView(Context context) {
        super(context);
    }

    public TaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String result = "width - " + canvas.getWidth() + ", " + "height - " + canvas.getHeight();
        Log.d("myLog", result);

        canvas.drawColor(Color.RED);

    }
}
