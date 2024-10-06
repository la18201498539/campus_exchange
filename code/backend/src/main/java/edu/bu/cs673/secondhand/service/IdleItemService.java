package edu.bu.cs673.secondhand.service;

import java.util.List;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.serviceInterface.IdleItemServiceInterface;

/**
 * A class for service provide by idle item
 */
public class IdleItemService implements IdleItemServiceInterface{

    private IdleItemMapper itemMapper;

    @Override
    public boolean addNewItem(IdleItem item) {
        return itemMapper.insert(item) == 1;
    }

    @Override
    public IdleItem getItem(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IdleItem> getAllItemByUser(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllItemByUser'");
    }

    @Override
    public boolean updateItem(IdleItem item) {
        return itemMapper.updateByPrimaryKey(item) == 1;
    }
    
}
