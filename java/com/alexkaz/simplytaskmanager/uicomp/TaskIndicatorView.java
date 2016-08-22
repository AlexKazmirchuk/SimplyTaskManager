package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TaskIndicatorView extends View {

    private TaskIndictor taskIndictor;

    public TaskIndicatorView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        taskIndictor = new TaskIndictor();
    }

    public TaskIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        taskIndictor = new TaskIndictor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String result = "width - " + canvas.getWidth() + ", " + "height - " + canvas.getHeight(); // 258 27
        Log.d("myLog", result);
        taskIndictor.draw(canvas);
//        canvas.drawColor(Color.GREEN);
//        int dividerPCoordX = canvas.getWidth()/8;
//
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
//
//        }

//        int border = 4;
//        Paint p = new Paint();
//        p.setColor(Color.GREEN);
//        canvas.drawRect(border, border, canvas.getWidth() - border, canvas.getHeight() - border, p);

    }
}
