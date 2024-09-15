package com.maria.pombo.model.repository;

import com.maria.pombo.model.entity.Like;
import com.maria.pombo.model.entity.Message;
import com.maria.pombo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Like findByMessageAndUser(Message message, User user);
}
