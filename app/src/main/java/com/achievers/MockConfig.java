package com.achievers;

import com.achievers.data.entities.Involvement;
import com.achievers.utils.DateUtils;

import java.util.Date;

public class MockConfig {

    public static final String Id            = "mock_id";
    public static final String Title         = "mock_title";
    public static final String Description   = "mock_description";
    public static final int    InvolvementId = Involvement.Gold.getId();
    public static final String Url           = "http://mock_url/";
    public static final Date   Date          = DateUtils.create(2017, 3, 17, 5, 45, 37);
    public static final String Email         = "mock_email";
    public static final String Password      = "mock_password";
    public static final String Token         = "mock_token";
    public static final int    Page          = 0;

    private MockConfig() {

    }
}
