package net.hrsoft.voter.home.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.common.NoToolbarActivity;
import net.hrsoft.voter.constant.HomeSlideMenuItem;
import net.hrsoft.voter.home.adapter.HomeMenuListAdapter;
import net.hrsoft.voter.home.fragment.HomeFragment;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;


import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeVoteActivity extends NoToolbarActivity {
    @BindView(R.id.listview_home)
    ListView homeSlideList;
    @BindView(R.id.txt_exit)
    TextView exitTxt;

    @OnClick(R.id.txt_exit)
    void onExit() {
        exitDialog();
    }

    private HomeFragment homeFragment;
    private static final int VOTE_MANAGER_POSITION = 0;//投票管理的位置
    private static final int PERSONAL_CENTER_POSITION = 1;//个人中心的位置

    /**
     * 初始化变量和ListView
     */
    @Override
    protected void initVariable() {
        super.initVariable();
        setHomeSlideMenuItem();//设置侧滑List
        homeFragment = new HomeFragment();
        replaceFragment(R.id.fra_home, homeFragment, null);
    }

    /**
     * 初始化侧滑List
     */
    private void setHomeSlideMenuItem() {
        HomeMenuListAdapter homeMenuListAdapter;
        homeMenuListAdapter = new HomeMenuListAdapter();
        homeMenuListAdapter.addItem(HomeSlideMenuItem.VOTEMANAGEITEM);
        homeMenuListAdapter.addItem(HomeSlideMenuItem.PERSONALCENTERITEM);
        homeSlideList.setAdapter(homeMenuListAdapter);
        homeSlideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case VOTE_MANAGER_POSITION:
                        startActivity(new Intent(HomeVoteActivity.this, ManageVoteActivity.class));
                        break;
                    case PERSONAL_CENTER_POSITION:
                        startActivity(new Intent(HomeVoteActivity.this, PersonalCenterActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 点击返回退出
     *
     * @param keyCode keyCode
     * @param event   event
     * @return return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitDialog();
        }
        return false;
    }

    /**
     * 退出时候提示的对话框
     */
    private void exitDialog() {
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        isExit.setTitle("系统提示");
        isExit.setMessage("确定要退出吗");
        isExit.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RestClient.getVoterService().logoutPost().enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.body().getCode() < APICode.SERVER_RESPONSE_CODE) {
                            VoterApplication.getInstance().clearAllActivity();
                        } else {
                            ToastUtil.showToast(HomeVoteActivity.this, R.string.toast_net_has_question);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        ToastUtil.showToast(HomeVoteActivity.this, R.string.toast_net_has_question);
                    }
                });

            }
        });
        isExit.setButton2("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        isExit.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_vote;
    }
}

