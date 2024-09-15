package com.maria.pombo.service;

import com.maria.pombo.exception.MessageNotFoundException;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReportRepository reportRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, User user) {
        User userToUpdate = userRepository.findById(userId).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    public void deleteUser(String userId) {
        Message message = messageRepository.findByUserId(userId);
        if (message != null) {
            messageRepository.delete(message);
        }
        userRepository.deleteById(userId);
    }

    public void toggleAdmin(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setAdmin(!user.isAdmin());
            userRepository.save(user);
        }
    }

    public void toggleLike(String userId, String messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Verifica se o like já existe
        Like existingLike = likeRepository.findByMessageIdAndUserId(messageId, userId);

        if (existingLike != null) {
            // Se o like já existe, remove o like e atualiza o total de likes
            likeRepository.delete(existingLike);
            message.setTotalLikes(message.getTotalLikes() - 1);
        } else {
            // Se o like não existe, adiciona um novo like e atualiza o total de likes
            Like newLike = new Like();
            newLike.setMessage(message);
            newLike.setUser(user);
            likeRepository.save(newLike);
            message.setTotalLikes(message.getTotalLikes() + 1);
        }

        // Atualiza a mensagem com o novo total de likes
        messageRepository.save(message);
    }

    public String reportMessage(String userId, String messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Verifica se o usuário já reportou a mensagem
        Report existingReport = reportRepository.findByMessageIdAndUserId(messageId, userId);

        if (existingReport != null) {
            // Se o usuário já reportou a mensagem, retorna uma mensagem de erro
            return "You have already reported this message";
        } else {
            // Se o usuário não reportou a mensagem, adiciona um novo report e atualiza o total de reports
            Report newReport = new Report();
            newReport.setMessage(message);
            newReport.setUser(user);
            reportRepository.save(newReport);
            message.setTotalReports(message.getTotalReports() + 1);
            messageRepository.save(message);
            return "Message reported successfully";
        }
    }
}
