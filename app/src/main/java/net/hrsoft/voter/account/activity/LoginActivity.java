package net.hrsoft.voter.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.NoToolbarActivity;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

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
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.txt_forget_password:
                break;
            case R.id.btn_weibo:
                ToastUtil.showToast(this,"微博登录，待完成");
                break;
            case R.id.btn_wechat:
                ToastUtil.showToast(this,"微信登录，待完成");
                break;
            case R.id.btn_qq:
                ToastUtil.showToast(this,"QQ登录，待完成");
                break;
            case R.id.btn_login:
                break;
            default:
                throw new NullPointerException(this.toString()+"no this choice");
        }
    }


}
