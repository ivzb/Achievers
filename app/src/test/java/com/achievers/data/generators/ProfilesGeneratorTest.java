package com.achievers.data.generators;

import com.achievers.data.entities.Profile;
import com.achievers.data.generators._base.contracts.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProfilesGeneratorTest
        extends AbstractGeneratorTest<Profile> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<Profile> generator = new ProfilesGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public Profile instantiate(long id) {
        return new Profile(
                id,
                sWord,
                sImageUri,
                sDate);
    }
}