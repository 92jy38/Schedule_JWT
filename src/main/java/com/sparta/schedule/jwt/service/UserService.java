package com.sparta.schedule.jwt.service;

import com.sparta.schedule.jwt.dto.user.UserRequestDto;
import com.sparta.schedule.jwt.dto.user.UserResponseDto;
import com.sparta.schedule.jwt.entity.User;
import com.sparta.schedule.jwt.exception.CustomException;
import com.sparta.schedule.jwt.exception.ErrorCode;
import com.sparta.schedule.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        // 유저명 및 이메일 중복 검사
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED);
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .build();

        userRepository.save(user);

        return new UserResponseDto(user);
    }

    // 유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserResponseDto(user);
    }

    // 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 유저명 및 이메일 중복 검사
        if (!user.getUsername().equals(requestDto.getUsername()) && userRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED);
        }
        if (!user.getEmail().equals(requestDto.getEmail()) && userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }

        user.updateUsername(requestDto.getUsername());
        user.updateEmail(requestDto.getEmail());

        return new UserResponseDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
