package com.alexkaz.simplytaskmanager.uicomp;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class TaskIndictor {

    private boolean measureFlag = true;
    private int circleRadius;
    private int elemRectSizeX;
    private int elemRectSizeY;
    private int linkingRectSizeX;
    private int linkingRectSizeY;

    private float blurRadius;

    private Paint notCompletedPaint;
    private Paint inProcessPaint;
    private Paint donePaint;

    private Paint notCompletedBlurPaint;
    private Paint inProcessBlurPaint;
    private Paint doneBlurPaint;

    public TaskIndictor() {

    }


    public void draw(Canvas canvas){
//        canvas.drawColor(Color.GREEN);
        if (measureFlag){
            determineMeasurements(canvas.getWidth(),canvas.getHeight());
            initPaintComp();
        }

        for (int i = 0; i < 8; i++) {
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.RED);
//            canvas.drawLine(i*elemRectSizeX,0,i*elemRectSizeX,elemRectSizeY,p);
            if (i < 7){
                canvas.drawRect(i*linkingRectSizeX+linkingRectSizeX/2,elemRectSizeY/2 - linkingRectSizeY,i*linkingRectSizeX+linkingRectSizeX/2 + linkingRectSizeX,elemRectSizeY/2 + linkingRectSizeY,p);
            }
            canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,circleRadius,donePaint);
            canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,blurRadius,doneBlurPaint);
        }

    }

    private void determineMeasurements(int width, int height){
        elemRectSizeX = width/8;
        elemRectSizeY = height;
        if (elemRectSizeX >= elemRectSizeY){
            float fCircleRadius = (elemRectSizeY/2) * 0.75f;
            circleRadius = (int)fCircleRadius;
        }else {
            float fCircleRadius = (elemRectSizeX/2) * 0.75f;
            circleRadius = (int)fCircleRadius;
        }
        linkingRectSizeX = elemRectSizeX;
        float fLinkingRectSizeY = circleRadius*0.25f;
        linkingRectSizeY = (int)fLinkingRectSizeY;
//        String elemRectSizes ="elemRectSizeX: " + elemRectSizeX + ", elemRectSizeY: " + elemRectSizeY;
//        String circleRad ="circleRadius: " + circleRadius;
//        String linkingRectSizes ="linkingRectSizeX: " + linkingRectSizeX + ", linkingRectSizeY: " + linkingRectSizeY;
//
//        Log.d("sizesLog",elemRectSizes);
//        Log.d("sizesLog",circleRad);
//        Log.d("sizesLog",linkingRectSizes);
    }

    private void initPaintComp(){
        blurRadius = circleRadius*0.7f;

        notCompletedPaint = new Paint();
        notCompletedPaint.setColor(Color.parseColor("#ef001c")); //red
        notCompletedPaint.setAntiAlias(true);

        inProcessPaint = new Paint();
        inProcessPaint.setColor(Color.parseColor("#c9d41b")); // yellow
        inProcessPaint.setAntiAlias(true);

        donePaint = new Paint();
        donePaint.setColor(Color.parseColor("#09c804")); //green
        donePaint.setAntiAlias(true);

        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL);

        notCompletedBlurPaint = new Paint();
        notCompletedBlurPaint.setMaskFilter(blurMaskFilter);
        notCompletedBlurPaint.setColor(Color.parseColor("#ffb1fc")); //pink blur

        inProcessBlurPaint = new Paint();
        inProcessBlurPaint.setMaskFilter(blurMaskFilter);
        inProcessBlurPaint.setColor(Color.parseColor("#c5e0f4")); // light blue blur

        doneBlurPaint = new Paint();
        doneBlurPaint.setMaskFilter(blurMaskFilter);
        doneBlurPaint.setColor(Color.parseColor("#d0fdff")); // light blue blur
    }
}
