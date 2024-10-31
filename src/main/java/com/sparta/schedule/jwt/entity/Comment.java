package com.sparta.schedule.jwt.entity;

import com.sparta.schedule.jwt.exception.CustomException;
import com.sparta.schedule.jwt.exception.ErrorCode;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Lob
    @Column(nullable = false)
    private String content;

    // 작성자와의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 일정과의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 서비스의 도메인 로직 엔티티로 위임하기 위해 댓글 내용 업데이트 메서드 추가
    public void updateContent(String content) {
        this.content = content;
    }

    // 댓글 작성자 검증 메서드 추가
    public void validateAuthor(Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw new CustomException(ErrorCode.NO_COMMENT_PERMISSION);
        }
    }

    // 댓글 생성 정적 팩토리 메서드 추가
    public static Comment create(String content, User user, Schedule schedule) {
        return Comment.builder()
                .content(content)
                .user(user)
                .schedule(schedule)
                .build();
    }
}
