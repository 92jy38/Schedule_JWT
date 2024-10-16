package com.sparta.schedule.jwt.repository;

import com.sparta.schedule.jwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 필요에 따라 추가 메서드 작성 가능
}
