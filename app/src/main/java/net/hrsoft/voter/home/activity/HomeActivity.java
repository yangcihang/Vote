package net.hrsoft.voter.home.activity;

import android.widget.ListView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.NoToolbarActivity;
import net.hrsoft.voter.home.adapter.UserVotesAdapter;
import net.hrsoft.voter.home.model.Votes;


import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 * Created by abtion on 17/4/13.
 */

public class HomeActivity extends NoToolbarActivity {
    @BindView(R.id.list_vote)
    ListView listVote;
    private ArrayList<Votes> votes = new ArrayList<Votes>() {
    };

    @Override
    protected void initVariable() {
        super.initVariable();
        for (int i = 0; i < 5; i++) {
            Votes vote = new Votes(25, "Test2", "This is a Test2", false, 100, true, 112232101, 1564564, 2,
                    1354564, 1265476, null,
                    0, null, false);
            votes.add(vote);
        }
        UserVotesAdapter userVotesAdapter = new UserVotesAdapter(this, R.layout.list_vote, votes);
        listVote.setAdapter(userVotesAdapter);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }



}
