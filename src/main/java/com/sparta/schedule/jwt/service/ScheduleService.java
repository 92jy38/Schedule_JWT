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
        User creator = userRepository.findById(requestDto.getCreatorId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Set<User> assignedUsers = getAssignedUsers(requestDto.getAssignedUserIds());

        Schedule schedule = Schedule.create(requestDto.getTitle(), requestDto.getContent(), creator, assignedUsers);

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

        Set<User> assignedUsers = getAssignedUsers(requestDto.getAssignedUserIds());

        // 엔티티의 업데이트 메서드 호출
        schedule.update(requestDto.getTitle(), requestDto.getContent(), assignedUsers);

        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        scheduleRepository.delete(schedule);
    }

    private Set<User> getAssignedUsers(Set<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Set.of();
        }
        return new HashSet<>(userRepository.findAllById(userIds));
    }
}
