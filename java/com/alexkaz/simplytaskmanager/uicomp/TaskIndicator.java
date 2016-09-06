package com.alexkaz.simplytaskmanager.uicomp;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alexkaz.simplytaskmanager.R;

import java.util.ArrayList;

public class TaskIndicator {

    public static final int MAX_DISPLAY_COUNT = 8;
    public static final float CIRCLE_RADIUS_DIVIDER = 0.75f;
    public static final float LINKING_RECT_SIZE_DIVIDER = 0.25f;
    private boolean measureFlag = true;
    private int circleRadius;
    private int elemRectSizeX;
    private int elemRectSizeY;
    private int linkingRectSizeX;
    private int linkingRectSizeY;

    private float blurRadius;

    private Context context;

    private Paint notCompletedPaint;
    private Paint inProcessPaint;
    private Paint donePaint;

    private Paint notCompletedBlurPaint;
    private Paint inProcessBlurPaint;
    private Paint doneBlurPaint;

    private TaskStatus[] taskStatuses = new TaskStatus[8];
    private ArrayList<TaskStatus> statuses;

    public TaskIndicator(Context context) {
        this.context = context;
        initComps();
    }

    private void initComps() {
        taskStatuses[0] = TaskStatus.DONE;
        taskStatuses[1] = TaskStatus.DONE;
        taskStatuses[2] = TaskStatus.DONE;
        taskStatuses[3] = TaskStatus.IN_PROCESS;
        taskStatuses[4] = TaskStatus.IN_PROCESS;
        taskStatuses[5] = TaskStatus.NOT_COMPLITED;
        taskStatuses[6] = TaskStatus.NOT_COMPLITED;
        taskStatuses[7] = TaskStatus.NOT_COMPLITED;

        statuses = new ArrayList<>();
        for (int i = 0; i < MAX_DISPLAY_COUNT; i++) {
            statuses.add(TaskStatus.NOT_COMPLITED);
        }

    }


    public void draw(Canvas canvas){
//        canvas.drawColor(Color.GREEN);
        if (measureFlag){
            determineMeasurements(canvas.getWidth(),canvas.getHeight());
            initPaintComp();
            measureFlag = false;
        }

        int countOfTaskItems;
        if (statuses.size() > MAX_DISPLAY_COUNT ){
            countOfTaskItems = MAX_DISPLAY_COUNT;
        } else {
            countOfTaskItems = statuses.size();
        }
        for (int i = 0; i < countOfTaskItems; i++) {
            switch (statuses.get(i)){
                case DONE:
                    if (i < countOfTaskItems-1){
                        canvas.drawRect(i*linkingRectSizeX+linkingRectSizeX/2,elemRectSizeY/2 - linkingRectSizeY,i*linkingRectSizeX+linkingRectSizeX/2 + linkingRectSizeX,elemRectSizeY/2 + linkingRectSizeY,donePaint);
                    }
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,circleRadius,donePaint);
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,blurRadius,doneBlurPaint);
                    break;
                case IN_PROCESS:
                    if (i < countOfTaskItems-1){
                        canvas.drawRect(i*linkingRectSizeX+linkingRectSizeX/2,elemRectSizeY/2 - linkingRectSizeY,i*linkingRectSizeX+linkingRectSizeX/2 + linkingRectSizeX,elemRectSizeY/2 + linkingRectSizeY,inProcessPaint);
                    }
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,circleRadius,inProcessPaint);
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,blurRadius,inProcessBlurPaint);
                    break;
                case NOT_COMPLITED:
                    if (i < countOfTaskItems-1){
                        canvas.drawRect(i*linkingRectSizeX+linkingRectSizeX/2,elemRectSizeY/2 - linkingRectSizeY,i*linkingRectSizeX+linkingRectSizeX/2 + linkingRectSizeX,elemRectSizeY/2 + linkingRectSizeY,notCompletedPaint);
                    }
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,circleRadius,notCompletedPaint);
                    canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,blurRadius,notCompletedBlurPaint);
                    break;
            }
        }
    }

    private void determineMeasurements(int width, int height){
        elemRectSizeX = width/MAX_DISPLAY_COUNT;
        elemRectSizeY = height;
        if (elemRectSizeX >= elemRectSizeY){
            float fCircleRadius = (elemRectSizeY/2) * CIRCLE_RADIUS_DIVIDER;
            circleRadius = (int)fCircleRadius;
        }else {
            float fCircleRadius = (elemRectSizeX/2) * CIRCLE_RADIUS_DIVIDER;
            circleRadius = (int)fCircleRadius;
        }
        linkingRectSizeX = elemRectSizeX;
        float fLinkingRectSizeY = circleRadius* LINKING_RECT_SIZE_DIVIDER;
        linkingRectSizeY = (int)fLinkingRectSizeY;
    }

    private void initPaintComp(){
        blurRadius = circleRadius*0.7f;

        notCompletedPaint = new Paint();
        notCompletedPaint.setColor(context.getResources().getColor(R.color.VI_NotCompletedColor));
        notCompletedPaint.setAntiAlias(true);

        inProcessPaint = new Paint();
        inProcessPaint.setColor(context.getResources().getColor(R.color.VI_InProcessColor));
        inProcessPaint.setAntiAlias(true);

        donePaint = new Paint();
        donePaint.setColor(context.getResources().getColor(R.color.VI_DoneColor));
        donePaint.setAntiAlias(true);

        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL);

        notCompletedBlurPaint = new Paint();
        notCompletedBlurPaint.setMaskFilter(blurMaskFilter);
        notCompletedBlurPaint.setColor(context.getResources().getColor(R.color.VI_NotCompletedBlurColor));

        inProcessBlurPaint = new Paint();
        inProcessBlurPaint.setMaskFilter(blurMaskFilter);
        inProcessBlurPaint.setColor(context.getResources().getColor(R.color.VI_InProcessBlurColor));

        doneBlurPaint = new Paint();
        doneBlurPaint.setMaskFilter(blurMaskFilter);
        doneBlurPaint.setColor(context.getResources().getColor(R.color.VI_DoneBlurColor));
    }

    public void setTaskStatus(int position, TaskStatus taskStatus){
        statuses.set(position,taskStatus);
    }

    public void setTaskStatuses(ArrayList<TaskStatus> statuses){
        this.statuses = statuses;
    }
}
