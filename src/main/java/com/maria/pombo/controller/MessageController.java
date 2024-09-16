package com.maria.pombo.controller;

import com.maria.pombo.exception.MessageNotFoundException;
import com.maria.pombo.exception.UnauthorizedException;
import com.maria.pombo.exception.UserNotFoundException;
import com.maria.pombo.model.entity.Message;
import com.maria.pombo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> saveMessage(@PathVariable String userId, @RequestBody Message message) {
        try {
            Message savedMessage = messageService.saveMessage(userId, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the message.");
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable String messageId) {
        try {
            Message message = messageService.getMessage(messageId);
            if (message == null) {
                throw new MessageNotFoundException("Message not found with id: " + messageId);
            }
            return ResponseEntity.ok(message);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the message.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        try {
            List<Message> messages = messageService.listMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable String messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.noContent().build();
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the message.");
        }
    }

    @PutMapping("/block/{messageId}")
    public ResponseEntity<?> blockMessage(
            @RequestHeader("userId") String userId,
            @PathVariable String messageId) {
        try {
            String response = messageService.blockMessage(userId, messageId);
            return ResponseEntity.ok(response);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while blocking the message.");
        }
    }
}
