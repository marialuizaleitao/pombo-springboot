package com.maria.pombo.service;

import com.maria.pombo.exception.MessageNotFoundException;
import com.maria.pombo.exception.UnauthorizedException;
import com.maria.pombo.exception.UserNotFoundException;
import com.maria.pombo.model.entity.Like;
import com.maria.pombo.model.entity.Message;
import com.maria.pombo.model.entity.Report;
import com.maria.pombo.model.entity.User;
import com.maria.pombo.model.repository.LikeRepository;
import com.maria.pombo.model.repository.MessageRepository;
import com.maria.pombo.model.repository.ReportRepository;
import com.maria.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AdminValidationService adminValidationService;

    public Message saveMessage(String userId, Message message) {
        User author = userRepository.findById(userId).orElse(null);
        if (author == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        } else {
            message.setUser(author);
        }
        return messageRepository.save(message);
    }

    public Message getMessage(String messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public List<Message> listMessages() {
        return messageRepository.findAll();
    }

    public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
    }

    public String blockMessage(String userId, String messageId) throws UnauthorizedException {
        // Verifica se o usuário é admin
        adminValidationService.validateAdmin(userId);

        // Recupera a mensagem pelo ID
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));

        // Seta o atributo isBlocked como true
        message.setBlocked(true);
        messageRepository.save(message);

        return "Message blocked successfully";
    }
}
