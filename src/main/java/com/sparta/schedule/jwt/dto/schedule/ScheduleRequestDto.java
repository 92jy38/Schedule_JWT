package com.sparta.schedule.jwt.dto.schedule;

import lombok.Getter;

import jakarta.validation.constraints.*;
import java.util.Set;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long creatorId;

    // 담당 유저 ID 목록
    private Set<Long> assignedUserIds;
}
