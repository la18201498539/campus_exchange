package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.Message;

import java.util.List;

public interface MessageService {

    /**
     * Send Message
     * @param messageModel
     * @return
     */
    boolean addMessage(Message messageModel);

    /**
     * Delete message
     * @param id
     * @return
     */
    boolean deleteMessage(Long id);

    /**
     * Get a message
     * @param id
     * @return
     */
     Message getMessage(Long id);

    /**
     * Get all messages received by a user
     * @param userId
     * @return
     */
    List<Message> getAllMyMessage(Long userId);

    /**
     * Get all messages for a certain idle
     * @param idleId
     * @return
     */
    List<Message> getAllIdleMessage(Long idleId);

}
