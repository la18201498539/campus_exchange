package edu.bu.cs673.secondhand.model;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.User;

/**
 * Class for returning item with user information
 */
public class ItemModel extends IdleItem{
    private User user;

    public ItemModel(IdleItem item) {
        super();
        this.setId(item.getId());
        this.setIdleName(item.getIdleName());
        this.setIdleDetails(item.getIdleDetails());;
        this.setPictureList(item.getPictureList());
        this.setIdlePrice(item.getIdlePrice());
        this.setIdlePlace(item.getIdlePlace());
        this.setIdleLabel(item.getIdleLabel());
        this.setReleaseTime(item.getReleaseTime());
        this.setIdleStatus(item.getIdleStatus());
        this.setUserId(item.getUserId());
        user = null;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
