package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;

/**
 * Created by abtion on 17/4/16.
 */

public class Votes extends BaseModel {
    private long id;
    private String tittle;
    private String description;
    private boolean anonymous;
    private long participatorLimit;
    private boolean visibilityLimit;
    private long starTime;
    private long endTime;
    private long creatorId;
    private long creatorAt;
    private long updatedAt;
    private String password;
    private long participatorNum;
    private String qaPath;
    private boolean deleted;



    public Votes(long id, String tittle, String description, boolean anonymous, long participatorLimit, boolean visibilityLimit, long starTime, long endTime, long creatorId, long creatorAt, long updatedAt, String password, long participatorNum, String qaPath, boolean deleted) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.anonymous = anonymous;
        this.participatorLimit = participatorLimit;
        this.visibilityLimit = visibilityLimit;
        this.starTime = starTime;
        this.endTime = endTime;
        this.creatorId = creatorId;
        this.creatorAt = creatorAt;
        this.updatedAt = updatedAt;
        this.password = password;
        this.participatorNum = participatorNum;
        this.qaPath = qaPath;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public long getParticipatorLimit() {
        return participatorLimit;
    }

    public void setParticipatorLimit(long participatorLimit) {
        this.participatorLimit = participatorLimit;
    }

    public boolean isVisibilityLimit() {
        return visibilityLimit;
    }

    public void setVisibilityLimit(boolean visibilityLimit) {
        this.visibilityLimit = visibilityLimit;
    }

    public long getStarTime() {
        return starTime;
    }

    public void setStarTime(long starTime) {
        this.starTime = starTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public long getCreatorAt() {
        return creatorAt;
    }

    public void setCreatorAt(long creatorAt) {
        this.creatorAt = creatorAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getParticipatorNum() {
        return participatorNum;
    }

    public void setParticipatorNum(long participatorNum) {
        this.participatorNum = participatorNum;
    }

    public String getQaPath() {
        return qaPath;
    }

    public void setQaPath(String qaPath) {
        this.qaPath = qaPath;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
