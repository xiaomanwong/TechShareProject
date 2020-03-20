package com.zbd.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2017/11/30.
 * 肌肤湿度view
 */

public class U_U9SkinHumView extends View {
    private float ys, ye, yi;
    private float xs, xe, xi;
    //刻度
    private float scaleY, scaleX;
    //内边距
    private float pl, pt, pr, pb;
    //刻度区域
    private float lsw, tsw, rsw, bsw;
    //View 的宽高
    private float mWidth, mHeight;
    //像素密度
    private float densityY, densityX;
    private float axisXWWidth;
    //箭头的边
    private float arrowW;
    private float arrowH;
    /**
     * 画笔
     * t:文本
     * a:轴
     * s:刻度
     * mSwitchBtn_bg:背景
     * l:线
     */
    private Paint xtsp, ytsp, xasp, yasp, bgp, lp, lbgp;
    /**
     * 折线轮廓
     * 折线背景
     */
    private Path linePath, lineBgPath, pointPath;
    /**
     * 渐变背景色值
     */
    private int bgGradientColorStart = Color.parseColor("#fb8394");
    private int bgGradientColorEnd = Color.parseColor("#ffffff");
    /**
     * 坐标刻度
     */
    private List<String> xScaleTexts;
    private List<String> yScaleTexts;
    /**
     * 是否自动填充横轴的刻度
     */
    private String mAtuoXScaleTexts;
    private boolean isAtuoXScaleTexts = true;


    public U_U9SkinHumView(Context context) {
        super(context);
        initConfig();
    }

    public U_U9SkinHumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initConfig();
    }


    public U_U9SkinHumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    private void initConfig() {
        densityX = DensityUtils.getW(getContext()) / 375f;
        axisXWWidth = DensityUtils.getW(getContext()) * 2 / 375f;
        densityY = DensityUtils.getH(getContext()) / 667f;

        arrowW = densityX * 9;
        arrowH = densityY * 10;

        xtsp = new Paint();
        xtsp.setTextSize(densityY * 9);
        xtsp.setAntiAlias(true);
        xtsp.setColor(Color.parseColor("#999999"));
        xtsp.setTextAlign(Paint.Align.CENTER);

        ytsp = new Paint();
        ytsp.setTextSize(densityY * 9);
        ytsp.setAntiAlias(true);
        ytsp.setColor(Color.parseColor("#999999"));
        ytsp.setTextAlign(Paint.Align.LEFT);

        yasp = xasp = new Paint();
        xasp.setColor(Color.parseColor("#fb8394"));
        xasp.setAntiAlias(true);
        xasp.setTextSize(densityY * 9);
        xasp.setTextAlign(Paint.Align.CENTER);
        xasp.setStrokeWidth(axisXWWidth);
        xasp.setStyle(Paint.Style.FILL);

        bgp = new Paint();
        bgp.setColor(Color.parseColor("#f5f5f5"));
        bgp.setStrokeWidth(densityX);


        lp = new Paint();
        lp.setColor(Color.parseColor("#FFA71D"));
        lp.setStrokeWidth(densityX);
        lp.setStyle(Paint.Style.STROKE);

        linePath = new Path();
        lineBgPath = new Path();
        pointPath = new Path();
    }

    private void testqq() {
        setYsAndYe(0, 100, 10);
        setSW(30, 30, 30, 30);
        setYScaleTexts("%", "100", "90", "80", "70", "60", "50", "40", "30", "20", "10", "0");
//        setIsAtuoXScaleTexts("(h)");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    private void initScale() {
        scaleY = (mHeight - pt - pb - tsw - bsw) * yi / (((ye - ys) == 0) ? 1 : (ye - ys));
        scaleX = (mWidth - pl - pr - lsw - rsw) * xi / (((xe - xs) == 0) ? 1 : (xe - xs));
        if (scaleY == 0) {
            scaleY = (mHeight - pt - pb - tsw - bsw);
        }
        if (scaleX == 0) {
            scaleX = (mHeight - pt - pb - tsw - bsw);
        }
    }

    private void setData() {
        if (data == null || data.size() == 0) {
            return;
        }
        linePath.reset();
        lineBgPath.reset();
        pointPath.reset();

        lineBgPath.moveTo(pl + lsw, mHeight - pb - bsw - axisXWWidth);
        if (isAtuoXScaleTexts) {
            if (xScaleTexts == null) {
                xScaleTexts = new ArrayList<>();
            }
            xScaleTexts.clear();
            xScaleTexts.add(mAtuoXScaleTexts + "");
        }

        float coordinateValueY;
        //y轴出现的最大坐标值
        float maxYValue = mHeight - pb - bsw;

        setXsAndXe(0, data.size() - 1, 1);
        initScale();

        for (int i = 0; i < data.size(); i++) {
            if (xScaleTexts != null && isAtuoXScaleTexts) xScaleTexts.add(i + "");
            coordinateValueY = getCoordinateValueY(data.get(i));
            if (maxYValue > coordinateValueY) {
                maxYValue = coordinateValueY;
            }

            if (i == 0) {
                linePath.moveTo(pl + lsw + i * scaleX, coordinateValueY);
                lineBgPath.lineTo(pl + lsw + i * scaleX, coordinateValueY);
            } else {
                linePath.lineTo(pl + lsw + i * scaleX, coordinateValueY);
                lineBgPath.lineTo(pl + lsw + i * scaleX, coordinateValueY);

                if (i == data.size() - 1) {
                    lineBgPath.lineTo(pl + lsw + i * scaleX, mHeight - pb - bsw - axisXWWidth);
                    lineBgPath.lineTo(pl + lsw, mHeight - pb - bsw - axisXWWidth);
                }
            }
        }

        lbgp = new Paint();
        lbgp.setStyle(Paint.Style.FILL);
        LinearGradient lg = new LinearGradient(0, maxYValue, 0, mHeight - pb - bsw, bgGradientColorStart, bgGradientColorEnd, Shader.TileMode.MIRROR); //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点，最后参数为平铺方式，这里设置为镜像
        lbgp.setShader(lg);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setData();
        canvas.drawColor(Color.WHITE);

        drawGraduationLine(canvas);
        drawGraduationText(canvas);
        drawArrow(canvas);
        if (lbgp != null) canvas.drawPath(lineBgPath, lbgp);
        //canvas.drawPath(linePath, lp);
    }

    /**
     * 画箭头
     */
    private void drawArrow(Canvas canvas) {
        Path yasPath = new Path();
        yasPath.moveTo(pl + lsw, pt);
        yasPath.lineTo(pl + lsw + arrowW / 2, pt + arrowH);
        yasPath.lineTo(pl + lsw, pt + arrowH * 0.8f);
        yasPath.lineTo(pl + lsw - arrowW / 2, pt + arrowH);
        yasPath.lineTo(pl + lsw, pt);
        canvas.drawPath(yasPath, yasp);


        Path xasPath = new Path();
        xasPath.moveTo(mWidth - pr, mHeight - pb - bsw);
        xasPath.lineTo(mWidth - pr - arrowH, mHeight - pb - bsw + arrowW / 2);
        xasPath.lineTo(mWidth - pr - arrowH * 0.8f, mHeight - pb - bsw);
        xasPath.lineTo(mWidth - pr - arrowH, mHeight - pb - bsw - arrowW / 2);
        xasPath.lineTo(mWidth - pr, mHeight - pb - bsw);
        canvas.drawPath(xasPath, xasp);
    }

    /**
     * 画刻度线
     */
    private void drawGraduationLine(Canvas canvas) {
        //竖线
        for (float i = pl + lsw + scaleX; i <= mWidth - pr - rsw + densityX; i += scaleX) {
            canvas.drawLine(i, pt + tsw, i, mHeight - pb - bsw, bgp);
        }
        //横线
        for (float i = mHeight - pb - bsw - scaleY; i >= pt + tsw - densityY; i -= scaleY) {
            canvas.drawLine(pl + lsw, i, mWidth - pr - rsw, i, bgp);
        }
        //y
        canvas.drawLine(pl + lsw, pt + arrowH * 0.8f, pl + lsw, mHeight - pb - bsw, yasp);
        //x
        canvas.drawLine(pl + lsw - axisXWWidth / 2, mHeight - pb - bsw, mWidth - pr - arrowH * 0.8f, mHeight - pb - bsw, xasp);
    }

    /**
     * 画刻值
     */
    private void drawGraduationText(Canvas canvas) {
        int ySize = (yScaleTexts == null ? 0 : yScaleTexts.size());
        Paint.FontMetrics yFontMetrics = yasp.getFontMetrics();
        float yTop = yFontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float yBottom = yFontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        Rect rect = getTextSize(yasp, "0");
        for (int i = 0; i < ySize; i++) {
            String st = yScaleTexts.get(i);
            if (i == 0) {
                canvas.drawText(st, pl, rect.height() / 2 + pt - yTop / 2 - yBottom / 2 + arrowH, ytsp);
            } else if (i == ySize) {
                canvas.drawText(st, pl, rect.height() / 2 + pt + tsw + (i - 1) * (mHeight - pt - tsw - pb - bsw) / (ySize - 2) - yTop / 2 - yBottom / 2, yasp);
            } else {
                canvas.drawText(st, pl, rect.height() / 2 + pt + tsw + (i - 1) * (mHeight - pt - tsw - pb - bsw) / (ySize - 2) - yTop / 2 - yBottom / 2, ytsp);
            }
        }


        int xSize = (xScaleTexts == null ? 0 : xScaleTexts.size());
        Paint.FontMetrics xFontMetrics = yasp.getFontMetrics();
        float xTop = xFontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float XBottom = xFontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        for (int i = 0; i < xSize; i++) {
            String st = xScaleTexts.get(i);
            if (i == xSize - 1) {
                //最后的刻度
                Rect rect1 = getTextSize(xasp, st);
                //单位
                Rect rect2 = getTextSize(xtsp, mAtuoXScaleTexts);
                //画最后的刻度
                canvas.drawText(st, pl + lsw + i * (mWidth - pl - lsw - pr - rsw) / (xSize - 1), mHeight - pb - bsw / 2 - xTop / 2 - XBottom / 2, xasp);
                canvas.drawText(mAtuoXScaleTexts, pl + lsw + i * (mWidth - pl - lsw - pr - rsw) / (xSize - 1) + rect1.width() / 2 + densityX * 5, mHeight - pb - bsw / 2 - xTop / 2 - XBottom / 2, xtsp);
            }else {
                canvas.drawText(st, pl + lsw + i * (mWidth - pl - lsw - pr - rsw) / (xSize - 1), mHeight - pb - bsw / 2 - xTop / 2 - XBottom / 2, xtsp);
            }
        }
    }

    private Rect getTextSize(Paint paint, String text) {
        Rect reat = new Rect();
        if (!TextUtils.isEmpty(text)) {
            paint.getTextBounds(text, 0, text.length(), reat);
        }
        return reat;
    }

    /**
     * 清楚数据
     */
    public void clear() {
        linePath.reset();
        lineBgPath.reset();
        pointPath.reset();
        invalidate();
    }

    /**
     * 根据数值转换成坐标值
     */
    private float getCoordinateValueY(Float y) {
        if (y == null) {
            return mHeight - pb - bsw - axisXWWidth;
        }
        return mHeight - pb - bsw - axisXWWidth - (mHeight - pb - pt - bsw - tsw) * (y - ys) / (ye - ys);
    }

    List<Float> data;
    /**
     * 对外的方法-----------------------------------------------------------------------------------
     */
    /**
     * 填充数据
     */
    public U_U9SkinHumView setData(List<Float> data) {
        this.data = data;
        invalidate();
        return this;
    }

    public U_U9SkinHumView setBgGradientColorStart(int bgGradientColorStart) {
        this.bgGradientColorStart = bgGradientColorStart;
        return this;
    }

    public U_U9SkinHumView setBgGradientColorEnd(int bgGradientColorEnd) {
        this.bgGradientColorEnd = bgGradientColorEnd;
        return this;
    }

    public U_U9SkinHumView setYsAndYe(float ys, float ye, float yi) {
        this.ys = ys;
        this.ye = ye;
        this.yi = yi;
        return this;
    }

    public U_U9SkinHumView setXsAndXe(float xs, float xe, float xi) {
        this.xs = xs;
        this.xe = xe;
        this.xi = xi;
        return this;
    }

    public U_U9SkinHumView setSW(float lsw, float tsw, float rsw, float bsw) {
        this.lsw = lsw;
        this.tsw = tsw;
        this.rsw = rsw;
        this.bsw = bsw;
        return this;
    }

    public U_U9SkinHumView setP(float l, float t, float r, float b) {
        this.pl = l;
        this.pt = t;
        this.pr = r;
        this.pb = b;
        return this;
    }

    public U_U9SkinHumView setXScaleTexts(List<String> xScaleTexts) {
        this.xScaleTexts = xScaleTexts;
        return this;
    }

    public U_U9SkinHumView setXScaleTexts(String... xScaleTexts) {
        List<String> list = new ArrayList<>();
        for (String xScaleText : xScaleTexts) {
            list.add(xScaleText);
        }
        setXScaleTexts(list);
        return this;
    }

    public U_U9SkinHumView setYScaleTexts(List<String> yScaleTexts) {
        this.yScaleTexts = yScaleTexts;
        return this;
    }

    public U_U9SkinHumView setYScaleTexts(String... yScaleTexts) {
        List<String> list = new ArrayList<>();
        for (String yScaleText : yScaleTexts) {
            list.add(yScaleText);
        }
        setYScaleTexts(list);
        return this;
    }

    public U_U9SkinHumView setIsAtuoXScaleTexts(boolean isAtuoXScaleTexts) {
        this.isAtuoXScaleTexts = isAtuoXScaleTexts;
        return this;
    }

    public U_U9SkinHumView setAtuoXScaleTexts(String atuoXScaleTexts) {
        this.mAtuoXScaleTexts = atuoXScaleTexts;
        return this;
    }

    public U_U9SkinHumView setXASPColor(int color) {
        xasp.setColor(color);
        return this;
    }
}
