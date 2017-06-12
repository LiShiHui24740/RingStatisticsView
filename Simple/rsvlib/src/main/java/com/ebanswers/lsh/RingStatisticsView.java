package com.ebanswers.lsh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Created by lishihui on 2016/12/26.
 */

public class RingStatisticsView extends View {
    private float[] mPercent = new float[]{0.1f, 0.2f, 0.3f, 0.4f};
    private int[] mColors = new int[]{Color.parseColor("#F9AA28"), Color.parseColor("#009752"), Color.parseColor("#2EC1FB"), Color.parseColor("#FA6723")};
    private Paint mPaint, mLinePaint, mPathPaint;
    private static final int DEFAULT_RINGWIDTH = 8;
    private static final int DEFAULT_TEXTSIZE1 = 15;
    private static final int DEFAULT_TEXTSIZE2 = 18;
    private static final int DEFAULT_TEXTSIZE3 = 15;
    private static final String DEFAULT_CENTER_TEXT = "总资产";
    private float mRingWidth = 0;
    private int textSize1 = 0, textSize2 = 0, textSize3 = 0;
    private TextPaint mTextPaint;
    private int mTextColor1 = Color.parseColor("#BBB9B8"), mTextColor2 = Color.parseColor("#FC824B");
    private RectF mRectF;
    private String mstr_total_text = DEFAULT_CENTER_TEXT;
    private String mstr_total_number = "10000.00";
    private int width, height;
    private int radius;

    public RingStatisticsView(Context context) {
        super(context);
        initSize(context);
        init();
    }

    public RingStatisticsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initSize(context);
        init();
    }

    public RingStatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initSize(context);
        init();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RingStatisticsView);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.RingStatisticsView_rsv_RingWidth) {
                mRingWidth = array.getInt(attr, dp2px(context, DEFAULT_RINGWIDTH));
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterText) {
                mstr_total_text = array.getString(attr);
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterNumber) {
                mstr_total_number = array.getString(attr);
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterTextColor) {
                mTextColor1 = array.getColor(attr, Color.parseColor("#BBB9B8"));
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterNumberColor) {
                mTextColor2 = array.getColor(attr, Color.parseColor("#FC824B"));
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterTextSize) {
                textSize1 = array.getDimensionPixelSize(attr, sp2px(context, DEFAULT_TEXTSIZE1));
            } else if (attr == R.styleable.RingStatisticsView_rsv_CenterNumberSize) {
                textSize2 = array.getDimensionPixelSize(attr, sp2px(context, DEFAULT_TEXTSIZE2));
            } else if (attr == R.styleable.RingStatisticsView_rsv_PercentTextSize) {
                textSize3 = array.getDimensionPixelSize(attr, sp2px(context, DEFAULT_TEXTSIZE3));
            }
        }
        array.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);

        mLinePaint = new Paint(mPaint);

        mPathPaint = new Paint(mPaint);
        mPathPaint.setStrokeWidth(1);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);

        mRectF = new RectF();

    }

    private void initSize(Context context) {
        if (mRingWidth == 0) {
            mRingWidth = dp2px(context, dp2px(context, DEFAULT_RINGWIDTH));
        }
        if (textSize1 == 0) {
            textSize1 = sp2px(context, DEFAULT_TEXTSIZE1);
        }
        if (textSize2 == 0) {
            textSize2 = sp2px(context, DEFAULT_TEXTSIZE2);
        }
        if (textSize3 == 0) {
            textSize3 = sp2px(context, DEFAULT_TEXTSIZE3);
        }
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dp2px(Context context, float dpValue) {
        final float densityScale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * densityScale + 0.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.max(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
        radius = Math.max(width, height) / 4;
        mRectF.set(getPaddingLeft() + mRingWidth / 2 + radius, getPaddingTop() + mRingWidth / 2 + radius, width - getPaddingRight() - mRingWidth / 2 - radius, getHeight() - getPaddingBottom() - mRingWidth / 2 - radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = 0f;
        float startAngle = 180f;
        float angle = 0f;
        float startX, startY, centerX, centerY, endX, endY, textX;
        int mCount = mPercent.length;
        mPaint.setStrokeWidth(mRingWidth);
        mLinePaint.setStrokeWidth(mRingWidth);
        for (int i = 0; i < mCount; i++) {
            mPaint.setColor(mColors[i]);
            startAngle += sweepAngle;
            sweepAngle = 360 * mPercent[i];
            canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
            angle = (startAngle - 180) + sweepAngle / 2;
            startX = (float) (width / 2 - radius * Math.cos(angle * Math.PI / 180));
            startY = (float) (height / 2 - radius * Math.sin(angle * Math.PI / 180));
            if (angle <= 90) {
                centerX = startX - width / 8;
                centerY = startY - height / 8;
                endX = centerX - width / 8;
                endY = centerY;
                textX = centerX - width / 16;
            } else if (angle <= 180) {
                centerX = startX + width / 8;
                centerY = startY - height / 8;
                endX = centerX + width / 8;
                endY = centerY;
                textX = centerX + width / 16;
            } else if (angle <= 270) {
                centerX = startX + width / 8;
                centerY = startY + height / 8;
                endX = centerX + width / 8;
                endY = centerY;
                textX = centerX + width / 16;
            } else {
                centerX = startX - width / 8;
                centerY = startY + height / 8;
                endX = centerX - width / 8;
                endY = centerY;
                textX = centerX - width / 16;
            }
            Path path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(centerX, centerY);
            path.lineTo(endX, endY);
            mPathPaint.setColor(mColors[i]);
            canvas.drawPath(path, mPathPaint);
            DecimalFormat df = new DecimalFormat("######0.0");
            BigDecimal b = new BigDecimal(mPercent[i] * 100);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            String percent = df.format(f1) + "%";
            float text_width = mTextPaint.measureText(percent);
            mTextPaint.setColor(mColors[i]);
            mTextPaint.setTextSize(textSize3);
            canvas.drawText(percent, textX - text_width / 2, endY - (mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent) / 2, mTextPaint);
        }
        mLinePaint.setColor(Color.WHITE);
        for (int i = 0; i < mCount; i++) {
            if (i == 0) {
                angle = 179;
            } else {
                angle += 360 * mPercent[i - 1];
            }
            canvas.drawArc(mRectF, angle, 2, false, mLinePaint);
        }

        mTextPaint.setTextSize(textSize1);
        float textwidth1 = mTextPaint.measureText(mstr_total_text);
        mTextPaint.setColor(mTextColor1);
        canvas.drawText(mstr_total_text, width / 2 - textwidth1 / 2, (height / 2 - (mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent) / 2), mTextPaint);
        mTextPaint.setTextSize(textSize2);
        float textwidth2 = mTextPaint.measureText(mstr_total_number);
        mTextPaint.setColor(mTextColor2);
        canvas.drawText(mstr_total_number, width / 2 - textwidth2 / 2, (height / 2 + (mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent) / 2) + 2, mTextPaint);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.save();
    }

    public void setPercentAndColors(float[] percent, int[] colors) {
        if (percent != null && colors != null) {
            if (percent.length == colors.length) {
                mPercent = percent;
                mColors = colors;
            } else {
                throw new IllegalArgumentException("length of percent must equals length of colors");
            }
        }
    }

    public void setRingWidth(float ringWindth) {
        mRingWidth = ringWindth;
    }

    public void setCenterText(String text) {
        mstr_total_text = text;
    }

    public void setCenterNumber(String number) {
        mstr_total_number = number;
    }

    public void setCenterTextColor(int color) {
        mTextColor1 = color;
    }

    public void setCenterNumberColor(int color) {
        mTextColor2 = color;
    }

    public void setCenterTextSize(int size) {
        textSize1 = size;
    }

    public void setCenterNumberSize(int size) {
        textSize2 = size;
    }

    public void setPercentTextSize(int size) {
        textSize3 = size;
    }

    public void refresh() {
        postInvalidate();
    }
}
