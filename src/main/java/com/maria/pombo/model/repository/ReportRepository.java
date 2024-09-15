package com.maria.pombo.model.repository;

import com.maria.pombo.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Report findByMessageIdAndUserId(String messageId, String userId);
}
