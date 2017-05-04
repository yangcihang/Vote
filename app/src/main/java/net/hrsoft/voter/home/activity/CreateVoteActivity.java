package net.hrsoft.voter.home.activity;

import android.app.DatePickerDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import net.hrsoft.voter.R;
import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.common.ToolbarActivity;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.home.adapter.CreateVoteAdapter;
import net.hrsoft.voter.home.fragment.HomeFragment;
import net.hrsoft.voter.home.model.CreateVoteRequest;
import net.hrsoft.voter.home.model.Problem;
import net.hrsoft.voter.home.model.QuestionValue;
import net.hrsoft.voter.network.APICode;
import net.hrsoft.voter.network.APIResponse;
import net.hrsoft.voter.network.RestClient;
import net.hrsoft.voter.util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVoteActivity extends ToolbarActivity implements CreateVoteAdapter.SaveEditListener {

    @BindView(R.id.rec_create_vote)
    RecyclerView voteItemRec;
    @BindView(R.id.txt_add_item)
    TextView addVoteItemTxt;
    @BindView(R.id.btn_create_vote)
    Button createVoteBtn;
    @BindView(R.id.edit_create_vote_descript)
    EditText descriptEdit;
    @BindView(R.id.edit_create_vote_title)
    EditText titleEdit;
    @BindView(R.id.txt_create_vote_endtime)
    TextView endTimeTxt;
    @BindView(R.id.edit_create_vote_participator_limit)
    EditText participatorLimitEdit;
    @BindView(R.id.edit_create_vote_password)
    EditText passwordEdit;
    @BindView(R.id.radio_create_vote_type_single)
    RadioButton singleRadio;
    @BindView(R.id.radio_create_vote_type_double)
    RadioButton doubleRadio;
    @BindView(R.id.radio_group_vote_type)
    RadioGroup groupTypeRadio;
    @BindView(R.id.swh_create_vote_anonymous)
    Switch anonymousSwh;
    @BindView(R.id.img_create_vote_set_end_date)
    ImageView setDataImg;
    @BindView(R.id.txt_create_vote_set_date)
    TextView setDateTxt;
    @BindView(R.id.swh_create_vote_password)
    Switch passwordSwh;
    @BindView(R.id.swh_create_vote_people_limit)
    Switch peopleLimitSwh;

    final CreateVoteRequest createVoteRequest = new CreateVoteRequest();//创建投票的请求
    private List<String> voteItemContent = new ArrayList<>();
    private CreateVoteAdapter createVoteAdapter;
    private long endTime = 0; //截止的日期
    final Problem problem = new Problem();

    @OnClick(R.id.txt_add_item)
    void addItem() {
        createVoteAdapter.addItem();
        createVoteAdapter.notifyItemInserted(voteItemContent.size());//刷新列表，用notifyItemInsert方法
    }

    @OnClick(R.id.img_create_vote_set_end_date)
    void onSetDat() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(CreateVoteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time = String.valueOf(year + "-" + month + "-" + dayOfMonth);
                setDateTxt.setText(time);
                try {
                    endTime = new SimpleDateFormat("yyyy-MM-dd").parse(time).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, iYear, iMonth, iDay).show();
    }

    /**
     * 设置是否匿名
     */
    @OnCheckedChanged(R.id.swh_create_vote_anonymous)
    void onSetAnonymous() {
        if (anonymousSwh.isChecked()) {
            createVoteRequest.setAnonymous(true);
        } else {
            createVoteRequest.setAnonymous(false);
        }
    }

    /**
     * 设置人数限制
     */
    @OnCheckedChanged(R.id.swh_create_vote_people_limit)
    void onPeopleLimit() {
        if (peopleLimitSwh.isChecked()) {
            participatorLimitEdit.setFocusableInTouchMode(true);
            participatorLimitEdit.setFocusable(true);
        } else {
            participatorLimitEdit.setFocusableInTouchMode(false);
            participatorLimitEdit.setFocusable(false);
            createVoteRequest.setParticipatorLimit(0);
        }
    }

    /**
     * 设置是否需要密码
     */
    @OnCheckedChanged(R.id.swh_create_vote_password)
    void onPasswordChecked() {
        if (!passwordSwh.isChecked()) {
            passwordEdit.setFocusableInTouchMode(false);
            passwordEdit.setFocusable(false);
            passwordEdit.setText("");
        } else {
            passwordEdit.setFocusableInTouchMode(true);
            passwordEdit.setFocusable(true);
        }
    }

    /**
     * 获取得到的信息，发送请求
     */
    @OnClick(R.id.btn_create_vote)
    void onCreateVote() {
        // TODO: 2017/5/3 0003  
        if (TextUtils.isEmpty(titleEdit.getText().toString().trim())) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_no_title);
        } else if (endTime == 0) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_no_end_time);
        } else if (voteItemContent.size() == 0) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_no_question_options);
        } else if (peopleLimitSwh.isChecked() && TextUtils.isEmpty(participatorLimitEdit.getText().toString().trim())) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_num_limit_is_wrong);
        } else if (peopleLimitSwh.isChecked() && participatorLimitEdit.getText().toString().length() <= 0) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_people_num_is_wrong);
        } else if (passwordSwh.isChecked() && TextUtils.isEmpty(passwordEdit.getText().toString().trim())) {
            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_no_password);
        } else {
            ArrayList<Problem> problems = new ArrayList<>();
            ArrayList<QuestionValue> questionValues = new ArrayList<>();
            for (int i = 0; i < voteItemContent.size(); i++) {
                QuestionValue questionValue = new QuestionValue();
                if (TextUtils.isEmpty(voteItemContent.get(i).trim())) {
                    ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_options_is_empty);
                }
                questionValue.setValue(voteItemContent.get(i));
                questionValues.add(questionValue);
            }//添加问题选项
            problem.setType(1);
            problem.setTitle(titleEdit.getText().toString().trim());
            problem.setOptions(questionValues);
            problems.add(problem);

            if (singleRadio.isChecked()) {
                problem.setType(1);
            }//当选择单选时
            if (doubleRadio.isChecked()) {
                problem.setType(2);
            }//当选择多选时
            if (peopleLimitSwh.isChecked()) {
                createVoteRequest.setParticipatorLimit(Integer.valueOf(participatorLimitEdit.getText().toString()));
                //当选中时，获取得到的人数
            } else {
                createVoteRequest.setParticipatorLimit(0);
            }//未选中时，将人数设置为无限制
            if (passwordSwh.isChecked()) {
                createVoteRequest.setVisibilityLimit(true);
                createVoteRequest.setPassword(passwordEdit.getText().toString());//当需要密码设定为私有，并获取密码
            } else {
                createVoteRequest.setVisibilityLimit(false);
                createVoteRequest.setPassword(null);//当没有密码设定时，设置有公有
            }
            createVoteRequest.setDescription(descriptEdit.getText().toString());
            createVoteRequest.setStartTime(System.currentTimeMillis());
            createVoteRequest.setTitle(titleEdit.getText().toString());
            createVoteRequest.setProblems(problems);
            createVoteRequest.setAnonymous(false);
            createVoteRequest.setEnd_time(endTime);
            sendCreateVoteRequest(createVoteRequest);
        }
    }

    /**
     * 发送创建投票的请求
     *
     * @param request 请求体
     */
    private void sendCreateVoteRequest(final CreateVoteRequest request) {
        RestClient.getVoterService().createVotePost(VoterApplication.getCache().getString(CacheKey.TOKEN), request)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.body().getCode() < APICode.SERVER_RESPONSE_CODE) {
                            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_success);
                            /**
                             * 投票总数加一
                             */
                            ManageVoteActivity.addVoteTotalItems();
                            HomeFragment.addVoteTotalItems();
                        } else {
                            ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_create_vote_has_error);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        ToastUtil.showToast(CreateVoteActivity.this, R.string.toast_net_has_question);
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_vote;
    }

    /**
     * LayoutManager和RecyclerView的初始化
     */
    @Override
    protected void init() {
        super.init();
        LinearLayoutManager recLinearLayoutManager;
        createVoteAdapter = new CreateVoteAdapter(voteItemContent, CreateVoteActivity.this);
        recLinearLayoutManager = new LinearLayoutManager(CreateVoteActivity.this);
        voteItemRec.setLayoutManager(recLinearLayoutManager);
        voteItemRec.setAdapter(createVoteAdapter);
        this.setActivityTitle(getResources().getString(R.string.activity_creat_vote_title));
        singleRadio.setChecked(true);
        passwordSwh.setChecked(false);
        participatorLimitEdit.setFocusable(false);
        participatorLimitEdit.setFocusableInTouchMode(false);
        passwordEdit.setFocusableInTouchMode(false);
        passwordEdit.setFocusable(false); //初始时密码和人数限制的Edit设置为不可操作
        createVoteRequest.setParticipatorLimit(0); //初始为无限制
        createVoteRequest.setAnonymous(false);//初始为不匿名
    }

    /**
     * 回调监听Edit内容的变化
     *
     * @param position 监听Edit的位置
     * @param content  Edit中的内容
     */
    @Override
    public void saveEdit(int position, String content) {
        Log.d("tag", "saveEdit: " + position);
        voteItemContent.set(position, content);
    }
}
