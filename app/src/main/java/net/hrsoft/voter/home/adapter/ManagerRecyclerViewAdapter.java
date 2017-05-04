package net.hrsoft.voter.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.home.model.UserVoteItem;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YangCihang.
 * @since 2017/4/18 0018.
 */

public class ManagerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<UserVoteItem> userVoteItems;
    private Vector<Boolean> flag;//标记下拉菜单的位置，如果可见为true，不可见为false
    private ItemClickListener itemClickListener;
    private DeleteBtnClickListener deleteBtnClickListener;
    private CheckedBtnClickListener checkedBtnClickListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public static final int PULL_UP_LOAD_MORE = 0;   //上拉加载更多
    public static final int LOADING_MORE = 1;//正在加载中
    public static final int LOADING_FINISH = 2;//加载完毕所有数据时
    private int load_more_status = 0;//上拉加载更多状态-默认为0
    private int footerCount = 1;

    public ManagerRecyclerViewAdapter(List<UserVoteItem> list) {
        userVoteItems = list;
        flag = new Vector<>();
    }

    /**
     * 加载数据一页数据
     *
     * @param voteData 从请求中获取的数据组
     */
    public void addPageOfVoteItem(List<UserVoteItem> voteData) {
        userVoteItems.addAll(voteData);
        for (UserVoteItem userVoteItem : userVoteItems) {
            flag.add(false);
        }
        notifyDataSetChanged();
    }

    public void addVoteItem(UserVoteItem voteItem) {
        userVoteItems.add(voteItem);
        flag.add(false);
        notifyDataSetChanged();
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
     * 根据类型加载不同的view
     *
     * @param parent   parent
     * @param viewType viewType
     * @return 返回界面类型的view
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_loading_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_manager, parent, false);
            return new VoteItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VoteItemHolder) {
            UserVoteItem userVoteItem = userVoteItems.get(position);
            ((VoteItemHolder) holder).manageTitleTxt.setText(userVoteItem.getTitle());
            ((VoteItemHolder) holder).manageDescriptTxt.setText(userVoteItem.getDescription());
            long endTime = userVoteItem.getEndTime();
            long currentTime = System.currentTimeMillis();
            long subTime = 18000000;//时间间隔是这么多时进行状态判定
            if (endTime - currentTime < subTime && endTime - currentTime > 0) {
                ((VoteItemHolder) holder).timeStateImg.setImageResource(R.drawable.ic_nearline);
                ((VoteItemHolder) holder).stateImg.setImageResource(R.drawable.ic_warningtime);
            } else if (endTime - currentTime <= 0) {
                ((VoteItemHolder) holder).timeStateImg.setImageResource(R.drawable.ic_ended);
                ((VoteItemHolder) holder).stateImg.setImageResource(R.drawable.ic_endedhead);
            } else {
                ((VoteItemHolder) holder).timeStateImg.setImageResource(R.drawable.ic_running);
                ((VoteItemHolder) holder).stateImg.setImageResource(R.drawable.ic_home_hot);
            }
            ((VoteItemHolder) holder).itemView.setTag(position); //标记
            if (!flag.get(position) && !flag.isEmpty()) {
                ((VoteItemHolder) holder).manageMenuLayout.setVisibility(View.GONE);
            } else {
                ((VoteItemHolder) holder).manageMenuLayout.setVisibility(View.VISIBLE);
            }
            ((VoteItemHolder) holder).itemView.setOnClickListener(this);
            ((VoteItemHolder) holder).deleteBtn.setTag(R.id.id_of_deleted_vote, userVoteItem.getId());
            ((VoteItemHolder) holder).deleteBtn.setTag(R.id.position_of_deleted_vote, position);
            ((VoteItemHolder) holder).deleteBtn.setOnClickListener(this); //删除按钮的监听
            ((VoteItemHolder) holder).detailBtn.setTag(userVoteItem);
            ((VoteItemHolder) holder).detailBtn.setOnClickListener(this); //查看按钮的监听
        } else {
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


    public Vector<Boolean> getPositionFlag() {
        return flag;
    }

    @Override
    public int getItemCount() {
        return userVoteItems.size() + footerCount;
    }


    /**
     * 点击事件
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_recyclerview_manager:
                if (itemClickListener != null) {
                    int selectPosition = (int) v.getTag(); //获取选中时当前位置
                    itemClickListener.onItemClick(v, selectPosition);
                }
                break;
            case R.id.btn_menu_deleted:
                if (deleteBtnClickListener != null) {
                    long voteId = (long) v.getTag(R.id.id_of_deleted_vote);
                    int deletePosition = (int) v.getTag(R.id.position_of_deleted_vote); //获取删除时当前的位置
                    deleteBtnClickListener.onDeleteBtnClick(v, voteId, deletePosition);
                }
                break;
            case R.id.btn_menu_checked:
                if (checkedBtnClickListener != null) {
                    UserVoteItem userVoteItem = (UserVoteItem) v.getTag();
                    checkedBtnClickListener.onCheckedBtnClick(v, userVoteItem);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ItemView的点击事件
     */
    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    /**
     * 下拉菜单中删除按钮的点击事件
     */
    public interface DeleteBtnClickListener {
        void onDeleteBtnClick(View v, long voteId, int position);
    }

    /**
     * 下拉菜单中查看按钮的点击事件
     */
    public interface CheckedBtnClickListener {
        void onCheckedBtnClick(View v, UserVoteItem userVoteItem);
    }

    /**
     * 设置ItemView的监听
     *
     * @param itemClickListener itemClickListener
     */
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 设置下拉菜单中删除按钮的监听
     *
     * @param deleteBtnClickListener deleteBtnClickListener
     */
    public void setDeleteBtnClickListener(DeleteBtnClickListener deleteBtnClickListener) {
        this.deleteBtnClickListener = deleteBtnClickListener;
    }

    /**
     * 设置下拉菜单中查看按钮的监听
     *
     * @param checkedBtnClickListener checkedBtnClickListener
     */
    public void setCheckedBtnClickListener(CheckedBtnClickListener checkedBtnClickListener) {
        this.checkedBtnClickListener = checkedBtnClickListener;
    }

    /**
     * ViewHolder
     */
    class VoteItemHolder extends RecyclerView.ViewHolder {
        TextView manageTitleTxt;
        TextView manageDescriptTxt;
        LinearLayout manageMenuLayout;
        Button deleteBtn;
        Button detailBtn;
        Button editBtn;
        ImageView stateImg;
        ImageView timeStateImg;

        VoteItemHolder(View itemView) {
            super(itemView);
            manageTitleTxt = (TextView) itemView.findViewById(R.id.txt_manager_rec_title);
            manageDescriptTxt = (TextView) itemView.findViewById(R.id.txt_manager_rec_description);
            manageMenuLayout = (LinearLayout) itemView.findViewById(R.id.menu_item_layout);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_menu_deleted);
            detailBtn = (Button) itemView.findViewById(R.id.btn_menu_checked);
            editBtn = (Button) itemView.findViewById(R.id.btn_menu_edit);
            stateImg = (ImageView) itemView.findViewById(R.id.img_manager_rec_state);
            timeStateImg = (ImageView) itemView.findViewById(R.id.img_manager_rec_time_state);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerTxt;

        FooterViewHolder(View itemView) {
            super(itemView);
            footerTxt = (TextView) itemView.findViewById(R.id.txt_rec_loading);
        }
    }

    public void changeLoadingStatues(int statue) {
        load_more_status = statue;
    }
}
