package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * @author YangCihang.
 * @since 2017/4/13 0013.
 */

public class UserVoteItem extends BaseModel{
    private long id;
    private String title;
    private String description;
    private String anonymous;
    private long participatorLimit;
    private boolean visibilityLimit;
    private long startTime;
    private long endTime;
    private long creatorId;
    private long creatorAt;
    private long updatedAt;
    private String password;
    private long participatorNum;
    public void setId(long id) {
        this.id = id;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public long getCreatorAt() {
        return creatorAt;
    }

    public void setCreatorAt(long creatorAt) {
        this.creatorAt = creatorAt;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getParticipatorLimit() {
        return participatorLimit;
    }

    public void setParticipatorLimit(long participatorLimit) {
        this.participatorLimit = participatorLimit;
    }

    public long getParticipatorNum() {
        return participatorNum;
    }

    public void setParticipatorNum(long participatorNum) {
        this.participatorNum = participatorNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQaPath() {
        return qaPath;
    }

    public void setQaPath(String qaPath) {
        this.qaPath = qaPath;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isVisibilityLimit() {
        return visibilityLimit;
    }

    public void setVisibilityLimit(boolean visibilityLimit) {
        this.visibilityLimit = visibilityLimit;
    }

    public long getId() {
        return id;

    }

    private String qaPath;
    private boolean deleted;

}
