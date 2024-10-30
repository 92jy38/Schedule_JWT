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

    // 서비스의 도메인 로직 엔티티로 위임하기 위해 일정 업데이트 메서드 추가
    public void updateSchedule(String title, String content, Set<User> assignedUsers) {
        this.title = title;
        this.content = content;
        updateAssignedUsers(assignedUsers);
    }

    // 담당 유저 업데이트 메서드 추가
    private void updateAssignedUsers(Set<User> newAssignedUsers) {
        // 기존 담당 유저들과의 관계 제거
        for (User user : this.assignedUsers) {
            user.getAssignedSchedules().remove(this);
        }
        this.assignedUsers.clear();

        // 새로운 담당 유저들과의 관계 설정
        if (newAssignedUsers != null) {
            for (User user : newAssignedUsers) {
                assignUser(user);
            }
        }
    }

    // 담당 유저 추가 메서드 추가
    public void assignUser(User user) {
        this.assignedUsers.add(user);
        user.getAssignedSchedules().add(this);
    }

    // 일정 생성 정적 팩토리 메서드 추가
    public static Schedule createSchedule(String title, String content, User creator, Set<User> assignedUsers) {
        Schedule schedule = Schedule.builder()
                .title(title)
                .content(content)
                .creator(creator)
                .build();
        if (assignedUsers != null) {
            for (User user : assignedUsers) {
                schedule.assignUser(user);
            }
        }
        return schedule;
    }
}
