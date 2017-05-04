package net.hrsoft.voter.home.activity;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.common.ToolbarActivity;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.constant.HomeSlideMenuItem;
import net.hrsoft.voter.home.adapter.ManagerRecyclerViewAdapter;
import net.hrsoft.voter.home.fragment.HomeFragment;
import net.hrsoft.voter.home.model.UserVoteInfoResponse;
import net.hrsoft.voter.home.model.UserVoteItem;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageVoteActivity extends ToolbarActivity {

    @BindView(R.id.rec_manager_vote)
    RecyclerView manageVoteRec;
    @BindView(R.id.txt_manager_default)
    TextView defaultTxt;
    ManagerRecyclerViewAdapter managerRecyclerViewAdapter;
    private List<UserVoteItem> userVoteItems;
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;//页数
    private final static int ROWS = 8; //一页8条
    private final static int FIRST_PAGE = 1;//首页
    private int lastPage;//最后一页
    private static boolean isLastItem = false;//是否是最后一条
    private static int voteTotalItems;//投票的总数

    /**
     * 当创建投票后，设置投票总数加一
     */
    public static void addVoteTotalItems() {
        voteTotalItems++;
        isLastItem = false;
    }

    /**
     * 设置title
     */
    @Override
    protected void init() {
        super.init();
        this.setActivityTitle(HomeSlideMenuItem.VOTEMANAGEITEM);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        linearLayoutManager = new LinearLayoutManager(this);//加载布局管理
        final int[] lastVisibleItem = new int[1];//记载最后一个位置
        userVoteItems = new ArrayList<>();
        managerRecyclerViewAdapter = new ManagerRecyclerViewAdapter(userVoteItems);
        sendVoteItemsInformationRequest(FIRST_PAGE, ROWS);//第一次启动时候获取信息

        manageVoteRec.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem[0] = linearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem[0] + 1 == managerRecyclerViewAdapter.getItemCount()) {
                    /**
                     * 当下拉到底部时
                     */
                    if (userVoteItems.size() < voteTotalItems) {
                        /**
                         * 当小于总数时
                         */
                        managerRecyclerViewAdapter.changeLoadingStatues(ManagerRecyclerViewAdapter.LOADING_MORE);
                        managerRecyclerViewAdapter.notifyDataSetChanged();//改变底部栏状态
                        if (page == lastPage) {
                            sendVoteItemsInformationRequest(page, ROWS);//当是最后一页时，页数不在自加
                            isLastItem = true;//获取最后一页之后，设为true
                        } else {
                            sendVoteItemsInformationRequest(++page, ROWS);
                        }
                        managerRecyclerViewAdapter.changeLoadingStatues(ManagerRecyclerViewAdapter.PULL_UP_LOAD_MORE);
                        managerRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        managerRecyclerViewAdapter.changeLoadingStatues(ManagerRecyclerViewAdapter.LOADING_FINISH);
                        managerRecyclerViewAdapter.notifyDataSetChanged();
                    }

                }
            }
        });//recyclerView// iew设置滚动事件
    }

    /**
     * 初始化Recycler
     */
    private void initRecyclerView() {
        super.initView();
        if (!userVoteItems.isEmpty()) {
            defaultTxt.setVisibility(View.GONE);
            manageVoteRec.setLayoutManager(linearLayoutManager);
            manageVoteRec.setAdapter(managerRecyclerViewAdapter);
            setOnManagerVoteRecListener();
        } else {
            manageVoteRec.setVisibility(View.GONE);
            defaultTxt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发起获取投票信息的请求
     *
     * @param page 需要请求的页码
     * @param rows 请求的条数
     */
    private void sendVoteItemsInformationRequest(final int page, final int rows) {
        RestClient.getVoterService().userVoteInfoGet(VoterApplication.getCache().getString(CacheKey.TOKEN), page, rows).enqueue(new Callback<APIResponse<UserVoteInfoResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<UserVoteInfoResponse>> call, Response<APIResponse<UserVoteInfoResponse>> response) {
                if (response.body().getCode() < APICode.SERVER_RESPONSE_CODE) {
                    voteTotalItems = response.body().getData().getTotal();
                    lastPage = response.body().getData().getLastPage();
                    if (page == FIRST_PAGE) {
                        managerRecyclerViewAdapter.addPageOfVoteItem(response.body().getData().getList());//第一次的加载数据
                        initRecyclerView();
                    } else if (!isLastItem) {
                        managerRecyclerViewAdapter.addPageOfVoteItem(response.body().getData().getList());//加载数据
                    } else {
                        int theLastPosition = response.body().getData().getList().size() - 1;
                        managerRecyclerViewAdapter.addVoteItem(response.body().getData().getList().get(theLastPosition));
                        isLastItem = true;
                    }
                } else {
                    ToastUtil.showToast(ManageVoteActivity.this, R.string.toast_net_has_question);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<UserVoteInfoResponse>> call, Throwable t) {
                ToastUtil.showToast(ManageVoteActivity.this, R.string.toast_net_has_question);
            }
        });
    }

    /**
     * 设置rec中各个view的监听事件
     */
    private void setOnManagerVoteRecListener() {
        managerRecyclerViewAdapter.setItemClickListener(new ManagerRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Vector<Boolean> positionFlag = managerRecyclerViewAdapter.getPositionFlag();
                boolean flag = positionFlag.get(position);//获取位置标记
                int count = 0;
                while (count < positionFlag.size()) {
                    positionFlag.set(count, false);
                    count += 1;
                }
                positionFlag.set(position, !flag);
                managerRecyclerViewAdapter.notifyDataSetChanged();//刷新页面
            }
        });//设置itemView的监听事件

        managerRecyclerViewAdapter.setDeleteBtnClickListener(new ManagerRecyclerViewAdapter.DeleteBtnClickListener() {
            @Override
            public void onDeleteBtnClick(View v, long voteId, int position) {
                showDeleteDialog(voteId, position);
            }
        });//设置deleteView的监听事件
        managerRecyclerViewAdapter.setCheckedBtnClickListener(new ManagerRecyclerViewAdapter.CheckedBtnClickListener() {
            @Override
            public void onCheckedBtnClick(View v, UserVoteItem userVoteItem) {
                Intent intent = new Intent(ManageVoteActivity.this, VoteStatisticsActivity.class);
                intent.putExtra(CacheKey.USERVOTEITEMDATA, userVoteItem);
                startActivity(intent);
            }
        });
    }


    /**
     * 展示删除投票时候的对话框
     */
    private void showDeleteDialog(final long voteId, final int position) {
        View view = LayoutInflater.from(ManageVoteActivity.this).inflate(R.layout.dialog_vote_delete_layout, null, false);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = null;
        if (window != null) {
            lp = window.getAttributes();
        }
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
        }
        if (lp != null) {
            lp.width = getResources().getDisplayMetrics().widthPixels;//宽度;
        }
        dialog.onWindowAttributesChanged(lp);
        if (window != null) {
            window.setAttributes(lp);
        }
        dialog.show();
        view.findViewById(R.id.btn_dialog_delete_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDeleteVoteRequest(voteId, position);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void sendDeleteVoteRequest(long voteID, final int position) {
        RestClient.getVoterService().deleteVotePost(voteID).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.code() < APICode.SERVER_RESPONSE_CODE) {
                    ToastUtil.showToast(ManageVoteActivity.this, R.string.toast_delete_success);
                    userVoteItems.remove(position);
                    managerRecyclerViewAdapter.notifyDataSetChanged();
                    HomeFragment.getHomeFragmentInstance().getDeleteVoteInfo(position);//通知主页面进行删除
                } else {
                    ToastUtil.showToast(ManageVoteActivity.this, R.string.toast_net_has_question);
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                ToastUtil.showToast(ManageVoteActivity.this, R.string.toast_net_has_question);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_vote;
    }
}
