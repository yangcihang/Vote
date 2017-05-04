package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

import java.util.ArrayList;

/**
 * @author YangCihang.
 * @since 2017/4/19 0019.
 */

public class CreateVoteRequest extends BaseModel {
    private String title; //投票名称
    private String description; //投票描述
    private boolean anonymous; //是否匿名
    private int participatorLimit; //参与人数限制
    private boolean visibilityLimit; //是否私有
    private String password; //密码。
    private long startTime; //开始时间
    private long endTime; //结束时间
    private ArrayList<Problem> problems; //问题组

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEnd_time() {
        return endTime;
    }

    public void setEnd_time(long end_time) {
        this.endTime = end_time;
    }

    public int getParticipatorLimit() {
        return participatorLimit;
    }

    public void setParticipatorLimit(int participatorLimit) {
        this.participatorLimit = participatorLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void setProblems(ArrayList<Problem> problems) {
        this.problems = problems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isVisibilityLimit() {
        return visibilityLimit;
    }

    public void setVisibilityLimit(boolean visibilityLimit) {
        this.visibilityLimit = visibilityLimit;
    }
}
