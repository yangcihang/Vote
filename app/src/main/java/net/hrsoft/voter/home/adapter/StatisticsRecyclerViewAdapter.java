package net.hrsoft.voter.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.home.model.VoteRecordItemOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YangCihang.
 * @since 2017/4/21 0021.
 */

public class StatisticsRecyclerViewAdapter extends RecyclerView.Adapter<StatisticsRecyclerViewAdapter.StatisticsRecHolder> {
    private List<VoteRecordItemOption> voteRecordItemOptionList;

    public StatisticsRecyclerViewAdapter(List<VoteRecordItemOption> list) {
        voteRecordItemOptionList = list;
    }

    @Override
    public StatisticsRecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_statistics, parent, false);
        return new StatisticsRecHolder(view);
    }

    @Override
    public void onBindViewHolder(StatisticsRecHolder holder, int position) {
        VoteRecordItemOption voteRecordItemOption = voteRecordItemOptionList.get(position);
        String statisticsNum;
        statisticsNum = holder.statisticsNumTxt.getText().toString() + voteRecordItemOption.getNum();
        holder.statisticsNumTxt.setText(statisticsNum);
        int percent = (int) (voteRecordItemOption.getPercent() * 100);
        holder.statisticsProgress.setProgress(percent);
        holder.statisticsOptionsTxt.setText(voteRecordItemOption.getOptionTitle());
    }

    @Override
    public int getItemCount() {
        return voteRecordItemOptionList.size();
    }

    class StatisticsRecHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_num_statistics)
        TextView statisticsNumTxt;
        @BindView(R.id.progress_statistics)
        ProgressBar statisticsProgress;
        @BindView(R.id.txt_statistics_rec_options)
        TextView statisticsOptionsTxt;

        StatisticsRecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
