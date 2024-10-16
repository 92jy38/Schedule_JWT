package com.sparta.schedule.jwt.service;

import com.sparta.schedule.jwt.dto.CommentRequestDto;
import com.sparta.schedule.jwt.dto.CommentResponseDto;
import com.sparta.schedule.jwt.entity.Comment;
import com.sparta.schedule.jwt.entity.Schedule;
import com.sparta.schedule.jwt.entity.User;
import com.sparta.schedule.jwt.exception.BadRequestException;
import com.sparta.schedule.jwt.exception.NotFoundException;
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
        // 작성자 유저 조회
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("작성자 유저를 찾을 수 없습니다."));

        // 일정 조회
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
                .orElseThrow(() -> new NotFoundException("일정을 찾을 수 없습니다."));

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
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 작성자 본인인지 확인
        if (!comment.getUser().getId().equals(requestDto.getUserId())) {
            throw new BadRequestException("댓글 수정 권한이 없습니다.");
        }

        comment.setContent(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 작성자 본인인지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new BadRequestException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(id);
    }
}
