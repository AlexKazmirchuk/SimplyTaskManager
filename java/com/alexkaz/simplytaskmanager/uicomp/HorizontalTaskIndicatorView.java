package com.alexkaz.simplytaskmanager.uicomp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HorizontalTaskIndicatorView extends View {

    private boolean measureFlag = true;

    private int circleRadius;
    private int circlePosX;
    private int circlePosY;

    private int paddingTop = 16;
    private int bgRectSizeX;

    private int linkingRectSize;



    public HorizontalTaskIndicatorView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public HorizontalTaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(measureFlag){
            determineMeasurements(canvas.getWidth(),canvas.getHeight());
            measureFlag = false;
        }

//        Paint p = new Paint();
//        p.setColor(Color.GRAY);
//
//        Rect rect = new Rect(0,0,(int)(canvas.getWidth()*0.9f),canvas.getHeight());
//        canvas.drawRect(rect,p);
//
//        p.setColor(Color.DKGRAY);
//        int buf = (int)(canvas.getWidth()*0.9f);
//        canvas.drawRect(buf/2,31-3,canvas.getWidth(),31+3,p); // to right
//
//        p.setColor(Color.GREEN);
//        canvas.drawRect(buf/2-3,0,buf/2+3,31,p); //to top
//        canvas.drawRect(buf/2-3,31,buf/2+3,canvas.getHeight(),p); // to bottom
////        canvas.drawCircle(25,30,15,p);
//
//        canvas.drawCircle(buf/2,31,14,p);
//
//        p.setColor(Color.BLACK);
////        canvas.drawLine(0,8,canvas.getWidth(),8,p);
//        canvas.drawLine(0,15,canvas.getWidth(),15,p);
//
//        canvas.drawLine(0,45,canvas.getWidth(),45,p);
//        canvas.drawLine(0,canvas.getHeight()-7,canvas.getWidth(),canvas.getHeight()-7,p);

        ///////////////////////////////////////
        Paint myPaint = new Paint();
        myPaint.setColor(Color.GRAY);

        Rect rect1 = new Rect(0,0,bgRectSizeX,canvas.getHeight());
        canvas.drawRect(rect1,myPaint);

        myPaint.setColor(Color.DKGRAY);
        canvas.drawRect(circlePosX,circlePosY-linkingRectSize,canvas.getWidth(),circlePosY+linkingRectSize,myPaint); // to right
        myPaint.setColor(Color.GREEN);
        canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,myPaint); //to top

        canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),myPaint); // to bottom

        canvas.drawCircle(circlePosX,circlePosY,circleRadius,myPaint);

        ///////////////////////////////////////

    }

    private void determineMeasurements(int width, int height){
        Log.d("canvasSizes",width + " " + height); // 60x52

        if (width>=height){
            circleRadius = (int)(height*0.5385f)/2; // 14
        } else {
            circleRadius = (int)(width*0.4667f)/2; // 14
        }

        bgRectSizeX = (int)(width*0.9f);

        circlePosX = (int) (width*0.9f)/2;
        circlePosY = paddingTop + circleRadius;

        linkingRectSize = (int) (circleRadius*0.2143f); // 3
    }
}
