package com.sparta.schedule.jwt.dto;

import lombok.Getter;

import jakarta.validation.constraints.*;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "일정 ID는 필수입니다.")
    private Long scheduleId;
}
