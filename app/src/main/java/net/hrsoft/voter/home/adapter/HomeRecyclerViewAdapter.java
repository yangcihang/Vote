package net.hrsoft.voter.home.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.home.activity.VoteStatisticsActivity;
import net.hrsoft.voter.home.model.UserVoteItem;


import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * @author YangCihang.
 * @since 2017/4/12 0012.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserVoteItem> userVoteItems;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public static final int PULL_UP_LOAD_MORE = 0;   //上拉加载更多
    public static final int LOADING_MORE = 1;//正在加载中
    public static final int LOADING_FINISH = 2;//加载完毕所有数据时
    private int load_more_status = 0;//上拉加载更多状态-默认为0
    private int footerCount = 1;

    /**
     * HomeRecyclerViewAdapter的构造函数
     *
     * @param list 构造时候传入的list
     */
    public HomeRecyclerViewAdapter(List<UserVoteItem> list) {
        userVoteItems = list;
    }

    private boolean isFooterView(int position) {
        return footerCount != 0 && position >= userVoteItems.size();
    }

    /**
     * 判断
     *
     * @param position position
     * @return 返回viewType
     */
    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 根据viewType判断创建不同的holder
     *
     * @param parent   viewGroup
     * @param viewType viewType
     * @return 返回holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_loading_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_home, parent, false);
            return new ItemViewHolder(view);
        }
    }

    /**
     * 绑定holder
     *
     * @param holder   viewHolder
     * @param position position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final UserVoteItem userData = userVoteItems.get(position);//获取用户投票实例
            ((ItemViewHolder) holder).homeItemTitleTxt.setText(userData.getTitle());
            ((ItemViewHolder) holder).homeItemDescriptionTxt.setText(userData.getDescription());
            ((ItemViewHolder) holder).homeItemDetailsTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(((ItemViewHolder) holder).itemView.getContext(), VoteStatisticsActivity.class);
                    intent.putExtra(CacheKey.USERVOTEITEMDATA, userData);
                    startActivity(holder.itemView.getContext(), intent, null);
                }
            });
            long subTime = 86400000;//时间间隔为这么多时判定状态
            long currentTime = System.currentTimeMillis();
            long endTime = userData.getEndTime();
            if (endTime - currentTime < subTime && endTime - currentTime > 0) {
                ((ItemViewHolder) holder).homeTimeStateImg.setImageResource(R.drawable.ic_nearline);
                ((ItemViewHolder) holder).homeStateImg.setImageResource(R.drawable.ic_warningtime);
            } else if (endTime - currentTime <= 0) {
                ((ItemViewHolder) holder).homeTimeStateImg.setImageResource(R.drawable.ic_ended);
                ((ItemViewHolder) holder).homeStateImg.setImageResource(R.drawable.ic_endedhead);
            } else {
                ((ItemViewHolder) holder).homeTimeStateImg.setImageResource(R.drawable.ic_running);
                ((ItemViewHolder) holder).homeStateImg.setImageResource(R.drawable.ic_home_hot);
            }
        } else {
            /**
             * 对底部栏的状态判断
             */
            switch (load_more_status) {
                case PULL_UP_LOAD_MORE:
                    ((FooterViewHolder) holder).footerTxt.setText("上拉加载更多");
                    break;
                case LOADING_MORE:
                    ((FooterViewHolder) holder).footerTxt.setText("加载中");
                    break;
                case LOADING_FINISH:
                    ((FooterViewHolder) holder).footerTxt.setText("已经木有数据啦");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 抛出改变底部加载栏状态的方法
     *
     * @param statue 需要改变的状态
     */
    public void changeLoadingStatues(int statue) {
        load_more_status = statue;
    }

    /**
     * 得到List的个数
     *
     * @return 返回个数
     */
    @Override
    public int getItemCount() {
        return userVoteItems.size() + footerCount;
    }

    /**
     * 投票信息的holder，进行view的初始化
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView homeItemTitleTxt;
        private TextView homeItemDescriptionTxt;
        private TextView homeItemDetailsTxt;
        private ImageView homeStateImg;
        private ImageView homeTimeStateImg;

        ItemViewHolder(final View itemView) {
            super(itemView);
            homeItemTitleTxt = (TextView) itemView.findViewById(R.id.txt_home_rec_title);
            homeItemDescriptionTxt = (TextView) itemView.findViewById(R.id.txt_home_rec_description);
            homeItemDetailsTxt = (TextView) itemView.findViewById(R.id.txt_check_details);
            homeStateImg = (ImageView) itemView.findViewById(R.id.img_home_rec_state);
            homeTimeStateImg = (ImageView) itemView.findViewById(R.id.img_home_rec_time_state);
        }
    }

    /**
     * 底部显示加载信息的Footer
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerTxt;

        FooterViewHolder(View itemView) {
            super(itemView);
            footerTxt = (TextView) itemView.findViewById(R.id.txt_rec_loading);
        }
    }
}
