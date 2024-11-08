package edu.bu.cs673.secondhand.domain;

import java.util.Date;

public class Favorite {
    private Long id;

    private Date createTime;

    private Long userId;

    private Long idleId;

    private IdleItem idleItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIdleId() {
        return idleId;
    }

    public void setIdleId(Long idleId) {
        this.idleId = idleId;
    }

    public IdleItem getIdleItem() {
        return idleItem;
    }

    public void setIdleItem(IdleItem idleItem) {
        this.idleItem = idleItem;
    }
}