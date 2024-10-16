package com.sparta.schedule.jwt.repository;

import com.sparta.schedule.jwt.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 필요에 따라 추가 메서드 작성 가능
}
