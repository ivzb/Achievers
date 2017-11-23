package com.achievers.data.sources._base.contracts;

public interface BaseDataSource<T>
        extends ReceiveDataSource<T>, DeliverDataSource<T> {

}