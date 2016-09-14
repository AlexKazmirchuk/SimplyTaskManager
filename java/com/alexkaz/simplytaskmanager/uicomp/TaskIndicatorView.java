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
        initComps(context);
    }

    public TaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComps(context);
    }

    private void initComps(Context context){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        taskIndicator = new TaskIndicator(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        taskIndicator.determineMeasurements(this.getWidth(),this.getHeight());
        taskIndicator.draw(canvas);
    }

    public void setTaskStatuses(ArrayList<TaskStatus> statuses){
        taskIndicator.setTaskStatuses(statuses);
    }

    @Override
    public void invalidate() {
        taskIndicator.determineMeasurements(this.getWidth(),this.getHeight());
        super.invalidate();
    }
}
