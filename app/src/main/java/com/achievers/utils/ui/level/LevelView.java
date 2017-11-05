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

    private static final int InfoPadding = 20;

    private int mProgress;

    private Paint mCompletedPaint;
    private Paint mPendingPaint;
    private Paint mInfoPaint;
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
        mCompletedPaint = new Paint();
        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(getResources().getColor(R.color.lvCompleted));
        mCompletedPaint.setStyle(Paint.Style.FILL);

        mPendingPaint = new Paint();
        mPendingPaint.setAntiAlias(true);
        mPendingPaint.setColor(getResources().getColor(R.color.lvPending));
        mPendingPaint.setStyle(Paint.Style.FILL);

        mInfoPaint = new Paint();
        mInfoPaint.setAntiAlias(true);
        mInfoPaint.setColor(getResources().getColor(R.color.lvCompleted));
        mInfoPaint.setStyle(Paint.Style.FILL);

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

        float width = canvas.getWidth();
        float height = canvas.getHeight();

        drawCompleted(canvas, width, height);
        drawPending(canvas, width, height);
        drawInfo(canvas, width, height);
    }

    private void drawCompleted(Canvas canvas, float width, float height) {
        float percentage = mProgress / 100f;

        float left = 0;
        float top = 0;
        float right = width * percentage;
        float bottom = height;

        canvas.drawRect(left, top, right, bottom, mCompletedPaint);
    }

    private void drawPending(Canvas canvas, float width, float height) {
        float percentage = 1 - (mProgress / 100f);

        float left = width - (width * percentage);
        float top = 0;
        float right = width;
        float bottom = height;

        canvas.drawRect(left, top, right, bottom, mPendingPaint);
    }

    private void drawInfo(Canvas canvas, float width, float height) {
        float percentage = mProgress / 100f;
        String text = String.valueOf(mProgress) + "%";

        float textWidth = mTextPaint.measureText(text) + InfoPadding;

        float left = (width * percentage) - textWidth;

        if (left < 0) left = 0;
        if (left + textWidth > width) left = width - textWidth;

        float top = 0;
        float right = left + mTextPaint.measureText(text) + InfoPadding;
        float bottom = height;

        canvas.drawRect(left, top, right, bottom, mInfoPaint);

        float x = left + (InfoPadding / 2);
        float y = (height - (mTextPaint.descent() + mTextPaint.ascent())) / 2;

        canvas.drawText(text, x, y, mTextPaint);
    }
}