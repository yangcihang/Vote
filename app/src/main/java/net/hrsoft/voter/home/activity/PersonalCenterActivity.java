package net.hrsoft.voter.home.activity;


import android.widget.Button;
import android.widget.EditText;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.common.ToolbarActivity;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.constant.HomeSlideMenuItem;
import net.hrsoft.voter.home.model.ModifyPasswordRequest;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalCenterActivity extends ToolbarActivity {
    @BindView(R.id.btn_personal_center_modify_password)
    Button modifyPasswordBtn;
    @BindView(R.id.edit_personal_center_new_password)
    EditText newPasswordEdit;
    @BindView(R.id.edit_personal_center_conform_password)
    EditText conformPasswordEdit;

    @OnClick(R.id.btn_personal_center_modify_password)
    void onModifyPassword() {
        if (!conformPasswordEdit.getText().toString().equals(newPasswordEdit.getText().toString())) {
            ToastUtil.showToast(PersonalCenterActivity.this, R.string.toast_personal_password_different);
        } else if (conformPasswordEdit.getText().length() < 6 || conformPasswordEdit.getText().length() > 20) {
            ToastUtil.showToast(PersonalCenterActivity.this, R.string.toast_personal_password_wrong);
        } else {
            ModifyPasswordRequest modifyPasswordRequest = new ModifyPasswordRequest();
            modifyPasswordRequest.setOldPassword(VoterApplication.getCache().getString(CacheKey.PASSWORD));
            modifyPasswordRequest.setNewPassword(conformPasswordEdit.getText().toString());
            sendModifyPasswordRequest(modifyPasswordRequest);
        }

    }

    private void sendModifyPasswordRequest(ModifyPasswordRequest modifyPasswordRequest) {
        RestClient.getVoterService().modifyPasswordPost(VoterApplication.getCache().getString(CacheKey.TOKEN), modifyPasswordRequest)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.code() < APICode.SERVER_RESPONSE_CODE) {
                            ToastUtil.showToast(PersonalCenterActivity.this, R.string.toast_personal_password_modify_success);
                        } else {
                            ToastUtil.showToast(PersonalCenterActivity.this, R.string.toast_net_has_question);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        ToastUtil.showToast(PersonalCenterActivity.this, R.string.toast_net_has_question);
                    }
                });
    }

    @Override
    protected void init() {
        super.init();
        this.setActivityTitle(HomeSlideMenuItem.PERSONALCENTERITEM);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }
}
