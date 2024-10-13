package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.Message;
import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.mapper.MessageMapper;
import edu.bu.cs673.secondhand.mapper.UserMapper;
import edu.bu.cs673.secondhand.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageDao;

    @Autowired
    private UserMapper userDao;

    @Autowired
    private IdleItemMapper idleItemDao;

    /**
     * Add a message
     * @param messageModel
     * @return
     */
    public boolean addMessage(Message messageModel){
        return messageDao.insert(messageModel)==1;
    }

    /**
     * Delete a message without user authentication
     * @param id
     * @return
     */
    public boolean deleteMessage(Long id){
        return messageDao.deleteByPrimaryKey(id)==1;
    }

    /**
     * Get a message
     * @param id
     * @return
     */
    public Message getMessage(Long id){
        return messageDao.selectByPrimaryKey(id);
    }

    /**
     * Get all messages received by a user, without paging query
     * user information and idle information at the same time
     * userId (Create index)
     * @param userId
     * @return
     */
    public List<Message> getAllMyMessage(Long userId){
        List<Message> list=messageDao.getMyMessage(userId);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(Message i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userDao.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(Message i:list){
                i.setFromU(map.get(i.getUserId()));
            }

            List<Long> idleIdList=new ArrayList<>();
            for(Message i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItem> idleList=idleItemDao.findIdleByList(idleIdList);
            Map<Long,IdleItem> idleMap=new HashMap<>();
            for(IdleItem idle:idleList){
                idleMap.put(idle.getId(),idle);
            }
            for(Message i:list){
                i.setIdle(idleMap.get(i.getIdleId()));
            }
        }
        return list;
    }

    /**
     * Query all messages in an idle state without paging
     * Find out the sender and receiver information at the same time
     * idleId (Create index)
     * @param idleId
     * @return
     */
    public List<Message> getAllIdleMessage(Long idleId){
        List<Message> list=messageDao.getIdleMessage(idleId);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(Message i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userDao.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(Message i:list){
                i.setFromU(map.get(i.getUserId()));
            }
            Map<Long,Message> mesMap=new HashMap<>();
            for(Message i:list){
                mesMap.put(i.getId(),i);
            }
            for(Message i:list){
                Message toM=new Message();
                User toU=new User();
                if(i.getToMessage()!=null){
                    toM.setContent(mesMap.get(i.getToMessage()).getContent());
                    toU.setNickname(map.get(i.getToUser()).getNickname());
                }
                i.setToM(toM);
                i.setToU(toU);
            }
        }
        return list;
    }

}
