package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class TaskIndicatorView extends View {

    private TaskIndicator taskIndicator;

    public TaskIndicatorView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        taskIndicator = new TaskIndicator(context);
    }

    public TaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        taskIndicator = new TaskIndicator(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String result = "width - " + canvas.getWidth() + ", " + "height - " + canvas.getHeight(); // 258 27
        Log.d("myLog", result);
        taskIndicator.draw(canvas);
    }

    public void setTaskStatuses(ArrayList<TaskStatus> statuses){
        taskIndicator.setTaskStatuses(statuses);
    }
}
