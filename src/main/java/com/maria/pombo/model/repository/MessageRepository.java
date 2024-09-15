package com.maria.pombo.model.repository;

import com.maria.pombo.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    Message findByUserId(String userId);
}
