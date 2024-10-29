package com.sparta.schedule.jwt.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 유저 관련 에러 코드
    USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USERNAME_DUPLICATED("USERNAME_DUPLICATED", "이미 사용 중인 유저명입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATED("EMAIL_DUPLICATED", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),

    // 일정 관련 에러 코드
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND", "일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 댓글 관련 에러 코드
    COMMENT_NOT_FOUND("COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NO_COMMENT_PERMISSION("NO_COMMENT_PERMISSION", "댓글 수정/삭제 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 기타 에러 코드
    INVALID_REQUEST("INVALID_REQUEST", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
