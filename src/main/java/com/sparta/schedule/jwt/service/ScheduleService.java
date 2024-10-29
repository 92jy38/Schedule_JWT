package com.sparta.schedule.jwt.service;

import com.sparta.schedule.jwt.dto.page.SchedulePageDto;
import com.sparta.schedule.jwt.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.jwt.dto.schedule.ScheduleResponseDto;
import com.sparta.schedule.jwt.entity.Schedule;
import com.sparta.schedule.jwt.entity.User;
import com.sparta.schedule.jwt.exception.CustomException;
import com.sparta.schedule.jwt.exception.ErrorCode;
import com.sparta.schedule.jwt.repository.ScheduleRepository;
import com.sparta.schedule.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // 작성자 유저 조회
        User creator = userRepository.findById(requestDto.getCreatorId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 담당 유저들 조회
        Set<User> assignedUsers = new HashSet<>();
        if (requestDto.getAssignedUserIds() != null) {
            for (Long userId : requestDto.getAssignedUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                assignedUsers.add(user);
            }
        }

        Schedule schedule = Schedule.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .creator(creator)
                .assignedUsers(assignedUsers)
                .build();

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    // 일정 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        return new ScheduleResponseDto(schedule);
    }

    // 일정 페이징 조회
    @Transactional(readOnly = true)
    public Page<SchedulePageDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return scheduleRepository.findAllWithCommentCount(pageable);
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 작성자 유저 조회
        User creator = userRepository.findById(requestDto.getCreatorId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 담당 유저들 조회
        Set<User> assignedUsers = new HashSet<>();
        if (requestDto.getAssignedUserIds() != null) {
            for (Long userId : requestDto.getAssignedUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                assignedUsers.add(user);
            }
        }

        schedule.updateTitle(requestDto.getTitle());
        schedule.updateContent(requestDto.getContent());
        schedule.getAssignedUsers().clear();
        schedule.getAssignedUsers().addAll(assignedUsers);

        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
        scheduleRepository.deleteById(id);
    }
}
