package net.hrsoft.voter.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.common.BaseFragment;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.home.adapter.HomeRecyclerViewAdapter;
import net.hrsoft.voter.home.model.UserVoteInfoResponse;
import net.hrsoft.voter.home.model.UserVoteItem;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author YangCihang.
 * @since 2017/4/11 0011.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.txt_home_default)//用户没有投票信息时的一段txt提示
            TextView homeDefaultTxt;
    @BindView(R.id.rec_home)
    RecyclerView homeRecyclerView;

    LinearLayoutManager linearLayoutManager;
    private int page = 1;//页数
    private final static int ROWS = 8; //一页8条
    private final static int FIRST_PAGE = 1;//首页
    private int lastPage;//最后一页
    private static boolean isLastItem = false;//是否是最后一条
    private List<UserVoteItem> homeRecItemList;//投票信息的list
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;//adapter
    private static int voteTotalItems;
    private static HomeFragment instance;

    /**
     * 当创建投票后，设置投票总数加一
     */
    public static void addVoteTotalItems() {
        voteTotalItems++;
        isLastItem = false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 初始化变量，获取用户所有投票的信息，设置recyclerView的下拉滑动事件
     */
    @Override
    protected void initVariable() {
        super.initVariable();
        instance = this;
        linearLayoutManager = new LinearLayoutManager(getContext());//加载布局管理
        final int[] lastVisibleItem = new int[1];//记载最后一个位置
        homeRecItemList = new ArrayList<>();
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(homeRecItemList);
        sendVoteItemsInformationRequest(FIRST_PAGE, ROWS);//第一次启动时候获取信息

        homeRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem[0] = linearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem[0] + 1 == homeRecyclerViewAdapter.getItemCount()) {
                    /**
                     * 当下拉到底部时
                     */
                    if (homeRecItemList.size() < voteTotalItems) {
                        /**
                         * 当小于总数时
                         */
                        homeRecyclerViewAdapter.changeLoadingStatues(HomeRecyclerViewAdapter.LOADING_MORE);
                        homeRecyclerViewAdapter.notifyDataSetChanged();//改变底部栏状态
                        if (page == lastPage) {
                            sendVoteItemsInformationRequest(page, ROWS);//当是最后一页时，页数不在自加
                            isLastItem = true;//获取最后一页之后，设为true
                        } else {
                            sendVoteItemsInformationRequest(++page, ROWS);
                        }
                        homeRecyclerViewAdapter.changeLoadingStatues(HomeRecyclerViewAdapter.PULL_UP_LOAD_MORE);
                        homeRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        homeRecyclerViewAdapter.changeLoadingStatues(HomeRecyclerViewAdapter.LOADING_FINISH);
                        homeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                }
            }
        });//recyclerView// iew设置滚动事件
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        super.initView();
        if (!homeRecItemList.isEmpty()) {
            homeDefaultTxt.setVisibility(View.GONE);
            homeRecyclerView.setLayoutManager(linearLayoutManager);
            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        } else {
            homeRecyclerView.setVisibility(View.GONE);
            homeDefaultTxt.setVisibility(View.VISIBLE);
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
                        homeRecItemList.addAll(response.body().getData().getList());//第一次的加载数据
                        initRecyclerView();
                    } else if (!isLastItem) {
                        homeRecItemList.addAll(response.body().getData().getList());//加载数据
                        homeRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        int theLastPosition = response.body().getData().getList().size() - 1;
                        homeRecItemList.add(response.body().getData().getList().get(theLastPosition));
                        homeRecyclerViewAdapter.notifyDataSetChanged();
                        isLastItem = true;
                    }
                } else {
                    ToastUtil.showToast(getContext(), R.string.toast_net_has_question);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<UserVoteInfoResponse>> call, Throwable t) {
                ToastUtil.showToast(getContext(), R.string.toast_net_has_question);
            }
        });
    }

    /**
     * @return 返回当前实例
     */
    public static HomeFragment getHomeFragmentInstance() {
        return instance;
    }

    public void getDeleteVoteInfo(int position) {
        homeRecItemList.remove(position);
        homeRecyclerViewAdapter.notifyDataSetChanged();
    }
}
