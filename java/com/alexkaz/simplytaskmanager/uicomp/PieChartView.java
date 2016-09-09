package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alexkaz.simplytaskmanager.R;

import java.util.ArrayList;

public class PieChartView extends View {

    public static final int MAX_DISPLAY_COUNT = 8;
    public static final int START_ANGLE = 270;
    public static final float RADIUS_HEIGHT_DIVIDER = 0.9524f;
    public static final float RADIUS_WIDTH_DIVIDER = 0.9524f;
    public static final float PIE_CHART_DIVIDER = 0.1f;
    private boolean measureFlag = true;

    private int pieChartRadius;
    private int pieChartDividerSize;

    private Paint pieChartPaint;
    private Paint pieChartDividerPaint;
    private RectF pieChartAvailableSpace;

    private int amountOfNotCompleted;
    private int amountOfInProcess;
    private int amountOfDone;

    private Context context;
    private Bitmap doneBitmap;
    private RectF doneRectF;

    private TaskStatus[] taskStatuses = new TaskStatus[MAX_DISPLAY_COUNT];
    private ArrayList<TaskStatus> statuses;

    public PieChartView(Context context) {
        super(context);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initComps();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initComps();
    }

    private void initComps() {
        doneBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.done);
        doneRectF = new RectF();
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
            statuses.add(TaskStatus.DONE);
        }

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

        float degreeInterest = 360.0f/(float)(amountOfDone + amountOfInProcess + amountOfNotCompleted);

        int doneAngle = Math.round(degreeInterest*amountOfDone);
        int inProcessAngle = Math.round(degreeInterest*amountOfInProcess);
        int notCompletedAngle = 360 - (doneAngle + inProcessAngle);

        if((amountOfDone + amountOfInProcess + amountOfNotCompleted) == 0){
            notCompletedAngle = 0;
        }

        pieChartPaint.setColor(Color.GREEN);
        canvas.drawArc(pieChartAvailableSpace,START_ANGLE,- (doneAngle),true,pieChartPaint);
        pieChartPaint.setColor(Color.YELLOW);
        canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle),-(inProcessAngle),true,pieChartPaint);
        pieChartPaint.setColor(Color.RED);
        canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle +  inProcessAngle),-(notCompletedAngle),true,pieChartPaint);

        canvas.drawArc(pieChartAvailableSpace,START_ANGLE,- (doneAngle),true,pieChartDividerPaint);
        canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle),-(inProcessAngle),true,pieChartDividerPaint);
        canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle +  inProcessAngle),-(notCompletedAngle),true,pieChartDividerPaint);

        if (amountOfNotCompleted > amountOfDone && amountOfNotCompleted > amountOfInProcess){
            pieChartPaint.setColor(Color.RED);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle +  inProcessAngle),-(notCompletedAngle),true,pieChartPaint);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle +  inProcessAngle),-(notCompletedAngle),true,pieChartDividerPaint);
        }
        if (amountOfInProcess > amountOfNotCompleted && amountOfInProcess > amountOfDone){
            pieChartPaint.setColor(Color.YELLOW);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle),-(inProcessAngle),true,pieChartPaint);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE - (doneAngle),-(inProcessAngle),true,pieChartDividerPaint);
        }
        if (amountOfDone > amountOfInProcess && amountOfDone > amountOfNotCompleted){
            pieChartPaint.setColor(Color.GREEN);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE,- (doneAngle),true,pieChartPaint);
            canvas.drawArc(pieChartAvailableSpace,START_ANGLE,- (doneAngle),true,pieChartDividerPaint);
        }

        if((amountOfDone + amountOfInProcess + amountOfNotCompleted) == amountOfDone && amountOfDone != 0){
            canvas.drawBitmap(doneBitmap,null,doneRectF, null);
        }
    }

    private void determineMeasurements(int width, int height){
        if (width >= height){
            pieChartRadius =(int)((height* RADIUS_HEIGHT_DIVIDER)/2);  //84 * 84;
        } else {
            pieChartRadius = (int)((width* RADIUS_WIDTH_DIVIDER)/2);  //84 * 84;
        }
        pieChartDividerSize = (int)(pieChartRadius* PIE_CHART_DIVIDER);
        pieChartAvailableSpace = new RectF(width/2-pieChartRadius,height/2-pieChartRadius,width/2+pieChartRadius,height/2+pieChartRadius);

        int bmpWidth = (int)(width*0.6f);
        int bmpHeight = (int)(height*0.6f);
        int bmpPosX = (width - bmpWidth)/2;
        int bmpPosY = (height - bmpHeight)/2;

        doneRectF.left = bmpPosX;
        doneRectF.top = bmpPosY;
        doneRectF.right = bmpPosX + bmpWidth;
        doneRectF.bottom = bmpPosY + bmpHeight;
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

    public void setTaskStatuses(ArrayList<TaskStatus> statuses) {
        this.statuses = statuses;
        amountOfNotCompleted = 0;
        amountOfInProcess = 0;
        amountOfDone = 0;
        for (TaskStatus taskStatus : statuses) {
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

    public void setValues(int amountOfNotCompleted, int amountOfInProcess, int amountOfDone){
        this.amountOfNotCompleted = amountOfNotCompleted;
        this.amountOfInProcess = amountOfInProcess;
        this.amountOfDone = amountOfDone;
    }
}
