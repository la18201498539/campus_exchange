package edu.bu.cs673.secondhand.service;
import edu.bu.cs673.secondhand.domain.Message;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.mapper.MessageMapper;
import edu.bu.cs673.secondhand.mapper.UserMapper;
import edu.bu.cs673.secondhand.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {

    @Mock
    private MessageMapper messageDao;

    @Mock
    private UserMapper userDao;

    @Mock
    private IdleItemMapper idleItemDao;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMessage() {
        Message message = new Message();
        when(messageDao.insert(message)).thenReturn(1);

        boolean result = messageService.addMessage(message);

        assertTrue(result);
        verify(messageDao, times(1)).insert(message);
    }

    @Test
    void testDeleteMessage() {
        Long messageId = 1L;
        when(messageDao.deleteByPrimaryKey(messageId)).thenReturn(1);

        boolean result = messageService.deleteMessage(messageId);

        assertTrue(result);
        verify(messageDao, times(1)).deleteByPrimaryKey(messageId);
    }

    @Test
    void testGetMessage() {
        Long messageId = 1L;
        Message message = new Message();
        when(messageDao.selectByPrimaryKey(messageId)).thenReturn(message);

        Message result = messageService.getMessage(messageId);

        assertNotNull(result);
        assertEquals(message, result);
        verify(messageDao, times(1)).selectByPrimaryKey(messageId);
    }

    @Test
    void testGetAllMyMessage() {
        Long userId = 1L;
        List<Message> messageList = new ArrayList<>();
        Message message = new Message();
        messageList.add(message);

        when(messageDao.getMyMessage(userId)).thenReturn(messageList);
        when(userDao.findUserByList(anyList())).thenReturn(new ArrayList<>());
        when(idleItemDao.findIdleByList(anyList())).thenReturn(new ArrayList<>());

        List<Message> result = messageService.getAllMyMessage(userId);

        assertNotNull(result);
        assertEquals(messageList, result);
        verify(messageDao, times(1)).getMyMessage(userId);
    }

    @Test
    void testGetAllIdleMessage() {
        Long idleId = 1L;
        List<Message> messageList = new ArrayList<>();
        Message message = new Message();
        messageList.add(message);

        when(messageDao.getIdleMessage(idleId)).thenReturn(messageList);
        when(userDao.findUserByList(anyList())).thenReturn(new ArrayList<>());

        List<Message> result = messageService.getAllIdleMessage(idleId);

        assertNotNull(result);
        assertEquals(messageList, result);
        verify(messageDao, times(1)).getIdleMessage(idleId);
    }

}
