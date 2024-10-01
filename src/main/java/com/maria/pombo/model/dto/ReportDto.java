package com.maria.pombo.model.dto;

import lombok.Data;

@Data
public class ReportDto {
    private String messageId;
    private Integer reportCount;
    private String userId;

    public ReportDto(String messageId, int totalReports, String userId) {
    }
}
