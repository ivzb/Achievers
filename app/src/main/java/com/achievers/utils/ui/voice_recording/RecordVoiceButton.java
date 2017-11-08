package com.achievers.utils.ui.voice_recording;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.achievers.R;

public class RecordVoiceButton extends View implements View.OnClickListener {

    boolean mStartRecording = true;
    private RecordActionHandler mRecordActionHandler;

    private float mHalfWidth;
    private float mHalfHeight;
    private float mRadius;

    private String mText;

    private Paint mCirclePaint;
    private Paint mButtonPaint;
    private Paint mTextPaint;

    private RectF mRect;

    public RecordVoiceButton(Context context) {
        super(context);
        init(context);
    }

    public RecordVoiceButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecordVoiceButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnClickListener(this);

        mText = "Start recording";

        int circleColor = getResources().getColor(R.color.rvbCircleColor);
        int buttonColor = getResources().getColor(R.color.rvbButtonColor);
        int textColor = getResources().getColor(R.color.rvbTextColor);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(circleColor);

        mButtonPaint = new Paint();
        mButtonPaint.setAntiAlias(true);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setColor(buttonColor);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTypeface(ResourcesCompat.getFont(context, R.font.open_sans_regular));
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.rvb_font_size));
    }

    @Override
    public void onClick(View v) {
        if (mRecordActionHandler != null) mRecordActionHandler.onRecord(mStartRecording);

        if (mStartRecording) {
            mText = "Stop recording";
        } else {
            mText = "Start recording";
        }

        mStartRecording = !mStartRecording;
        invalidate();
    }

    public void setOnRecordActionHandler(RecordActionHandler recordActionHandler) {
        mRecordActionHandler = recordActionHandler;
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mHalfWidth, mHalfHeight, mRadius, mCirclePaint);

        if (mStartRecording) {
            float x = mHalfWidth;
            float y = mHalfHeight;
            float radius = mRadius * 0.5f;

            canvas.drawCircle(x, y, radius, mButtonPaint);
        } else {
            float left = mRect.centerX() - (mRadius / 2);
            float top = mRect.centerY() - (mRadius / 2);
            float right = mRadius + left;
            float bottom = mRadius + top;

            canvas.drawRect(left, top, right, bottom, mButtonPaint);
        }
    }
}