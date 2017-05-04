package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author YangCihang.
 * @since 2017/4/21 0021.
 */

public class VoteRecordItemOption extends BaseModel{
    private long optionId;
    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }


    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getPercent() {
        return percent;
    }

    private float percent;
    private float num;
    private String optionTitle;
}
