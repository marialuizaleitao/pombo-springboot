package com.maria.pombo.model.dto;

import lombok.Data;

@Data
public class MessageDto {
    private String id;
    private String text;
    private Integer likeCount;
    private Integer authorId;
    private String authorName;
    private Integer reportCount;
}

