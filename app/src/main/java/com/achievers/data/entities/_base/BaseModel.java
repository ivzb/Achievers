package com.achievers.data.entities._base;

import java.util.Date;

public interface BaseModel {

    long getId();
    void setId(long id);

    Date getCreatedOn();
    void setCreatedOn(Date createdOn);

    Long getContainerId();
}
