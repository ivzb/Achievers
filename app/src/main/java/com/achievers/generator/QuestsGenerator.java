package com.achievers.generator;

import com.achievers.data.entities.Quest;
import com.achievers.generator._base.BaseGenerator;
import com.achievers.utils.GeneratorUtils;

import java.util.Date;

public class QuestsGenerator
        extends Generator<Quest>
        implements BaseGenerator<Quest> {

    public Quest single(long id) {
        return GeneratorUtils.getInstance().getQuest(id, new Date());
    }
}
