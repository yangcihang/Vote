package net.hrsoft.voter.home.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.ToolbarActivity;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.home.adapter.StatisticsRecyclerViewAdapter;
import net.hrsoft.voter.home.model.UserVoteItem;
import net.hrsoft.voter.home.model.VoteRecord;
import net.hrsoft.voter.home.model.VoteRecordItemOption;
import net.hrsoft.voter.home.model.VoteRecordsResponse;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteStatisticsActivity extends ToolbarActivity {
    @BindView(R.id.rec_vote_statistics)
    RecyclerView voteStatisticsRec;
    @BindView(R.id.txt_statistics_hint)
    TextView statisticsHintTxt;
    @BindView(R.id.txt_statistics_title)
    TextView statisticsTitleTxt;
    @BindView(R.id.txt_statistics_descript)
    TextView statisticsDescriptTxt;

    @Override
    protected void init() {
        super.init();
        this.setActivityTitle(getResources().getString(R.string.title_vote_statistics));
        UserVoteItem userVoteItem = (UserVoteItem) getIntent().getSerializableExtra(CacheKey.USERVOTEITEMDATA);
        statisticsTitleTxt.setText(userVoteItem.getTitle());
        statisticsDescriptTxt.setText(userVoteItem.getDescription());
        sendGetStatisticsRequest(userVoteItem.getId());
    }

    private void sendGetStatisticsRequest(final long voteId) {
        RestClient.getVoterService().getVoteRecordsPost(voteId).enqueue(new Callback<APIResponse<VoteRecordsResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<VoteRecordsResponse>> call, Response<APIResponse<VoteRecordsResponse>> response) {
                if (response.code() < APICode.SERVER_RESPONSE_CODE) {
                    ArrayList<VoteRecord> voteRecords = response.body().getData().getRecords();
                    initRecList(voteRecords.get(0).getOption());
                } else {
                    ToastUtil.showToast(VoteStatisticsActivity.this, R.string.toast_vote_statistics_votes_has_question);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<VoteRecordsResponse>> call, Throwable t) {
                ToastUtil.showToast(VoteStatisticsActivity.this, R.string.toast_net_has_question);
            }
        });
    }

    private void initRecList(ArrayList<VoteRecordItemOption> voteRecordItemOptions) {
        StatisticsRecyclerViewAdapter statisticsRecyclerViewAdapter;
        statisticsRecyclerViewAdapter = new StatisticsRecyclerViewAdapter(voteRecordItemOptions);
        voteStatisticsRec.setLayoutManager(new LinearLayoutManager(VoteStatisticsActivity.this));
        voteStatisticsRec.setAdapter(statisticsRecyclerViewAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_statistics;
    }
}
