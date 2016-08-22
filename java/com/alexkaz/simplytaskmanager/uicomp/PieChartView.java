package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChartView extends View {

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);

//        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,38,p);

        p.setColor(Color.GREEN);

        RectF rectF = new RectF(canvas.getWidth()/2-38,canvas.getHeight()/2-38,canvas.getWidth()/2+38,canvas.getHeight()/2+38);

        p.setColor(Color.GREEN);
        canvas.drawArc(rectF,180,-170,true,p);

        p.setColor(Color.YELLOW);
        canvas.drawArc(rectF,10,-70,true,p);


    }
}
