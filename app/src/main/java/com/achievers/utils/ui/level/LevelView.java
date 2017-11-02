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
        mLeftPaint.setColor(getResources().getColor(R.color.lvLeft));
        mLeftPaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(getResources().getColor(R.color.lvCompleted));
        mRingPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(getResources().getColor(R.color.lvDelimiter));
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

        drawHolder(canvas);

        drawCompleted(canvas);
        drawLeft(canvas);
        drawDelimiters(canvas);
        drawRing(canvas);
    }

    private void drawRing(Canvas canvas) {
        float percentage = mProgress / 100f;

        float x = (canvas.getWidth() * percentage) + mDelimiterPaint.getStrokeWidth();
        float y = canvas.getHeight() / 2;
        float radius = (canvas.getHeight() / 2) - (mDelimiterPaint.getStrokeWidth() / 2);

        if (x < radius) x = radius;
        if (x + radius > canvas.getWidth()) x = canvas.getWidth() - radius;

        canvas.drawCircle(x, y, radius, mRingPaint);
        canvas.drawCircle(x, y, radius, mDelimiterPaint);

        String text = String.valueOf(mProgress) + "%";

        x = x - (mTextPaint.measureText(text) / 2);
        y = y - ((mTextPaint.descent() + mTextPaint.ascent()) / 2);

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

    private void drawDelimiters(Canvas canvas) {
        float interval = canvas.getWidth() / 8;

        float top = (canvas.getHeight() * 0.25f);
        float bottom = (canvas.getHeight() * 0.75f);
        float middle = bottom - top;

        for (int i = 0; i < 8; i++) {
            float startX = (i * interval);
            float startY = top;

            float endX = startX + 50;
            float endY = startY + ((bottom - top) / 2);

            canvas.drawLine(startX, startY, endX, endY, mDelimiterPaint);


            canvas.drawLine(endX, endY, startX, bottom, mDelimiterPaint);
        }
    }

    private void drawHolder(Canvas canvas) {
        float offset = mDelimiterPaint.getStrokeWidth() / 2;

        float top = (canvas.getHeight() * 0.25f) - offset;
        float bottom = canvas.getHeight() * 0.75f + offset;

        canvas.drawRect(0, top, canvas.getWidth(), bottom, mDelimiterPaint);
    }
}