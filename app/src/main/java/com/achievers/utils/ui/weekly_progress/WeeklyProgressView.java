package com.achievers.utils.ui.weekly_progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.achievers.R;

public class WeeklyProgressView extends View {

    private static final String[] sDays = new String[] { "M", "T", "W", "T", "F", "S", "S" };

    private WeeklyProgressWeightsEvaluator mEvaluator;

    private int[] mWeights;
    private int[] mWeightColors;

    private DayMeasurement[] mDayMeasurements;
    private WeightMeasurement[] mWeightMeasurements;

    private TextPaint mDayPaint;
    private Paint mWeightPaint;


    public WeeklyProgressView(Context context) {
        super(context);
        init(context);
    }

    public WeeklyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeeklyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mEvaluator = new WeeklyProgressWeightsEvaluator();
        mWeights = new int[sDays.length];

        mDayMeasurements = new DayMeasurement[sDays.length];
        mWeightMeasurements = new WeightMeasurement[sDays.length];

        mWeightColors = new int[5];
        mWeightColors[0] = getResources().getColor(R.color.wpvWeightEmpty);
        mWeightColors[1] = getResources().getColor(R.color.wpvWeightLight);
        mWeightColors[2] = getResources().getColor(R.color.wpvWeightMediumLight);
        mWeightColors[3] = getResources().getColor(R.color.wpvWeightMediumDark);
        mWeightColors[4] = getResources().getColor(R.color.wpvWeightDark);

        mDayPaint = new TextPaint();
        mDayPaint.setColor(getResources().getColor(R.color.wpvText));
        mDayPaint.setAntiAlias(true);
        mDayPaint.setTypeface(ResourcesCompat.getFont(context, R.font.open_sans_regular));
        mDayPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.wpv_font_size));

        mWeightPaint = new Paint();
        mWeightPaint.setAntiAlias(true);
        mWeightPaint.setStyle(Paint.Style.FILL);
    }

    public void setWeights(int[] values) {
        mWeights = mEvaluator.evaluate(values);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        float boxWidth = width / sDays.length;
        float boxPadding = boxWidth / 30;

        float left = -boxWidth;
        float top = 0;
        float right = 0;
        float bottom = height;

        for (int day = 0; day < sDays.length; day++) {
            left += boxWidth;
            right += boxWidth;

            mDayMeasurements[day] = new DayMeasurement(
                    sDays[day],
                    left + (boxWidth / 2) - (mDayPaint.measureText(sDays[day]) / 2),
                    top + (height / 6) - ((mDayPaint.descent() + mDayPaint.ascent()) / 2));

            mWeightMeasurements[day] = new WeightMeasurement(
                    left + boxPadding,
                    bottom / 3,
                    right - boxPadding,
                    bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int day = 0; day < sDays.length; day++) {
            DayMeasurement dayMeasurement = mDayMeasurements[day];
            dayMeasurement.draw(canvas, mDayPaint);

            int weightColor = mWeightColors[mWeights[day]];
            mWeightPaint.setColor(weightColor);

            WeightMeasurement weightMeasurement = mWeightMeasurements[day];
            weightMeasurement.draw(canvas, mWeightPaint);
        }
    }

    private class DayMeasurement {

        private String mText;
        private float mX;
        private float mY;

        DayMeasurement(String text, float x, float y) {
            mText = text;
            mX = x;
            mY = y;
        }

        void draw(Canvas canvas, Paint paint) {
            canvas.drawText(mText, mX, mY, paint);
        }
    }

    private class WeightMeasurement {

        private float mLeft;
        private float mTop;
        private float mRight;
        private float mBottom;

        WeightMeasurement(float left, float top, float right, float bottom) {
            mLeft = left;
            mTop = top;
            mRight = right;
            mBottom = bottom;
        }

        void draw(Canvas canvas, Paint paint) {
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
        }
    }
}
