package edu.bu.cs673.secondhand.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.IdleItemExample;
import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.mapper.UserMapper;
import edu.bu.cs673.secondhand.model.ItemModel;
import edu.bu.cs673.secondhand.service.impl.IdleItemServiceImpl;
import edu.bu.cs673.secondhand.vo.PageVo;

public class ItemServiceTest {

    @Mock
    private IdleItemMapper idleItemMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private IdleItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewItem() {
        IdleItem item = new IdleItem();

        when(idleItemMapper.insert(item)).thenReturn(1);

        assertEquals(true, itemService.addNewItem(item));

        verify(idleItemMapper, times(1)).insert(item);
    }

    @Test
    public void testGetItem() {
        Long id = 1L;
        Long userId = 1L;
        User user = new User();
        IdleItem item = new IdleItem();
        item.setUserId(userId);
        
        when(userMapper.selectByPrimaryKey(userId)).thenReturn(user);
        when(idleItemMapper.selectByPrimaryKey(id)).thenReturn(item);

        ItemModel result = itemService.getItem(id);
        assertEquals(item.getId(), result.getId());
        assertEquals(user, result.getUser());

        verify(idleItemMapper, times(1)).selectByPrimaryKey(id);
        verify(userMapper, times(1)).selectByPrimaryKey(id);
    }

    @Test
    public void testGetAllItemByUser() {
        Long userId = 1L;
        List<IdleItem> items = new ArrayList<>();
        IdleItemExample itemExample = new IdleItemExample();

        when(idleItemMapper.selectByExample(itemExample)).thenReturn(items);

        assertEquals(items, itemService.getAllItemByUser(userId));
    }

    @Test
    public void testUpdateItem() {
        IdleItem item = new IdleItem();
        
        when(idleItemMapper.updateByPrimaryKey(item)).thenReturn(1);
        
        assertEquals(true, itemService.updateItem(item));

        verify(idleItemMapper, times(1)).updateByPrimaryKey(item);
    }

    @Test
    public void testRemoveItem() {
        Long id = 1L;
        
        when(idleItemMapper.deleteByPrimaryKey(id)).thenReturn(1);
        
        assertEquals(true, itemService.removeItem(id));

        verify(idleItemMapper, times(1)).deleteByPrimaryKey(id);
    }

    @Test
    public void testSearchItemLabel() {
        List<IdleItem> items = new ArrayList<>();

        IdleItem item = new IdleItem();
        User user = new User();
        Long userId = 1L;
        user.setId(userId);
        item.setUserId(userId);

        items.add(item);

        when(userMapper.selectByPrimaryKey(userId)).thenReturn(user);
        when(idleItemMapper.selectByExampleWithRowbounds(any(IdleItemExample.class), any(RowBounds.class))).thenReturn(items);

        PageVo<ItemModel> result = itemService.searchItemLabel(0, 1, 8);
        assertEquals(1, result.getCount());
        assertEquals(new ItemModel(item), result.getList().get(0));
        assertEquals(user, result.getList().get(0).getUser());
    }

    @Test
    public void testSearchItem() {
        List<IdleItem> items = new ArrayList<>();

        IdleItem item = new IdleItem();
        User user = new User();
        Long userId = 1L;
        user.setId(userId);
        item.setUserId(userId);

        items.add(item);

        when(userMapper.selectByPrimaryKey(userId)).thenReturn(user);
        when(idleItemMapper.selectByExampleWithRowbounds(any(IdleItemExample.class), any(RowBounds.class))).thenReturn(items);

        PageVo<ItemModel> result = itemService.searchItemLabel(0, 1, 8);
        assertEquals(1, result.getCount());
        assertEquals(new ItemModel(item), result.getList().get(0));
        assertEquals(user, result.getList().get(0).getUser());
    }
}
