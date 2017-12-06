package com.achievers.data.sources.involvements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;

import java.util.Arrays;

import static com.achievers.utils.Preconditions.checkNotNull;

public class InvolvementsMockDataSource implements InvolvementsDataSource {

    private static InvolvementsMockDataSource sINSTANCE;

    public static InvolvementsMockDataSource getInstance() {
        checkNotNull(sINSTANCE);

        return sINSTANCE;
    }

    public static InvolvementsMockDataSource createInstance() {
        sINSTANCE = new InvolvementsMockDataSource();

        return getInstance();
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private InvolvementsMockDataSource() {

    }

    @Override
    public void loadInvolvements(LoadCallback<Involvement> callback) {
        checkNotNull(callback);

        callback.onSuccess(Arrays.asList(Involvement.values()), 0);
    }
}