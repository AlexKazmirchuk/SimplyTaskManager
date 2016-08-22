package com.alexkaz.simplytaskmanager.uicomp;

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

    private Paint notCompletedPaint;
    private Paint inProcessPaint;
    private Paint donePaint;

    private Paint notCompletedRadialGradientPaint;
    private Paint inProcessRadialGradientPaint;
    private Paint doneRadialGradientPaint;

    public TaskIndictor() {

    }


    public void draw(Canvas canvas){
        canvas.drawColor(Color.GREEN);
        if (measureFlag){determineMeasurements(canvas.getWidth(),canvas.getHeight());}

        for (int i = 0; i < 8; i++) {
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            canvas.drawLine(i*elemRectSizeX,0,i*elemRectSizeX,elemRectSizeY,p);
            if (i < 7){
                canvas.drawRect(i*linkingRectSizeX+linkingRectSizeX/2,elemRectSizeY/2 - linkingRectSizeY,i*linkingRectSizeX+linkingRectSizeX/2 + linkingRectSizeX,elemRectSizeY/2 + linkingRectSizeY,p);
            }
            canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,circleRadius,p);
        }

    }

    private void determineMeasurements(int width, int height){
        elemRectSizeX = width/8;
        elemRectSizeY = height;

        if (elemRectSizeX >= elemRectSizeY){
            // визначаємо по висоті
            // circleRad = elemRectSizeY
//            float fCircleRadius = (elemRectSizeY/2) * 0.625f;
            float fCircleRadius = (elemRectSizeY/2) * 0.75f;
            circleRadius = (int)fCircleRadius;
        }else {
            // визначаємо по ширині
            // circleRad = elemRectSizeX
//            float fCircleRadius = (elemRectSizeX/2) * 0.625f;
            float fCircleRadius = (elemRectSizeX/2) * 0.75f;
            circleRadius = (int)fCircleRadius;
        }

        linkingRectSizeX = elemRectSizeX;
        float fLinkingRectSizeY = circleRadius*0.25f;
        linkingRectSizeY = (int)fLinkingRectSizeY;
//        linkingRectSizeY = circleRadius/4;

        String elemRectSizes ="elemRectSizeX: " + elemRectSizeX + ", elemRectSizeY: " + elemRectSizeY;
        String circleRad ="circleRadius: " + circleRadius;
        String linkingRectSizes ="linkingRectSizeX: " + linkingRectSizeX + ", linkingRectSizeY: " + linkingRectSizeY;

        Log.d("sizesLog",elemRectSizes);
        Log.d("sizesLog",circleRad);
        Log.d("sizesLog",linkingRectSizes);

    }
}
