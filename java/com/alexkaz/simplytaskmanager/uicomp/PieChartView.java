package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PieChartView extends View {

    private boolean measureFlag = true;

    private int pieChartRadius;
    private int pieChartDividerSize;

    private Paint pieChartPaint;
    private Paint pieChartDividerPaint;
    private RectF pieChartAvailableSpace;

    private int amountOfNotCompleted;
    private int amountOfInProcess;
    private int amountOfDone;

    private TaskStatus[] taskStatuses = new TaskStatus[8];

    public PieChartView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initComps();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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

        for (TaskStatus taskStatus : taskStatuses) {
            switch (taskStatus) {
                case NOT_COMPLITED:
                    amountOfNotCompleted++;
                    break;
                case IN_PROCESS:
                    amountOfInProcess++;
                    break;
                case DONE:
                    amountOfDone++;
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(measureFlag){
            determineMeasurements(canvas.getWidth(),canvas.getHeight());
            initGraphicComp();
            measureFlag = false;
        }
//        Paint p = new Paint();
//        p.setColor(Color.RED);
//        p.setAntiAlias(true);
//        p.setColor(Color.GREEN);
////        RectF rectF = new RectF(canvas.getWidth()/2-40,canvas.getHeight()/2-40,canvas.getWidth()/2+40,canvas.getHeight()/2+40);
//        RectF rectF = new RectF(canvas.getWidth()/2-pieChartRadius,canvas.getHeight()/2-pieChartRadius,canvas.getWidth()/2+pieChartRadius,canvas.getHeight()/2+pieChartRadius);
//
//        p.setColor(Color.GREEN);
//        canvas.drawArc(rectF,270,-120,true,p);
//
//        p.setColor(Color.YELLOW);
//        canvas.drawArc(rectF,150,-120,true,p);
//
//        p.setColor(Color.RED);
//        canvas.drawArc(rectF,30,-120,true,p);
//
//
//        Paint p1 = new Paint();
//        p1.setStyle(Paint.Style.STROKE);
//        p1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        p1.setAntiAlias(true);
////        p1.setStrokeWidth(4);
//        p1.setStrokeWidth(pieChartDividerSize);
//        p1.setColor(Color.BLACK);
//
//        canvas.drawArc(rectF,270,-120,true,p1);
//        canvas.drawArc(rectF,150,-120,true,p1);
//        canvas.drawArc(rectF,30,-120,true,p1);

        //////////////////////////////
        ////////////////////////////////////////////
//        pieChartPaint.setColor(Color.GREEN);
//        canvas.drawArc(pieChartAvailableSpace,270,-120,true,pieChartPaint);
//        pieChartPaint.setColor(Color.YELLOW);
//        canvas.drawArc(pieChartAvailableSpace,150,-120,true,pieChartPaint);
//        pieChartPaint.setColor(Color.RED);
//        canvas.drawArc(pieChartAvailableSpace,30,-120,true,pieChartPaint);
//
//        canvas.drawArc(pieChartAvailableSpace,270,-120,true,pieChartDividerPaint);
//        canvas.drawArc(pieChartAvailableSpace,150,-120,true,pieChartDividerPaint);
//        canvas.drawArc(pieChartAvailableSpace,30,-120,true,pieChartDividerPaint);

        ////////////////////////////////////////
        pieChartPaint.setColor(Color.GREEN);
        canvas.drawArc(pieChartAvailableSpace,270,-(45*amountOfDone),true,pieChartPaint);
        pieChartPaint.setColor(Color.YELLOW);
        canvas.drawArc(pieChartAvailableSpace,270 - (45*amountOfDone),-(45*amountOfInProcess),true,pieChartPaint);
        pieChartPaint.setColor(Color.RED);
        canvas.drawArc(pieChartAvailableSpace,270 - (45*amountOfDone + 45*amountOfInProcess),-(45*amountOfNotCompleted),true,pieChartPaint);

        canvas.drawArc(pieChartAvailableSpace,270,-(45*amountOfDone),true,pieChartDividerPaint);
        canvas.drawArc(pieChartAvailableSpace,270 - (45*amountOfDone),-(45*amountOfInProcess),true,pieChartDividerPaint);
        canvas.drawArc(pieChartAvailableSpace,270 - (45*amountOfDone + 45*amountOfInProcess),-(45*amountOfNotCompleted),true,pieChartDividerPaint);
    }

    private void determineMeasurements(int width, int height){
        if (width >= height){
            pieChartRadius =(int)((height*0.9524f)/2);  //84 * 84;
        } else {
            pieChartRadius = (int)((width*0.9524f)/2);  //84 * 84;
        }
        pieChartDividerSize = (int)(pieChartRadius*0.1f);
        pieChartAvailableSpace = new RectF(width/2-pieChartRadius,height/2-pieChartRadius,width/2+pieChartRadius,height/2+pieChartRadius);
    }

    private void initGraphicComp(){
        pieChartPaint = new Paint();
        pieChartPaint.setColor(Color.RED);
        pieChartPaint.setAntiAlias(true);

        pieChartDividerPaint = new Paint();
        pieChartDividerPaint.setStyle(Paint.Style.STROKE);
        pieChartDividerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        pieChartDividerPaint.setAntiAlias(true);
        pieChartDividerPaint.setStrokeWidth(pieChartDividerSize);
        pieChartDividerPaint.setColor(Color.BLACK);
    }
}
