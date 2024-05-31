package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressView extends View {

    private int progress = 0;
    private int maxProgress = 100;
    private int strokeWidth = 10;
    private int progressColor = 0xFF009688; // Green color
    private Paint paint;
    private RectF oval;

    public CircularProgressView(Context context) {
        super(context);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(progressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (oval == null) {
            int width = getWidth();
            int height = getHeight();
            int size = Math.min(width, height);
            int left = (width - size) / 2;
            int top = (height - size) / 2;
            int right = left + size;
            int bottom = top + size;
            oval = new RectF(left, top, right, bottom);
        }

        float sweepAngle = 360 * progress / (float) maxProgress;
        canvas.drawArc(oval, -90, sweepAngle, false, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        paint.setColor(progressColor);
    }
}

