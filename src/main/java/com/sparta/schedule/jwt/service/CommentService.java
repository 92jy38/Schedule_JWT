package com.sparta.schedule.jwt.service;

import com.sparta.schedule.jwt.dto.comment.CommentRequestDto;
import com.sparta.schedule.jwt.dto.comment.CommentResponseDto;
import com.sparta.schedule.jwt.entity.Comment;
import com.sparta.schedule.jwt.entity.Schedule;
import com.sparta.schedule.jwt.entity.User;
import com.sparta.schedule.jwt.exception.CustomException;
import com.sparta.schedule.jwt.exception.ErrorCode;
import com.sparta.schedule.jwt.repository.CommentRepository;
import com.sparta.schedule.jwt.repository.ScheduleRepository;
import com.sparta.schedule.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 일정 조회
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .user(user)
                .schedule(schedule)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 작성자 본인인지 확인
        if (!comment.getUser().getId().equals(requestDto.getUserId())) {
            throw new CustomException(ErrorCode.NO_COMMENT_PERMISSION);
        }

        comment.updateContent(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 작성자 본인인지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.NO_COMMENT_PERMISSION);
        }

        commentRepository.deleteById(id);
    }
}
