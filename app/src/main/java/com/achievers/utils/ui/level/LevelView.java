package com.achievers.utils.ui.level;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.achievers.R;

public class LevelView extends View {

    private static final int StrokeWidth = 10;

    private int mProgress;

    private Paint mDelimiterPaint;
    private Paint mCompletedPaint;
    private Paint mLeftPaint;
    private Paint mRingPaint;
    private Paint mTextPaint;

    public LevelView(Context context) {
        super(context);
        init(context);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDelimiterPaint = new Paint();
        mDelimiterPaint.setAntiAlias(true);
        mDelimiterPaint.setColor(getResources().getColor(R.color.lvDelimiter));
        mDelimiterPaint.setStrokeWidth(StrokeWidth);
        mDelimiterPaint.setStyle(Paint.Style.STROKE);

        mCompletedPaint = new Paint();
        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(getResources().getColor(R.color.lvCompleted));
        mCompletedPaint.setStyle(Paint.Style.FILL);

        mLeftPaint = new Paint();
        mLeftPaint.setAntiAlias(true);
        mLeftPaint.setColor(getResources().getColor(R.color.lvPending));
        mLeftPaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(getResources().getColor(R.color.lvDelimiter));
        mRingPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(getResources().getColor(R.color.lvCompleted));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTypeface(ResourcesCompat.getFont(context, R.font.open_sans_bold));
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.lv_font_size));
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }

        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCompleted(canvas);
        drawLeft(canvas);
        drawInfo(canvas);
    }

    private void drawInfo(Canvas canvas) {
        float percentage = mProgress / 100f;

        String text = String.valueOf(mProgress) + "%";

        float left = (canvas.getWidth() * percentage);
        float top = canvas.getHeight() * 0.25f;
        float right = left + mTextPaint.measureText(text);
        float bottom = canvas.getHeight() * 0.75f;

        canvas.drawRect(left, top, right, bottom, mRingPaint);

        float x = left;
        float y = (canvas.getHeight() - (mTextPaint.descent() + mTextPaint.ascent())) / 2;

        canvas.drawText(text, x, y, mTextPaint);
    }

    private void drawCompleted(Canvas canvas) {
        float percentage = mProgress / 100f;

        float left = 0;
        float top = canvas.getHeight() * 0.25f;
        float right = (canvas.getWidth() * percentage);
        float bottom = canvas.getHeight() * 0.75f;

        canvas.drawRect(left, top, right, bottom, mCompletedPaint);
    }

    private void drawLeft(Canvas canvas) {
        float percentage = 1 - (mProgress / 100f);

        float left = canvas.getWidth() - (canvas.getWidth() * percentage);
        float top = canvas.getHeight() * 0.25f;
        float right = canvas.getWidth();
        float bottom = canvas.getHeight() * 0.75f;

        canvas.drawRect(left, top, right, bottom, mLeftPaint);
    }
}