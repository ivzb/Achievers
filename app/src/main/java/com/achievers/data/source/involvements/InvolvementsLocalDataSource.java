package com.achievers.data.source.involvements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;

import java.util.Arrays;

public class InvolvementsLocalDataSource implements InvolvementsDataSource {

    private static InvolvementsDataSource INSTANCE;

    public static InvolvementsDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new InvolvementsLocalDataSource();
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private InvolvementsLocalDataSource() {

    }

    @Override
    public void loadInvolvements(LoadCallback<Involvement> callback) {
        callback.onSuccess(Arrays.asList(Involvement.values()));
    }
}