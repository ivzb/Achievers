package com.achievers.sync._base._shadows;

import org.apache.commons.io.IOUtils;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.io.IOException;
import java.io.InputStream;

@Implements(IOUtils.class)
public class IOUtilsShadow {

    @Implementation
    public static byte[] toByteArray(final InputStream input) throws IOException {
        return new byte[] { 1, 2, 3 };
    }
}
