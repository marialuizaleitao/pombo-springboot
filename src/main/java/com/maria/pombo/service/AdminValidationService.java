package com.maria.pombo.service;

import com.maria.pombo.exception.UnauthorizedException;
import com.maria.pombo.model.entity.User;
import com.maria.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateAdmin(String userId) throws UnauthorizedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (!user.isAdmin()) {
            throw new UnauthorizedException("User is not an admin");
        }
    }
}
