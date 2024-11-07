package edu.bu.cs673.secondhand.domain;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Long id;

    private Long userId;

    private Long idleId;

    private String content;

    private Date createTime;

    private Long toUser;

    private Long toMessage;

    private IdleItem idle;

    private User fromU;

    private User toU;

    private Message toM;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public Long getToMessage() {
        return toMessage;
    }

    public void setToMessage(Long toMessage) {
        this.toMessage = toMessage;
    }

    public User getFromU() {
        return fromU;
    }

    public void setFromU(User fromU) {
        this.fromU = fromU;
    }

    public IdleItem getIdle() {
        return idle;
    }

    public void setIdle(IdleItem idle) {
        this.idle = idle;
    }

    public User getToU() {
        return toU;
    }

    public void setToU(User toU) {
        this.toU = toU;
    }

    public Message getToM() {
        return toM;
    }

    public void setToM(Message toM) {
        this.toM = toM;
    }
}