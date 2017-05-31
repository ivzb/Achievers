package com.achievers.util;

import android.support.annotation.Nullable;
import android.view.ViewParent;

import java.util.List;

import im.ene.toro.Toro;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroStrategy;

public class CustomToroPlayStrategy implements ToroStrategy {

    private final ToroStrategy delegate = Toro.Strategies.FIRST_PLAYABLE_TOP_DOWN;

    public CustomToroPlayStrategy() { }

    @Override
    public String getDescription() {
        return "All Media should be played by order, start from 'firstMediaPosition'";
    }

    @Nullable
    @Override
    public ToroPlayer findBestPlayer(List<ToroPlayer> candidates) {
        // Inherit the same behaviour with built-in one.
        return delegate.findBestPlayer(candidates);
    }

    @Override
    public boolean allowsToPlay(ToroPlayer player, ViewParent parent) {
        return false;
    }
}