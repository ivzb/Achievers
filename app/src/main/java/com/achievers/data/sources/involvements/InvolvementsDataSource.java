package com.achievers.data.sources.involvements;

import com.achievers.data.callbacks.LoadCallback;
import com.achievers.data.entities.Involvement;

public interface InvolvementsDataSource {

    void loadInvolvements(final LoadCallback<Involvement> callback);
}
