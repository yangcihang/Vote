package net.hrsoft.voter.account.fragment;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.BaseFragment;
import net.hrsoft.voter.util.RegexUtil;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by abtion on 17/4/12.
 */

public class GetVerifyCodeFragment extends BaseFragment {
    private GetVerifyCodeNextStepOnClickListener listener;
    private int verifyCode = 0;


    @BindView(R.id.mobile_edit)
    EditText mobileEdit;
    @BindView(R.id.verify_edit)
    EditText verifyEdit;
    @BindView(R.id.btn_get_verify)
    Button btnGetVerify;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_get_verify_code;
    }


    @OnClick({R.id.btn_get_verify, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_get_verify:

                verifyCode = (int) (Math.random() * 10000);
                ToastUtil.showToast(getActivity(), "你的验证码是" + verifyCode);
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btnGetVerify.setClickable(false);
                        btnGetVerify.setText((millisUntilFinished / 1000) + " s");
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
                String mobile = mobileEdit.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast(getActivity(), "请输入手机号");
                } else if (!RegexUtil.isMobileNum(mobile)) {
                    ToastUtil.showToast(getActivity(), "请输入正确的手机号");
                } else {
                    int verifyCodeCheck = Integer.parseInt(verifyEdit.getText().toString().trim());
                    if (verifyCode == verifyCodeCheck) {
                        listener.getVerifyCodeNextStepOnClick(mobile);
                    } else {
                        ToastUtil.showToast(getActivity(), "请输入正确的验证码");
                    }
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GetVerifyCodeNextStepOnClickListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + "must implement " +
                    "GetVerifyCodeNextStepOnClickListener");
        }
    }

    public interface GetVerifyCodeNextStepOnClickListener {
        void getVerifyCodeNextStepOnClick(String mobile);
    }


}
