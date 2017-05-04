package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author YangCihang.
 * @since 2017/4/23 0023.
 */

public class QuestionValue extends BaseModel{
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
