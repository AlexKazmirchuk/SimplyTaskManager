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


    public TaskIndictor() {
    }


    public void draw(Canvas canvas){
        canvas.drawColor(Color.GREEN);
        if (measureFlag){determineMeasurements(canvas.getWidth(),canvas.getHeight());}

//        int dividerPCoordX = canvas.getWidth()/8;

//        for (int i = 0; i < 8; i++) {
//            Paint p = new Paint();
//            p.setAntiAlias(true);
//            p.setColor(Color.RED);
//            canvas.drawLine(i*dividerPCoordX,0,i*dividerPCoordX,canvas.getHeight(),p);
//            if (i < 7){
//                canvas.drawRect(i*dividerPCoordX+dividerPCoordX/2,canvas.getHeight()/2 - 2,i*dividerPCoordX+dividerPCoordX/2 + dividerPCoordX,canvas.getHeight()/2 + 2,p);
//            }
//
//            canvas.drawCircle(i*dividerPCoordX + dividerPCoordX/2,canvas.getHeight()/2,10,p);
////            canvas.drawCircle(i*dividerPCoordX + dividerPCoordX/2,canvas.getHeight()/2,16,p);
//        }

        for (int i = 0; i < 8; i++) {
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            canvas.drawLine(i*elemRectSizeX,0,i*elemRectSizeX,elemRectSizeY,p);
            if (i < 7){
                canvas.drawRect(i*elemRectSizeX+elemRectSizeX/2,elemRectSizeY/2 - 2,i*elemRectSizeX+elemRectSizeX/2 + elemRectSizeX,elemRectSizeY/2 + 2,p);
            }
            canvas.drawCircle(i*elemRectSizeX + elemRectSizeX/2,elemRectSizeY/2,10,p);
        }

    }

    private void determineMeasurements(int width, int height){
        elemRectSizeX = width/8;
        elemRectSizeY = height;

        if (elemRectSizeX >= elemRectSizeY){
            // визначаємо по висоті
            // circleRad = elemRectSizeY
            float fCircleRadius = (elemRectSizeX/2) * 0.625f;
            circleRadius = (int)fCircleRadius;
        }else {
            // визначаємо по ширині
            // circleRad = elemRectSizeX
            float fCircleRadius = (elemRectSizeX/2) * 0.625f;
            circleRadius = (int)fCircleRadius;
        }

        linkingRectSizeX = elemRectSizeX;
        linkingRectSizeY = circleRadius/3;

        String elemRectSizes ="elemRectSizeX: " + elemRectSizeX + ", elemRectSizeY: " + elemRectSizeY;
        String circleRad ="circleRadius: " + circleRadius;
        String linkingRectSizes ="linkingRectSizeX: " + linkingRectSizeX + ", linkingRectSizeY: " + linkingRectSizeY;

        Log.d("sizesLog",elemRectSizes);
        Log.d("sizesLog",circleRad);
        Log.d("sizesLog",linkingRectSizes);

    }
}
