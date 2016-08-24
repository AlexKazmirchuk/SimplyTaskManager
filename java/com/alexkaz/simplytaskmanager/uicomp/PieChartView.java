package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChartView extends View {

    public PieChartView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);


        p.setColor(Color.GREEN);

        RectF rectF = new RectF(canvas.getWidth()/2-40,canvas.getHeight()/2-40,canvas.getWidth()/2+40,canvas.getHeight()/2+40);

        p.setColor(Color.GREEN);
        canvas.drawArc(rectF,270,-120,true,p);

        p.setColor(Color.YELLOW);
        canvas.drawArc(rectF,150,-120,true,p);

        p.setColor(Color.RED);
        canvas.drawArc(rectF,30,-120,true,p);


        Paint p1 = new Paint();
        p1.setStyle(Paint.Style.STROKE);
        p1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        p1.setAntiAlias(true);
        p1.setStrokeWidth(4);
        p1.setColor(Color.BLACK);

        canvas.drawArc(rectF,270,-120,true,p1);
        canvas.drawArc(rectF,150,-120,true,p1);
        canvas.drawArc(rectF,30,-120,true,p1);




    }
}
