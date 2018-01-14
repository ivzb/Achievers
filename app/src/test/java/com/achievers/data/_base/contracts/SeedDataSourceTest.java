package com.achievers.data._base.contracts;

import java.util.List;

public interface SeedDataSourceTest<T> {

    List<T> seed(String containerId, int size);
}
