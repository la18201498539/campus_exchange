package edu.bu.cs673.secondhand.service.impl;

import java.util.List;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.IdleItemExample;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.mapper.UserMapper;
import edu.bu.cs673.secondhand.model.ItemModel;
import edu.bu.cs673.secondhand.service.IdleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A class for service provide by idle item
 */
@Service
public class IdleItemServiceImpl implements IdleItemService {

    @Autowired
    private IdleItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addNewItem(IdleItem item) {
        return itemMapper.insert(item) == 1;
    }

    @Override
    public ItemModel getItem(Long id) {
        ItemModel item = new ItemModel(itemMapper.selectByPrimaryKey(id));
        item.setUser(userMapper.selectByPrimaryKey(item.getUserId()));
        return item;
    }

    @Override
    public List<IdleItem> getAllItemByUser(Long userId) {
        IdleItemExample example = new IdleItemExample();
        IdleItemExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return itemMapper.selectByExample(example);
    }

    @Override
    public boolean updateItem(IdleItem item) {
        return itemMapper.updateByPrimaryKey(item) == 1;
    }

    @Override
    public boolean removeItem(Long id) {
        return itemMapper.deleteByPrimaryKey(id) == 1;
    }
    
}
