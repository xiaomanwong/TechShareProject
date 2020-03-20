package com.zbd.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author wangxu
 * @date 2020/3/20
 * @Desc
 */
public class TestView extends View {
    Paint p;

    {

        p = new Paint();
    }

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);
        p.setColor(Color.parseColor("#000000"));
        p.setTextSize(100);
        p.setAntiAlias(true);


        canvas.drawText("谢谢晓雨", 100, 100, p);

        canvas.drawRect(new RectF(0, 0, 400, 400), p);
        canvas.drawArc(new RectF(0, 0, 400, 400), 0, 120, true, p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
