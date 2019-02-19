package com.germanlizondo.ruleta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.view.View;

class Lienzo extends View {

    private Paint paint;
    public Lienzo(Context context) {
        super(context);

        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(700, 700, 500, paint);
    }
}


