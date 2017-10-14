package com.achievers.data.source._base;

public interface BaseDataSource<T>
        extends GetDataSource<T>, LoadDataSource<T>, SaveDataSource<T> {

}