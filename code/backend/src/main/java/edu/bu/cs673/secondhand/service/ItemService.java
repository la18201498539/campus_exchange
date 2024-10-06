package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.Item;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:36
 *****/
public interface ItemService {

    /**
     * Get Item List by UserId
     * @param userId
     * @return
     */
    List<Item> getItemListByUserId(Long userId);
}
