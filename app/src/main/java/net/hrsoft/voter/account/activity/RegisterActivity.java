package net.hrsoft.voter.account.activity;



import net.hrsoft.voter.R;
import net.hrsoft.voter.account.Fragment.GetVerifyCodeFragment;
import net.hrsoft.voter.common.ToolbarActivity;

/**
 *
 * Created by abtion on 17/4/12.
 */

public class RegisterActivity extends ToolbarActivity {

    @Override
    protected void initView() {
        super.initView();
        this.setActivityTitle("手机号注册");
        replaceFragment(R.id.register_view_container,new GetVerifyCodeFragment(),null);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

}
