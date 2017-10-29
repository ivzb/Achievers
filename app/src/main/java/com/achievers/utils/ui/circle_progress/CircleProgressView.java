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
import android.util.TypedValue;
import android.view.View;

import com.achievers.R;

public class CircleProgressView extends View {

    private int mProgress;
    private String mPrimaryText;
    private String mSecondaryText;
    private boolean mInverted;

    private float mHalfWidth;
    private float mHalfHeight;
    private float mRadius;
    private float mOffset;

    private int mNotStartedColor;
    private int mProgressColor;
    private int mProgressLightColor;
    private int mCompletedColor;
    private int mCompletedLightColor;
    private int mTextColor;

    private Paint mCirclePaint;
    private Paint mInnerCirclePaint;
    private Paint mArcPaint;
    private TextPaint mPrimaryTextPaint;
    private TextPaint mSecondaryTextPaint;

    private RectF mRect;

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

    public int getProgress() {
        return mProgress;
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

    public String getPrimaryText() {
        return mPrimaryText;
    }

    public void setPrimaryText(String primaryText) {
        if (primaryText == null) primaryText = "";

        mPrimaryText = primaryText;
        invalidate();
    }

    public String getSecondaryText() {
        return mSecondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        if (secondaryText == null) secondaryText = "";

        mSecondaryText = secondaryText;
        invalidate();

    }

    public boolean isInverted() {
        return mInverted;
    }

    public void setInverted(boolean isInverted) {
        mInverted = isInverted;
        invalidate();
    }

    private void updatePaints() {
        int progress = getProgress();

        if (progress == 0) {
            mCirclePaint.setColor(mNotStartedColor);
            mInnerCirclePaint.setColor(mNotStartedColor);
        } else if (progress < 100) {
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
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, metrics);

        mNotStartedColor = getResources().getColor(R.color.cpvNotStarted);
        mCompletedColor = getResources().getColor(R.color.cpvCompleted);
        mCompletedLightColor = getResources().getColor(R.color.cpvCompletedLight);
        mProgressColor = getResources().getColor(R.color.cpvProgress);
        mProgressLightColor = getResources().getColor(R.color.cpvProgressLight);
        mTextColor = getResources().getColor(R.color.cpvText);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setStyle(Paint.Style.FILL);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.open_sans_regular);

        mPrimaryTextPaint = new TextPaint();
        mPrimaryTextPaint.setColor(mTextColor);
        mPrimaryTextPaint.setAntiAlias(true);
        mPrimaryTextPaint.setTypeface(typeface);

        mSecondaryTextPaint = new TextPaint();
        mSecondaryTextPaint.setColor(mTextColor);
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
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mHalfWidth = width / 2;
        mHalfHeight = height / 2;
        mRadius = Math.min(mHalfWidth , mHalfHeight);

        boolean landscape = width > height;
        float left, top, right, bottom;

        if (landscape) {
            left = mHalfWidth - mRadius;
            top = 0;
            right = mHalfWidth + mRadius;
            bottom = height;
        } else {
            left = 0;
            top = mHalfHeight - mRadius;
            right = width;
            bottom = mHalfHeight + mRadius;
        }

        mRect = new RectF(left, top, right, bottom);

        // text size aspect ratio according to the scribed rectangular
        mPrimaryTextPaint.setTextSize(dpToPx(mRect.height() / 15));
        mSecondaryTextPaint.setTextSize(dpToPx(mRect.height() / 30));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mHalfWidth, mHalfHeight, mRadius, mCirclePaint);

        float degrees = 3.6f;

        for (float angle = 0; angle < 360; angle += degrees) {
            double target = angle * Math.PI / 180;
            float cosTarget = (float)(Math.cos(target) * mRadius);
            float sinTarget = (float)(Math.sin(target) * mRadius);

            canvas.drawLine(
                    mRect.centerX(),
                    mRect.centerY(),
                    mRect.centerX() + cosTarget,
                    mRect.centerY() + sinTarget,
                    mPrimaryTextPaint);
        }

        float startAngle = 0;
        float sweepAngle = getProgress() * degrees;
        if (isInverted()) sweepAngle *= -1;

        canvas.drawArc(mRect, startAngle, sweepAngle, true, mArcPaint);

        canvas.drawCircle(mHalfWidth, mHalfHeight, mRadius - mOffset, mInnerCirclePaint);

        canvas.drawText(
                getPrimaryText(),
                mRect.centerX() - (mPrimaryTextPaint.measureText(getPrimaryText()) / 2),
                (mRect.bottom - (mRadius * 1.2f)) - ((mPrimaryTextPaint.descent() + mPrimaryTextPaint.ascent()) / 2),
                mPrimaryTextPaint
        );

        canvas.drawText(
                getSecondaryText(),
                mRect.centerX() - (mSecondaryTextPaint.measureText(getSecondaryText().toUpperCase()) / 2),
                (mRect.top + (mRadius * 1.2f)) - ((mSecondaryTextPaint.descent() + mSecondaryTextPaint.ascent()) / 2),
                mSecondaryTextPaint
        );
    }

    private int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
}