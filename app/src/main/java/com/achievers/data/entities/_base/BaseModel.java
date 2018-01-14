package com.achievers.data.entities._base;

import java.util.Date;

public interface BaseModel {

    String getId();
    void setId(String id);

    Date getCreatedOn();
    void setCreatedOn(Date createdOn);

    String getContainerId();
}
