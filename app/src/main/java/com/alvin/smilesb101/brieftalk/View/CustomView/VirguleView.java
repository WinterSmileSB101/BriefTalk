package com.alvin.smilesb101.brieftalk.View.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class VirguleView extends View {
    public VirguleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        canvas.drawLine(110,40,190,80,paint);
    }
}
