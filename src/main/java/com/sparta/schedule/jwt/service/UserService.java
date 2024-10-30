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
        validateUsernameAndEmail(requestDto.getUsername(), requestDto.getEmail());

        User user = User.createUser(requestDto.getUsername(), requestDto.getEmail());

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
        validateUsernameAndEmailForUpdate(user, requestDto.getUsername(), requestDto.getEmail());

        // 엔티티의 업데이트 메서드 호출
        user.updateUser(requestDto.getUsername(), requestDto.getEmail());

        return new UserResponseDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED);
        }
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    private void validateUsernameAndEmailForUpdate(User user, String newUsername, String newEmail) {
        if (!user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED);
        }
        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
    }
}
