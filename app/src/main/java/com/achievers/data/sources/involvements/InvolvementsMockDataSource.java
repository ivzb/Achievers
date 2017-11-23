package com.achievers.data.sources.involvements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;

import java.util.Arrays;

import static com.achievers.utils.Preconditions.checkNotNull;

public class InvolvementsMockDataSource implements InvolvementsDataSource {

    private static InvolvementsDataSource INSTANCE;

    public static InvolvementsDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new InvolvementsMockDataSource();
        return INSTANCE;
    }

    private InvolvementsMockDataSource() {

    }

    @Override
    public void loadInvolvements(LoadCallback<Involvement> callback) {

        checkNotNull(callback);

        callback.onSuccess(Arrays.asList(Involvement.values()), 0);
    }
}