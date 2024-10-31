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
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

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

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 서비스의 도메인 로직 엔티티로 위임하기 위해 유저 정보 업데이트 메서드 추가
    public void update(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // 유저 생성 정적 팩토리 메서드 추가
    public static User create(String username, String email) {
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }
}
