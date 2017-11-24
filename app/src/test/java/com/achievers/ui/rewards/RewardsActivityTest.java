package com.achievers.ui.rewards;

import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ResourcesCompatShadow.class })
public class RewardsActivityTest extends AbstractActivityTest<RewardsActivity> {

    public RewardsActivityTest() {
        super(RewardsActivity.class);
    }
}
