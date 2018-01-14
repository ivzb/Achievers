package com.achievers.data.entities._base;

import java.util.Date;

public interface BaseModel {

    String getId();
    void setId(String id);

    Date getCreatedAt();
    void setCreatedAt(Date createdAt);

    String getContainerId();
}
