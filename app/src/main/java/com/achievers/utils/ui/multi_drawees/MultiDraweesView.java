package com.achievers.utils.ui.multi_drawees;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.achievers.DefaultConfig;
import com.achievers.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import static android.graphics.Paint.Style.STROKE;
import static com.facebook.drawee.generic.RoundingParams.RoundingMethod.BITMAP_ONLY;

public class MultiDraweesView extends View {

    private MultiDraweeHolder<GenericDraweeHierarchy> mMultiDraweeHolder;
    private RoundingParams mRoundingParams;
    private Uri[] mUris;

    private Paint mSolidPaint;
    private Paint mStrokePaint;
    private Paint mTextPaint;

    public MultiDraweesView(Context context) {
        super(context);
        init();
    }

    public MultiDraweesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MultiDraweesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mUris = new Uri[0];
        mMultiDraweeHolder = new MultiDraweeHolder<>();

        int solidColor = getResources().getColor(R.color.mdvSolid);
        int strokeColor = getResources().getColor(R.color.mdvStroke);
        int strokeWidth = getResources().getDimensionPixelSize(R.dimen.mdv_stroke_size);

        mRoundingParams = new RoundingParams();
        mRoundingParams.setRoundingMethod(BITMAP_ONLY);
        mRoundingParams.setRoundAsCircle(true);
        mRoundingParams.setBorderColor(strokeColor);
        mRoundingParams.setBorderWidth(strokeWidth);

        mSolidPaint = new Paint();
        mSolidPaint.setAntiAlias(true);
        mSolidPaint.setColor(solidColor);

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(STROKE);
        mStrokePaint.setStrokeWidth(strokeWidth);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(strokeColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.open_sans_bold));
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.mdv_font_size));
    }

    public void setUris(Uri[] uri) {
        mUris = uri;

        for (int i = 0; i < mUris.length; i++) {
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                    .setRoundingParams(mRoundingParams)
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .setPlaceholderImage(DefaultConfig.PlaceholderImage)
                    .build();

            DraweeHolder<GenericDraweeHierarchy> draweeHolder = new DraweeHolder<>(hierarchy);
            draweeHolder.getTopLevelDrawable().setCallback(this);
            mMultiDraweeHolder.add(draweeHolder);

            ImageRequest.RequestLevel level = ImageRequest.RequestLevel.FULL_FETCH;

            final ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(uri[i])
                    .setLowestPermittedRequestLevel(level)
                    .setProgressiveRenderingEnabled(true)
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(mMultiDraweeHolder.get(i).getController())
                    .build();

            mMultiDraweeHolder.get(i).getTopLevelDrawable().setCallback(this);
            mMultiDraweeHolder.get(i).setController(controller);
        }
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return mMultiDraweeHolder.verifyDrawable(who) || super.verifyDrawable(who);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = Math.min(canvas.getWidth(), canvas.getHeight());
        int padding = width / 15;
        int circles = mUris.length;
        int maxCircles = canvas.getWidth() / width;

        boolean drawMore = circles > maxCircles;

        if (drawMore) {
            circles = maxCircles - 1;
            drawMore(canvas, circles, width, padding);
        }

        drawDrawables(canvas, circles, width, padding);
    }

    private void drawDrawables(Canvas canvas, int circles, int width, int padding) {
        for (int i = 0; i < circles; i++) {
            Drawable drawable = mMultiDraweeHolder.get(i).getTopLevelDrawable();

            int left = (width * i) + padding;
            int top = padding;
            int right = left + width - padding;
            int bottom = canvas.getHeight() - padding;

            drawable.setBounds(left, top ,right, bottom);
            drawable.draw(canvas);
        }
    }

    private void drawMore(Canvas canvas, int circles, int width, int padding) {
        float radius = (width / 2);
        float circleX = (width * circles) + radius;
        float circleY = radius;
        radius -= padding;

        canvas.drawCircle(circleX, circleY, radius, mSolidPaint);
        canvas.drawCircle(circleX, circleY, radius, mStrokePaint);

        String text = "···";
        float textX = (width * circles) + (width / 2) - (mTextPaint.measureText(text) / 2);
        float textY = (canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2);

        canvas.drawText(text, textX, textY, mTextPaint);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mMultiDraweeHolder.onDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        mMultiDraweeHolder.onDetach();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mMultiDraweeHolder.onAttach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        mMultiDraweeHolder.onAttach();
    }
}