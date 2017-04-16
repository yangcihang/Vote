package net.hrsoft.voter.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.home.model.Votes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abtion on 17/4/16.
 */

public class UserVotesAdapter extends ArrayAdapter<Votes> {
    private ArrayList<Votes> votesArrayList;


    public UserVotesAdapter(Context context, int resource, List<Votes> votesList) {
        super(context, resource, votesList);
        votesArrayList = (ArrayList<Votes>) votesList;
    }


    static class ViewHolder {
        @BindView(R.id.vote_status)
        ImageView voteStatus;
        @BindView(R.id.txt_vote_tittle)
        TextView txtVoteTittle;
        @BindView(R.id.txt_vote_context)
        TextView txtVoteContext;
        @BindView(R.id.img_vote_runable)
        ImageView imgVoteRunable;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = View.inflate(getContext(), R.layout.list_vote, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtVoteTittle.setText(votesArrayList.get(position).getTittle());
        viewHolder.txtVoteContext.setText(votesArrayList.get(position).getDescription());
        return convertView;
    }
}
