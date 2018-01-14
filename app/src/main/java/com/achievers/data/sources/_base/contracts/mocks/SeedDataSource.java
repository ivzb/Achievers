package com.achievers.data.sources._base.contracts.mocks;

import java.util.List;

public interface SeedDataSource<T> {

    List<T> seed(String containerId, int size);
}
