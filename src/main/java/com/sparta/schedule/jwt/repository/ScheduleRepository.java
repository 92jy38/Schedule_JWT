package com.sparta.schedule.jwt.repository;

import com.sparta.schedule.jwt.dto.SchedulePageDto;
import com.sparta.schedule.jwt.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT new com.sparta.schedule.jwt.dto.SchedulePageDto(s.id, s.title, s.content, s.creator.username, COUNT(c), s.createdAt, s.updatedAt) " +
            "FROM Schedule s LEFT JOIN s.comments c " +
            "GROUP BY s.id, s.title, s.content, s.creator.username, s.createdAt, s.updatedAt " +
            "ORDER BY s.updatedAt DESC")
    Page<SchedulePageDto> findAllWithCommentCount(Pageable pageable);
}
