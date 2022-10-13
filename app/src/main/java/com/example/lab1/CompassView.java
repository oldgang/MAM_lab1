package com.example.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class CompassView extends View {

    Paint paint;
    Canvas canvas;
    float radius = 0f;
    int width = 0;
    float azimuth = 0f;

    public void set_azimuth(float value){
        this.azimuth = value;
        invalidate();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,0,0);
        paint.setStrokeWidth(5);
        canvas = new Canvas();
        this.drawCompass(canvas, paint);
    }

    public void drawCompass(Canvas canvas, Paint paint){
        float x = getWidth();
        float y = getHeight();
        Path path = new Path();
        path.moveTo(x/2f, y/2f);
        path.lineTo(x/2f - radius*0.15f, y/2f); // bottom left
        path.lineTo(x/2f, y/2f + radius*0.7f); // top
        path.lineTo(x/2f + radius*0.15f, y/2f); // bottom right
        path.lineTo(x/2f, y/2f);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth();
        float y = getHeight();
        if(radius == 0f) { radius = x/2f;}
        canvas.save();
        canvas.rotate(azimuth, x/2f, y/2f);
        drawCompass(canvas, paint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = w / 2f * 0.9f;
    }
}
