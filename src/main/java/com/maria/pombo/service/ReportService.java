package com.maria.pombo.service;

import com.maria.pombo.exception.MessageNotFoundException;
import com.maria.pombo.exception.UserNotFoundException;
import com.maria.pombo.model.dto.ReportDto;
import com.maria.pombo.model.entity.Message;
import com.maria.pombo.model.entity.Report;
import com.maria.pombo.model.entity.User;
import com.maria.pombo.model.repository.MessageRepository;
import com.maria.pombo.model.repository.ReportRepository;
import com.maria.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Create a report for a message by a user.
     *
     * @param userId    the ID of the user reporting the message
     * @param messageId the ID of the message being reported
     * @return a ReportDto with details of the report
     * @throws UserNotFoundException    if the user does not exist
     * @throws MessageNotFoundException if the message does not exist
     */
    public ReportDto createReport(String userId, String messageId) throws UserNotFoundException, MessageNotFoundException {
        // Fetch user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Fetch message by ID
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));

        // Create a new report
        Report report = new Report();
        report.setUser(user);
        report.setMessage(message);

        // Save the report
        report = reportRepository.save(report);

        // Update the total report count in the message
        message.setTotalReports(message.getTotalReports() + 1);
        messageRepository.save(message);

        // Return a ReportDto with the relevant information
        return new ReportDto(report.getMessageId(), message.getTotalReports(), report.getUserId());
    }

    /**
     * Get all reports for a specific message.
     *
     * @param messageId the ID of the message
     * @return a list of ReportDto with the reports for the message
     * @throws MessageNotFoundException if the message does not exist
     */
    public List<ReportDto> getReportsByMessage(String messageId) throws MessageNotFoundException {
        // Fetch message by ID
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found with id: " + messageId));

        // Fetch all reports associated with the message
        List<Report> reports = reportRepository.findByMessage(message);

        // Convert the list of reports to a list of ReportDto and return
        return reports.stream()
                .map(report -> new ReportDto(report.getMessageId(), message.getTotalReports(), report.getUserId()))
                .collect(Collectors.toList());
    }
}
