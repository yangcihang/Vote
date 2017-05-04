package net.hrsoft.voter.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import net.hrsoft.voter.R;
import net.hrsoft.voter.home.activity.CreateVoteActivity;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * @author YangCihang.
 * @since 2017/4/12 0012.
 */

public class HomeBottomLayout extends RelativeLayout{
    public HomeBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_home_bar,this);
        findViewById(R.id.btn_home_vote).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getContext(),new Intent(getContext(), CreateVoteActivity.class),null);
            }
        });
    }
}
