package com.sparta.schedule.jwt.dto.user;

import lombok.Getter;

import jakarta.validation.constraints.*;

@Getter
public class UserRequestDto {

    @NotBlank(message = "유저명은 필수입니다.")
    @Size(max = 50, message = "유저명은 50자 이내여야 합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @Size(max = 100, message = "이메일은 100자 이내여야 합니다.")
    private String email;
}
