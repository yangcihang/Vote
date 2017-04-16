package net.hrsoft.voter.account.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.account.model.LoginResponse;
import net.hrsoft.voter.account.model.LoginReuqest;
import net.hrsoft.voter.account.model.Token;
import net.hrsoft.voter.account.model.User;
import net.hrsoft.voter.common.NoToolbarActivity;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.home.activity.HomeActivity;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.MD5;
import net.hrsoft.voter.util.RegexUtil;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends NoToolbarActivity {
    @BindView(R.id.mobile_edit)
    EditText mobileEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.txt_forget_password)
    TextView txtForgetPassword;
    @BindView(R.id.btn_weibo)
    ImageView btnWeibo;
    @BindView(R.id.btn_wechat)
    ImageView btnWechat;
    @BindView(R.id.btn_qq)
    ImageView btnQq;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.txt_register, R.id.txt_forget_password, R.id.btn_weibo, R.id.btn_wechat, R.id.btn_qq, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.txt_forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_weibo:
                ToastUtil.showToast(this, "微博登录，待完成");
                break;
            case R.id.btn_wechat:
                ToastUtil.showToast(this, "微信登录，待完成");
                break;
            case R.id.btn_qq:
                ToastUtil.showToast(this, "QQ登录，待完成");
                break;
            case R.id.btn_login:
                /*String mobile = mobileEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                 if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast(this, "请输入手机号");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToast(this, "请输入6-20位的密码");
                } else if (!RegexUtil.isMobileNum(mobile)) {
                    ToastUtil.showToast(this, "请输入正确的手机号");
                } else if (password.length() < 6) {
                    ToastUtil.showToast(this, "请输入6-20位的密码");
                } else {
                    login(mobile, MD5.getMD5(password));
                }  */
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
                VoterApplication.getInstance().clearAllActivity();
                startActivity(intent);
                break;
            default:
                throw new NullPointerException(this.toString() + "no this choice");
        }
    }

    private void login(String mobile, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("拼命加载中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        LoginReuqest reuqest = new LoginReuqest(mobile, password);
        RestClient.getVoterService().login(reuqest).enqueue(new Callback<APIResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<LoginResponse>> call, Response<APIResponse<LoginResponse>> response) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.code() < APICode.SERVER_RESPONSE_CODE) {
                    switch (response.body().getCode()) {
                        case APICode.SUCCESS:
                            loginSuccess(response.body().getData().getUser(), response.body().getData().getToken());
                            break;
                        default:
                            ToastUtil.showToast(LoginActivity.this,response.body().getData().toString());
                    }
                } else {
                    ToastUtil.showToast(LoginActivity.this, "网络出现问题，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<APIResponse<LoginResponse>> call, Throwable t) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                ToastUtil.showToast(LoginActivity.this, "网络出现问题，请稍后重试");
            }
        });
    }

    /**
     * 登录成功,存储token,跳转页面,清除所有的Activity
     */
    private void loginSuccess(User user, Token token) {
        VoterApplication.getCache().putSerializableObj(CacheKey.USER, user);
        VoterApplication.getCache().putSerializableObj(CacheKey.TOKEN, token);
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, HomeActivity.class);
        VoterApplication.getInstance().clearAllActivity();
        startActivity(intent);
    }


}
