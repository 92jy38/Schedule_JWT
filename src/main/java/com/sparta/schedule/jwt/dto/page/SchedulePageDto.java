package com.sparta.schedule.jwt.dto.page;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePageDto {
    private Long id;
    private String title;
    private String content;
    private String creatorUsername;
    private Long commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
