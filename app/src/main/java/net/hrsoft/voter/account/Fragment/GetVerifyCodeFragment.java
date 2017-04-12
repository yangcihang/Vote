package net.hrsoft.voter.account.Fragment;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *
 * Created by abtion on 17/4/12.
 */

public class GetVerifyCodeFragment extends BaseFragment {
    @BindView(R.id.mobile_edit)
    EditText mobileEdit;
    @BindView(R.id.verify_edit)
    EditText verifyEdit;
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_get_verify_code;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_get_verify, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verify:
                new CountDownTimer(30000,1000) {


                    @Override
                    public void onTick(long millisUntilFinished) {
                        btnGetVerify.setClickable(false);
                        btnGetVerify.setText((millisUntilFinished/1000)+" s");
                        btnGetVerify.setBackgroundResource(R.drawable.btn_verify_wait);
                    }

                    @Override
                    public void onFinish() {
                        btnGetVerify.setBackgroundResource(R.drawable.btn_verify);
                        btnGetVerify.setText(R.string.get_verify_code);
                        btnGetVerify.setClickable(true);

                    }
                }.start();
                break;
            case R.id.btn_next:
                break;
        }
    }
}
