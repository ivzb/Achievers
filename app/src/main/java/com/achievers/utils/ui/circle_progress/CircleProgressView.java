package com.achievers.utils.ui.circle_progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.achievers.R;

import java.util.Hashtable;

public class CircleProgressView extends View {

    private int mProgress;
    private String mPrimaryText;
    private String mSecondaryText;
    private boolean mInverted;

    private float mHalfWidth;
    private float mHalfHeight;
    private float mRadius;

    private int mNotStartedColor;
    private int mProgressColor;
    private int mProgressLightColor;
    private int mCompletedColor;
    private int mCompletedLightColor;

    private Paint mCirclePaint;
    private Paint mInnerCirclePaint;
    private Paint mArcPaint;
    private TextPaint mPrimaryTextPaint;
    private TextPaint mSecondaryTextPaint;

    private static Hashtable<Double, Line> mLines;

    private class Line {

        private float mStartX;
        private float mStartY;
        private float mStopX;
        private float mStopY;

        Line(float startX, float startY, float stopX, float stopY) {
            mStartX = startX;
            mStartY = startY;
            mStopX = stopX;
            mStopY = stopY;
        }

        float startX() {
            return mStartX;
        }

        float startY() {
            return mStartY;
        }

        float stopX() {
            return mStopX;
        }

        float stopY() {
            return mStopY;
        }
    }

    private RectF mRect;
    private final float mStep = 3.6f; // in degrees

    public CircleProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }

        mProgress = progress;

        updatePaints();
        invalidate();
    }

    public void setPrimaryText(String primaryText) {
        if (primaryText == null) primaryText = "";

        mPrimaryText = primaryText;
        invalidate();
    }

    public void setSecondaryText(String secondaryText) {
        if (secondaryText == null) secondaryText = "";

        mSecondaryText = secondaryText;
        invalidate();

    }

    public void setInverted(boolean isInverted) {
        mInverted = isInverted;
        invalidate();
    }

    private void updatePaints() {
        if (mProgress == 0) {
            mCirclePaint.setColor(mNotStartedColor);
            mInnerCirclePaint.setColor(mNotStartedColor);
        } else if (mProgress < 100) {
            mCirclePaint.setColor(mProgressLightColor);
            mInnerCirclePaint.setColor(mProgressLightColor);
            mArcPaint.setColor(mProgressColor);
        } else {
            mCirclePaint.setColor(mCompletedLightColor);
            mInnerCirclePaint.setColor(mCompletedLightColor);
            mArcPaint.setColor(mCompletedColor);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mNotStartedColor = getResources().getColor(R.color.cpvNotStarted);
        mCompletedColor = getResources().getColor(R.color.cpvCompleted);
        mCompletedLightColor = getResources().getColor(R.color.cpvCompletedLight);
        mProgressColor = getResources().getColor(R.color.cpvProgress);
        mProgressLightColor = getResources().getColor(R.color.cpvProgressLight);
        int textColor = getResources().getColor(R.color.cpvText);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setStyle(Paint.Style.FILL);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.open_sans_regular);

        mPrimaryTextPaint = new TextPaint();
        mPrimaryTextPaint.setColor(textColor);
        mPrimaryTextPaint.setAntiAlias(true);
        mPrimaryTextPaint.setTypeface(typeface);

        mSecondaryTextPaint = new TextPaint();
        mSecondaryTextPaint.setColor(textColor);
        mSecondaryTextPaint.setAntiAlias(true);
        mSecondaryTextPaint.setTypeface(typeface);

        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

            setPrimaryText(arr.getString(R.styleable.CircleProgressView_primaryText));
            setSecondaryText(arr.getString(R.styleable.CircleProgressView_secondaryText));
            setProgress(arr.getInt(R.styleable.CircleProgressView_progress, 0));
            setInverted(arr.getBoolean(R.styleable.CircleProgressView_invert, false));

            arr.recycle();
        }

        mLines = new Hashtable<>();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mHalfWidth = width / 2;
        mHalfHeight = height / 2;
        mRadius = Math.min(mHalfWidth , mHalfHeight);

        float left;
        float top;
        float right;
        float bottom;

        if (width > height) { // landscape
            left = mHalfWidth - mRadius;
            top = 0;
            right = mHalfWidth + mRadius;
            bottom = height;
        } else { // portrait
            left = 0;
            top = mHalfHeight - mRadius;
            right = width;
            bottom = mHalfHeight + mRadius;
        }

        mRect = new RectF(left, top, right, bottom);

        // text size aspect ratio according to the scribed rectangular
        mPrimaryTextPaint.setTextSize(dpToPx(mRect.height() / 12));
        mSecondaryTextPaint.setTextSize(dpToPx(mRect.height() / 20));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOuterCircle(canvas);
        drawLines(canvas);
        drawArc(canvas);
        drawInnerCircle(canvas);
        drawPrimaryText(canvas);
        drawSecondaryText(canvas);
    }

    private void drawOuterCircle(Canvas canvas) {
        canvas.drawCircle(mHalfWidth, mHalfHeight, mRadius, mCirclePaint);
    }

    private void drawLines(Canvas canvas) {
        float sweepAngle = mProgress * mStep;
        float centerX = mRect.centerX();
        float centerY = mRect.centerY();

        double target = Math.PI / 180;

        // todo: adapt it for inverted
        for (float angle = sweepAngle; angle < 360; angle += mStep) {
            double targetAngle = angle * target;

            if (!mLines.containsKey(targetAngle)) {
                double targetCos = Math.cos(targetAngle) * mRadius;
                double targetSin = Math.sin(targetAngle) * mRadius;

                float startX = centerX + (float)(targetCos * 0.75f);
                float startY = centerY + (float)(targetSin * 0.75f);

                float stopX = centerX + (float)(targetCos);
                float stopY = centerY + (float)(targetSin);

                Line line = new Line(startX, startY, stopX, stopY);

                mLines.put(targetAngle, line);
            }

            Line line = mLines.get(targetAngle);

            canvas.drawLine(
                    line.startX(),
                    line.startY(),
                    line.stopY(),
                    line.stopY(),
                    mPrimaryTextPaint);
        }
    }

    private void drawArc(Canvas canvas) {
        int startAngle = 0;
        float sweepAngle = mProgress * mStep;

        if (mInverted) sweepAngle *= -1;

        canvas.drawArc(mRect, startAngle, sweepAngle, true, mArcPaint);
    }

    private void drawInnerCircle(Canvas canvas) {
        canvas.drawCircle(mHalfWidth, mHalfHeight, mRadius * 0.75f, mInnerCirclePaint);
    }

    private void drawPrimaryText(Canvas canvas) {
        String text = mPrimaryText;
        Paint paint = mPrimaryTextPaint;
        float x = mRect.centerX() - (paint.measureText(text) / 2);
        float y = (mRect.bottom - (mRadius * 1.2f)) - ((paint.descent() + paint.ascent()) / 2);

        drawText(canvas, text, x, y, paint);
    }

    private void drawSecondaryText(Canvas canvas) {
        String text = mSecondaryText;
        Paint paint = mSecondaryTextPaint;
        float x = mRect.centerX() - (paint.measureText(text) / 2);
        float y = (mRect.top + (mRadius * 1.3f)) - ((paint.descent() + paint.ascent()) / 2);

        drawText(canvas, text, x, y, paint);
    }

    private void drawText(
            Canvas canvas,
            String text,
            float x,
            float y,
            Paint paint) {

        canvas.drawText(text, x, y, paint);
    }

    private int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
}