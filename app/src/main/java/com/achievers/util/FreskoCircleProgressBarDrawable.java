package com.achievers.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.facebook.drawee.drawable.ProgressBarDrawable;

public class FreskoCircleProgressBarDrawable extends ProgressBarDrawable {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mLevel = 0;
    private int maxLevel = 10000;

    public FreskoCircleProgressBarDrawable() {
        setColor(Color.DKGRAY);
        setColor(Color.LTGRAY);
    }

    @Override
    protected boolean onLevelChange(int level) {
        this.mLevel = level;
        this.invalidateSelf();

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.getHideWhenZero() && this.mLevel == 0) return;

        this.drawBar(canvas, this.maxLevel, this.getBackgroundColor());
        this.drawBar(canvas, this.mLevel, this.getColor());
    }

    private void drawBar(Canvas canvas, int level, int color) {
        if (level != 0) {
            Rect bounds = this.getBounds();

            float x = bounds.right / 2;
            float y = bounds.bottom / 2;

            PointF center = new PointF(x, y);

            this.mPaint.setColor(color);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(6);

            ArcUtils.drawArc(canvas, center, 50, 0, (float) (level * 360 / maxLevel), mPaint);
        }
    }
}