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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "유저명은 필수입니다.")
    @Size(max = 50, message = "유저명은 50자 이내여야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @Size(max = 100, message = "이메일은 100자 이내여야 합니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 작성일

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정일

    // 작성한 일정들 (1:N 관계)
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Schedule> createdSchedules = new HashSet<>();

    // 담당한 일정들 (N:M 관계)
    @ManyToMany(mappedBy = "assignedUsers")
    @Builder.Default
    private Set<Schedule> assignedSchedules = new HashSet<>();

    // 작성한 댓글들 (1:N 관계)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

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

    // 유저명 업데이트 메서드
    public void updateUsername(String username) {
        this.username = username;
    }

    // 이메일 업데이트 메서드
    public void updateEmail(String email) {
        this.email = email;
    }

    // 생성 메서드 (빌더 대신 사용 가능)
    public static User createUser(String username, String email) {
        User user = new User();
        user.username = username;
        user.email = email;
        return user;
    }
}
