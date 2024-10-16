package com.sparta.schedule.jwt.dto;

import com.sparta.schedule.jwt.entity.Schedule;
import com.sparta.schedule.jwt.entity.User; // User 클래스 import
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String content;
    private String creatorUsername;
    private Set<String> assignedUsernames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.creatorUsername = schedule.getCreator().getUsername();
        this.assignedUsernames = schedule.getAssignedUsers().stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
