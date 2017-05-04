package net.hrsoft.voter.account.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.hrsoft.voter.R;
import net.hrsoft.voter.common.BaseFragment;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author abtion on 17/4/13.
 */

public class AccountSettingFragment extends BaseFragment {
    private DoneRegisterOnClickListener listener;

    @BindView(R.id.btn_choose_photo)
    Button btnChoosePhoto;
    @BindView(R.id.username_edit)
    EditText usernameEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.password_check)
    EditText passwordCheck;
    @BindView(R.id.btn_done_register)
    Button btnDoneRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_setting;
    }


    @OnClick({R.id.btn_choose_photo, R.id.btn_done_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_photo:
                ToastUtil.showToast(getActivity(), "来呀选照片啊");
                break;
            case R.id.btn_done_register:
                String username = usernameEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                listener.doneRegisterOnClick(username, password);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DoneRegisterOnClickListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implement AccountSettingFragment.");
        }
    }

    /**
     * 该接口用于传递用户名及密码给Activity
     */
    public interface DoneRegisterOnClickListener {
        void doneRegisterOnClick(String username, String password);
    }

}
