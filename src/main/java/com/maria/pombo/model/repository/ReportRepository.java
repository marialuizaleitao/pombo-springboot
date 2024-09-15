package com.maria.pombo.model.repository;

import com.maria.pombo.model.entity.Message;
import com.maria.pombo.model.entity.Report;
import com.maria.pombo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Report findByMessageAndUser(Message message, User user);
}
