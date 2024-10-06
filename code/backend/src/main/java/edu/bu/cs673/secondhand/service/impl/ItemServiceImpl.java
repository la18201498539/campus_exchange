package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.Item;
import edu.bu.cs673.secondhand.domain.ItemExample;
import edu.bu.cs673.secondhand.mapper.ItemMapper;
import edu.bu.cs673.secondhand.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:39
 *****/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> getItemListByUserId(Long userId) {
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return itemMapper.selectByExample(example);
    }
}
