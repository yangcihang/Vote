package net.hrsoft.voter.account.activity;

import net.hrsoft.voter.R;
import net.hrsoft.voter.account.Fragment.GetVerifyCodeFragment;
import net.hrsoft.voter.account.Fragment.ReSetPasswordFragment;
import net.hrsoft.voter.common.ToolbarActivity;

/**
 * @author abtion.
 */

public class ForgetPasswordActivity extends ToolbarActivity implements GetVerifyCodeFragment.GetVerifyCodeNextStepOnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contianer;
    }

    @Override
    protected void initView() {
        super.initView();
        replaceFragment(R.id.account_container, new GetVerifyCodeFragment(), null);
        setActivityTitle("忘记密码");
    }

    @Override
    public void getVerifyCodeNextStepOnClick(String mobile) {
        replaceFragment(R.id.account_container, new ReSetPasswordFragment(), null);
        setActivityTitle("重置密码");
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
