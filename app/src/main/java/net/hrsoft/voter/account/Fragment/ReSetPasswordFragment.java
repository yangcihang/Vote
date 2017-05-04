package net.hrsoft.voter.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.BaseFragment;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author abtion.
 */

public class ReSetPasswordFragment extends BaseFragment {


    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.password_check)
    EditText passwordCheck;
    @BindView(R.id.btn_done_register)
    Button btnDoneRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_password;
    }


    @OnClick(R.id.btn_done_register)
    public void onViewClicked() {
        ToastUtil.showToast(getActivity(), "修改完成");
    }
}
