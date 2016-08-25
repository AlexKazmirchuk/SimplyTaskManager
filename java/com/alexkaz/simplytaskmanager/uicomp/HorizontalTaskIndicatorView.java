package com.alexkaz.simplytaskmanager.uicomp;


import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HorizontalTaskIndicatorView extends View {

    private boolean measureFlag = true;
    private TaskStatus currentStatus = TaskStatus.NOT_COMPLITED;
    private TaskStatus previousStatus;
    private boolean last = false;

    private int circleRadius;
    private int circlePosX;
    private int circlePosY;

    private int paddingTop = 16;
    private int bgRectSizeX;

    private int linkingRectSize;

    private Paint topRectPaint;
    private Paint linkingPaint;
    private Paint notCompletedPaint;
    private Paint inProcessPaint;
    private Paint donePaint;

    private Paint bgPaint;
    private Paint notCompletedBlurPaint;
    private Paint inProcessBlurPaint;
    private Paint doneBlurPaint;
    private float blurRadius;

    private Rect bgRect;


    public HorizontalTaskIndicatorView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setStatus(TaskStatus.DONE,TaskStatus.DONE,false);
    }

    public HorizontalTaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setStatus(TaskStatus.DONE,TaskStatus.DONE,false);
    }

    public void setStatus(TaskStatus current, TaskStatus previous, boolean last){
        this.currentStatus = current;
        this.previousStatus = previous;
        this.last = last;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(measureFlag){
            determineMeasurements(canvas.getWidth(),canvas.getHeight());
            initPaintComp();
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


//        Paint myPaint = new Paint();
//        myPaint.setColor(Color.GRAY);
//
//        Rect rect1 = new Rect(0,0,bgRectSizeX,canvas.getHeight());
//        canvas.drawRect(rect1,myPaint);
//
//        myPaint.setColor(Color.DKGRAY);
//        canvas.drawRect(circlePosX,circlePosY-linkingRectSize,canvas.getWidth(),circlePosY+linkingRectSize,myPaint); // to right
//        myPaint.setColor(Color.GREEN);
//        canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,myPaint); //to top
//
//        canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),myPaint); // to bottom
//
//        canvas.drawCircle(circlePosX,circlePosY,circleRadius,myPaint);

        ///////////////////////////////////////


        ///////////////////////////////////////
        ///////////////////////////////////////
//        canvas.drawRect(bgRect,bgPaint);

//        canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,topRectPaint); //to top



//        if (!last){
//            canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),donePaint); // to bottom
//        }
//        canvas.drawCircle(circlePosX,circlePosY,circleRadius,inProcessPaint);
//        canvas.drawCircle(circlePosX,circlePosY,blurRadius,inProcessBlurPaint);
        ///////////////////////////////////////
        ///////////////////////////////////////
        canvas.drawRect(circlePosX,circlePosY-linkingRectSize,canvas.getWidth(),circlePosY+linkingRectSize,linkingPaint); // to right
        if(previousStatus != null){
            switch (previousStatus){
                case NOT_COMPLITED:
                    canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,notCompletedPaint); //to top
                    break;
                case IN_PROCESS:
                    canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,inProcessPaint); //to top
                    break;
                case DONE:
                    canvas.drawRect(circlePosX-linkingRectSize,0,circlePosX+linkingRectSize,circlePosY,donePaint); //to top
                    break;
            }
        }
        switch (currentStatus){
            case NOT_COMPLITED:
                if (!last){
                    canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),notCompletedPaint); // to bottom
                }
                canvas.drawCircle(circlePosX,circlePosY,circleRadius,notCompletedPaint);
                canvas.drawCircle(circlePosX,circlePosY,blurRadius,notCompletedBlurPaint);
                break;
            case IN_PROCESS:
                if (!last){
                    canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),inProcessPaint); // to bottom
                }
                canvas.drawCircle(circlePosX,circlePosY,circleRadius,inProcessPaint);
                canvas.drawCircle(circlePosX,circlePosY,blurRadius,inProcessBlurPaint);
                break;
            case DONE:
                if (!last){
                    canvas.drawRect(circlePosX-linkingRectSize,circlePosY,circlePosX+linkingRectSize,canvas.getHeight(),donePaint); // to bottom
                }
                canvas.drawCircle(circlePosX,circlePosY,circleRadius,donePaint);
                canvas.drawCircle(circlePosX,circlePosY,blurRadius,doneBlurPaint);
                break;
        }
    }

    private void determineMeasurements(int width, int height){
        Log.d("canvasSizes",width + " " + height); // 60x52

        if (width>=height){
            circleRadius = (int)(height*0.5385f)/2; // 14
        } else {
            circleRadius = (int)(width*0.4667f)/2; // 14
        }

        blurRadius = circleRadius*0.7f;
        bgRectSizeX = (int)(width*0.9f);

        circlePosX = (int) (width*0.9f)/2;
        circlePosY = paddingTop + circleRadius;

        linkingRectSize = (int) (circleRadius*0.2143f); // 3

        bgRect = new Rect(0,0,bgRectSizeX,height);
    }

    private void initPaintComp(){
        bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#d6d7d6"));

        linkingPaint = new Paint();
        linkingPaint.setColor(Color.parseColor("#4b288c"));

        topRectPaint = new Paint();
        topRectPaint.setColor(Color.parseColor("#09c804"));

        notCompletedPaint = new Paint();
        notCompletedPaint.setAntiAlias(true);
        notCompletedPaint.setColor(Color.parseColor("#ef001c"));

        inProcessPaint = new Paint();
        inProcessPaint.setAntiAlias(true);
        inProcessPaint.setColor(Color.parseColor("#c9d41b"));

        donePaint = new Paint();
        donePaint.setAntiAlias(true);
        donePaint.setColor(Color.parseColor("#09c804"));

        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL);

        notCompletedBlurPaint = new Paint();
        notCompletedBlurPaint.setMaskFilter(blurMaskFilter);
        notCompletedBlurPaint.setColor(Color.parseColor("#ffa3d5")); //pink blur

        inProcessBlurPaint = new Paint();
        inProcessBlurPaint.setMaskFilter(blurMaskFilter);
        inProcessBlurPaint.setColor(Color.parseColor("#ffe1b7")); // light blue blur

        doneBlurPaint = new Paint();
        doneBlurPaint.setMaskFilter(blurMaskFilter);
        doneBlurPaint.setColor(Color.parseColor("#c4e5fd")); // light blue blur
    }
}
