package com.achievers.Evidence;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.achievers.R;
import com.achievers.data.Evidence;
import com.achievers.databinding.EvidenceItemVideoBinding;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import im.ene.toro.exoplayer2.ExoPlayerHelper;
import im.ene.toro.exoplayer2.ExoPlayerView;
import im.ene.toro.exoplayer2.ExoPlayerViewHolder;

//public class EvidenceItemVideoViewHolder extends RecyclerView.ViewHolder {
//    private EvidenceItemVideoBinding binding;
//
//    public EvidenceItemVideoViewHolder(EvidenceItemVideoBinding binding) {
//        super(binding.getRoot());
//        this.binding = binding;
//    }
//
//    public EvidenceItemVideoBinding getBinding() {
//        return this.binding;
//    }
//}

public class EvidenceItemVideoViewHolder extends ExoPlayerViewHolder {

//    public static final int LAYOUT_RES = R.layout.vh_toro_video_basic_4;

//    private SimpleVideoObject videoItem;
    private EvidenceItemVideoBinding binding;
    private Evidence evidence;
    private MediaSource mediaSource;

    public EvidenceItemVideoViewHolder(EvidenceItemVideoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override protected void onBind(RecyclerView.Adapter adapter, Object item) {
//        if (!(item instanceof SimpleVideoObject)) {
//            throw new IllegalArgumentException("Invalid Object: " + item);
//        }
        this.evidence = (Evidence) item;
        // prepare mediaSource
        this.mediaSource = ExoPlayerHelper.buildMediaSource(itemView.getContext(), //
                Uri.parse(this.evidence.getUrl()), new DefaultDataSourceFactory(itemView.getContext(),
                        Util.getUserAgent(itemView.getContext(), "Toro-Sample")), itemView.getHandler(), null);
        try {
          this.playerView.setMediaSource(mediaSource, true);
        } catch (ParserException e) {
          e.printStackTrace();
        }
    }

    public EvidenceItemVideoBinding getBinding() {
        return this.binding;
    }

    @Override protected ExoPlayerView findVideoView(View itemView) {
        return (ExoPlayerView) itemView.findViewById(R.id.video);
//        return (ExoPlayerView) binding.getRoot().findViewById(R.id.video);
    }

    @Override protected MediaSource getMediaSource() {
        return mediaSource;
    }

    @Nullable
    @Override public String getMediaId() {
        return this.evidence != null ? this.evidence.getUrl() + "@" + getAdapterPosition() : null;
    }
}