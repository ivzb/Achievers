package com.achievers.data.generators;

import com.achievers.data.entities.Evidence;
import com.achievers.data.generators._base.BaseGenerator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EvidencesGeneratorTest
        extends AbstractGeneratorTest<Evidence> {

    @Before
    public void before() {
        super.before();

        BaseGenerator<Evidence> generator = new EvidencesGenerator(mGeneratorConfig);
        setGenerator(generator);
    }

    @Override
    public Evidence instantiate(long id) {
        return new Evidence(
                id,
                sSentence,
                sMultimediaType,
                sImageUrl,
                sVideoUri,
                sDate);
    }
}
