package com.achievers.Evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.achievers.R;
import com.achievers.data.Evidence;
import com.achievers.databinding.EvidenceItemVideoBinding;
import com.achievers.util.FreskoCircleProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import im.ene.toro.exoplayer2.ExoPlayerHelper;
import im.ene.toro.exoplayer2.ExoPlayerView;
import im.ene.toro.exoplayer2.ExoPlayerViewHolder;

class EvidenceItemVideoViewHolder extends ExoPlayerViewHolder {

    private SimpleDraweeView mThumbnail;

    private EvidenceItemVideoBinding binding;
    private Evidence evidence;
    private MediaSource mediaSource;

    EvidenceItemVideoViewHolder(EvidenceItemVideoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.mThumbnail = (SimpleDraweeView) this.binding.getRoot().findViewById(R.id.thumbnail);
    }

    @Override protected void onBind(RecyclerView.Adapter adapter, Object item) {
        this.evidence = (Evidence) item;
        this.mediaSource = ExoPlayerHelper.buildMediaSource(
            itemView.getContext(),
            Uri.parse(this.evidence.getUrl()),
            new DefaultDataSourceFactory(itemView.getContext(), Util.getUserAgent(itemView.getContext(), "Achievers")),
            itemView.getHandler(),
            null
        );

        this.playerView.setControllerShowTimeoutMs(1500);
        this.playerView.setUseController(true);

        try {
          this.playerView.setMediaSource(mediaSource, false);
        } catch (ParserException e) {
          e.printStackTrace();
            // TODO: show error message and display refresh button
        }

        this.playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerView.isPlaying()) {
                    playerView.pause();
                } else {
                    playerView.start();
                }
            }
        });
    }

    public EvidenceItemVideoBinding getBinding() {
        return this.binding;
    }

    @Override protected ExoPlayerView findVideoView(View itemView) {
        return (ExoPlayerView) itemView.findViewById(R.id.video);
    }

    @Override protected MediaSource getMediaSource() {
        return mediaSource;
    }

    @Nullable
    @Override public String getMediaId() {
        return this.evidence != null ? this.evidence.getUrl() + "@" + getAdapterPosition() : null;
    }

    @Override public void onVideoPreparing() {
        super.onVideoPreparing();
        Log.v("video", "Preparing");
    }

    @Override public void onVideoPrepared() {
        super.onVideoPrepared();
        Log.v("video", "Prepared");
    }

    @Override public void onViewHolderBound() {
        super.onViewHolderBound();

        GenericDraweeHierarchy hierarchy = this.mThumbnail.getHierarchy();
        hierarchy.setFadeDuration(250);

        Resources resources = getBinding().getRoot().getResources();
        Drawable bunny = ResourcesCompat.getDrawable(resources, R.drawable.ic_camera_alt_black_48dp, null);
        hierarchy.setPlaceholderImage(bunny);
        hierarchy.setProgressBarImage(new FreskoCircleProgressBarDrawable());

        Log.v("video", "Bound");
    }

    @Override public void onPlaybackStarted() {
        mThumbnail.animate().alpha(0.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                EvidenceItemVideoViewHolder.super.onPlaybackStarted();
            }
        }).start();
        Log.v("video", "Started");
    }

    @Override public void onPlaybackPaused() {
//        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
//            @Override public void onAnimationEnd(Animator animation) {
//                EvidenceItemVideoViewHolder.super.onPlaybackPaused();
//            }
//        }).start();
        Log.v("video", "Paused");
    }

    @Override public void onPlaybackCompleted() {
//        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
//            @Override public void onAnimationEnd(Animator animation) {
//                EvidenceItemVideoViewHolder.super.onPlaybackCompleted();
//            }
//        }).start();
        Log.v("video", "Completed");
    }

    @Override public boolean onPlaybackError(Exception error) {
//        mThumbnail.animate().alpha(1.f).setDuration(0).setListener(new AnimatorListenerAdapter() {
//            @Override public void onAnimationEnd(Animator animation) {
//                // TODO: Immediately finish the animation.
//            }
//        }).start();
        Log.v("video", "Error: videoId = " + getMediaId());
        return super.onPlaybackError(error);
    }
}