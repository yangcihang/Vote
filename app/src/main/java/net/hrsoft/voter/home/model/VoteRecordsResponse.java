package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

import java.util.ArrayList;

/**
 * @author YangCihang.
 * @since 2017/4/21 0021.
 */

public class VoteRecordsResponse extends BaseModel {
    private long total;
    private long voteId;

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public long getVoteId() {
        return voteId;
    }

    private ArrayList<VoteRecord> records;
    public ArrayList<VoteRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<VoteRecord> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
