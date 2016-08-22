package com.alexkaz.simplytaskmanager.uicomp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TaskIndictor {

    public TaskIndictor() {
    }


    public void draw(Canvas canvas){
        canvas.drawColor(Color.GREEN);
        int dividerPCoordX = canvas.getWidth()/8;

        for (int i = 0; i < 8; i++) {
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            canvas.drawLine(i*dividerPCoordX,0,i*dividerPCoordX,canvas.getHeight(),p);
            if (i < 7){
                canvas.drawRect(i*dividerPCoordX+dividerPCoordX/2,canvas.getHeight()/2 - 2,i*dividerPCoordX+dividerPCoordX/2 + dividerPCoordX,canvas.getHeight()/2 + 2,p);
            }

            canvas.drawCircle(i*dividerPCoordX + dividerPCoordX/2,canvas.getHeight()/2,10,p);

        }
    }
}
