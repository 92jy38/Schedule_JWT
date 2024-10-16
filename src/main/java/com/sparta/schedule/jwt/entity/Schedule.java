package com.sparta.schedule.jwt.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Lob
    @Column(nullable = false)
    private String content;

    // 작성자와의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    // 담당 유저들과의 관계 설정 (N:M)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "schedule_user_assignments",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> assignedUsers = new HashSet<>();

    // 댓글들과의 관계 설정 (1:N)
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 작성일

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정일

    // 생성 시각 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // 수정 시각 설정
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 제목 업데이트 메서드
    public void updateTitle(String title) {
        this.title = title;
    }

    // 내용 업데이트 메서드
    public void updateContent(String content) {
        this.content = content;
    }

    // 담당 유저 추가 메서드
    public void addAssignedUser(User user) {
        this.assignedUsers.add(user);
        user.getAssignedSchedules().add(this);
    }

    // 담당 유저 제거 메서드
    public void removeAssignedUser(User user) {
        this.assignedUsers.remove(user);
        user.getAssignedSchedules().remove(this);
    }
}
