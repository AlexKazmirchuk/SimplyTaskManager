package com.alexkaz.simplytaskmanager.uicomp;


import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.alexkaz.simplytaskmanager.R;

public class VerticalTaskIndicatorView extends View {

    private static final float CIRCLE_RADIUS_HEIGHT_DIVIDER = 0.5385f;
    private static final float CIRCLE_RADIUS_WIDTH_DIVIDER = 0.4667f;
    private static final float BLUR_RADIUS_DIVIDER = 0.7f;
    private static final float BG_RECT_SIZE_DIVIDER = 0.9f;
    private static final float CIRCLE_POS_X_DIVIDER = 0.9f;
    private static final float LINKING_RECT_SIZE_DIVIDER = 0.2143f;
    public static final int CIRCLE_PADDING_TOP = 16;

    private TaskStatus currentStatus = TaskStatus.NOT_COMPLETED;
    private TaskStatus previousStatus;
    private boolean last = false;

    private boolean measureFlag = true;
    private int circleRadius;
    private int circlePosX;
    private int circlePosY;
    private int linkingRectSize;
    private float blurRadius;

    private Paint linkingPaint;
    private Paint notCompletedPaint;
    private Paint inProcessPaint;
    private Paint donePaint;

    private Paint notCompletedBlurPaint;
    private Paint inProcessBlurPaint;
    private Paint doneBlurPaint;

    private Rect bgRect;


    public VerticalTaskIndicatorView(Context context) {
        super(context);
        initComps();
    }

    public VerticalTaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComps();
    }

    private void initComps(){
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

        canvas.drawRect(circlePosX,circlePosY-linkingRectSize,canvas.getWidth(),circlePosY+linkingRectSize,linkingPaint); // to right
        if(previousStatus != null){
            switch (previousStatus){
                case NOT_COMPLETED:
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
            case NOT_COMPLETED:
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
        if (width>=height){
            circleRadius = (int)(height* CIRCLE_RADIUS_HEIGHT_DIVIDER)/2; // 14
        } else {
            circleRadius = (int)(width* CIRCLE_RADIUS_WIDTH_DIVIDER)/2; // 14
        }

        blurRadius = circleRadius* BLUR_RADIUS_DIVIDER;
        int bgRectSizeX = (int) (width * BG_RECT_SIZE_DIVIDER);

        circlePosX = (int) (width* CIRCLE_POS_X_DIVIDER)/2;
        int paddingTop = CIRCLE_PADDING_TOP;
        circlePosY = paddingTop + circleRadius;

        linkingRectSize = (int) (circleRadius* LINKING_RECT_SIZE_DIVIDER); // 3

        bgRect = new Rect(0,0, bgRectSizeX,height);
    }

    private void initPaintComp(){
        Paint bgPaint = new Paint();
        bgPaint.setColor(getContext().getResources().getColor(R.color.VI_BgColor));

        linkingPaint = new Paint();
        linkingPaint.setColor(getContext().getResources().getColor(R.color.VI_LinkingRectColor));

        Paint topRectPaint = new Paint();
        topRectPaint.setColor(getContext().getResources().getColor(R.color.VI_TopRectColor));

        notCompletedPaint = new Paint();
        notCompletedPaint.setAntiAlias(true);
        notCompletedPaint.setColor(getContext().getResources().getColor(R.color.VI_NotCompletedColor));

        inProcessPaint = new Paint();
        inProcessPaint.setAntiAlias(true);
        inProcessPaint.setColor(getContext().getResources().getColor(R.color.VI_InProcessColor));

        donePaint = new Paint();
        donePaint.setAntiAlias(true);
        donePaint.setColor(getContext().getResources().getColor(R.color.VI_DoneColor));

        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL);

        notCompletedBlurPaint = new Paint();
        notCompletedBlurPaint.setMaskFilter(blurMaskFilter);
        notCompletedBlurPaint.setColor(getContext().getResources().getColor(R.color.VI_NotCompletedBlurColor));

        inProcessBlurPaint = new Paint();
        inProcessBlurPaint.setMaskFilter(blurMaskFilter);
        inProcessBlurPaint.setColor(getContext().getResources().getColor(R.color.VI_InProcessBlurColor));

        doneBlurPaint = new Paint();
        doneBlurPaint.setMaskFilter(blurMaskFilter);
        doneBlurPaint.setColor(getContext().getResources().getColor(R.color.VI_DoneBlurColor));
    }
}
