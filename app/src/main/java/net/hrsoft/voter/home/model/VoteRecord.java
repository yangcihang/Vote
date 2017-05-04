package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

import java.util.ArrayList;

/**
 * @author YangCihang.
 * @since 2017/4/21 0021.
 */

public class VoteRecord  extends BaseModel{
    private long problemId;
    private int type;
    private String problemTitle;
    private String problemDescription;
    private ArrayList<VoteRecordItemOption> option;

    public void setOption(ArrayList<VoteRecordItemOption> option) {
        this.option = option;
    }

    public ArrayList<VoteRecordItemOption> getOption() {
        return option;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    private ArrayList<VoteRecordItemOption> options;

    public ArrayList<VoteRecordItemOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<VoteRecordItemOption> options) {
        this.options = options;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }
}
