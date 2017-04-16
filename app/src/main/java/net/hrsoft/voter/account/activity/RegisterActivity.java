package net.hrsoft.voter.account.activity;


import android.app.ProgressDialog;
import android.content.Intent;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.account.Fragment.AccountSettingFragment;
import net.hrsoft.voter.account.Fragment.GetVerifyCodeFragment;
import net.hrsoft.voter.account.model.RegisterRequest;
import net.hrsoft.voter.common.ToolbarActivity;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abtion.
 */

public class RegisterActivity extends ToolbarActivity implements GetVerifyCodeFragment
        .GetVerifyCodeNextStepOnClickListener, AccountSettingFragment.DoneRegisterOnClickListener {

    private RegisterRequest request;

    @Override
    protected void initView() {
        super.initView();
        this.setActivityTitle("手机号注册");
        replaceFragment(R.id.account_container, new GetVerifyCodeFragment(), null);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contianer;
    }

    @Override
    public void getVerifyCodeNextStepOnClick(String mobile) {
        replaceFragment(R.id.account_container, new AccountSettingFragment(), null);
        request.setMobile(mobile);
        this.setActivityTitle("设置账户");
    }


    @Override
    public void doneRegisterOnClick(String username, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("拼命加载中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        request = new RegisterRequest(username, password);
        RestClient.getVoterService().register(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.code() < APICode.SERVER_RESPONSE_CODE) {
                    switch (response.body().getCode()) {
                        case APICode.SUCCESS:
                            ToastUtil.showToast(RegisterActivity.this, "注册成功，请重新登录");
                            goLogin();
                            break;
                        default:
                            ToastUtil.showToast(RegisterActivity.this, response.body().getData().toString());
                    }
                } else {
                    ToastUtil.showToast(RegisterActivity.this, "网络出现问题，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                ToastUtil.showToast(RegisterActivity.this, "网络出现问题，请稍后重试");
            }
        });
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        VoterApplication.getInstance().clearAllActivity();
        startActivity(intent);
    }

    @Override
    protected void onBackBtnOnclick() {
        if (GetVerifyCodeFragment.class.isInstance(getSupportFragmentManager().findFragmentById(R.id.account_container))) {
            this.finish();
        } else {
            this.getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (GetVerifyCodeFragment.class.isInstance(getSupportFragmentManager().findFragmentById(R.id.account_container))) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }
}
