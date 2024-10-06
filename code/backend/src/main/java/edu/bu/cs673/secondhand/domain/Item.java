package edu.bu.cs673.secondhand.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
    private Long id;

    private String idleName;

    private String idleDetails;

    private String pictureList;

    private BigDecimal idlePrice;

    private String idlePlace;

    private Integer idleLabel;

    private Date releaseTime;

    private Byte idleStatus;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdleName() {
        return idleName;
    }

    public void setIdleName(String idleName) {
        this.idleName = idleName == null ? null : idleName.trim();
    }

    public String getIdleDetails() {
        return idleDetails;
    }

    public void setIdleDetails(String idleDetails) {
        this.idleDetails = idleDetails == null ? null : idleDetails.trim();
    }

    public String getPictureList() {
        return pictureList;
    }

    public void setPictureList(String pictureList) {
        this.pictureList = pictureList == null ? null : pictureList.trim();
    }

    public BigDecimal getIdlePrice() {
        return idlePrice;
    }

    public void setIdlePrice(BigDecimal idlePrice) {
        this.idlePrice = idlePrice;
    }

    public String getIdlePlace() {
        return idlePlace;
    }

    public void setIdlePlace(String idlePlace) {
        this.idlePlace = idlePlace == null ? null : idlePlace.trim();
    }

    public Integer getIdleLabel() {
        return idleLabel;
    }

    public void setIdleLabel(Integer idleLabel) {
        this.idleLabel = idleLabel;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Byte getIdleStatus() {
        return idleStatus;
    }

    public void setIdleStatus(Byte idleStatus) {
        this.idleStatus = idleStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}