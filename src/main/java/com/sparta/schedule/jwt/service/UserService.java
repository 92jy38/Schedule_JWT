package com.sparta.schedule.jwt.service;

import com.sparta.schedule.jwt.dto.user.UserRequestDto;
import com.sparta.schedule.jwt.dto.user.UserResponseDto;
import com.sparta.schedule.jwt.entity.User;
import com.sparta.schedule.jwt.exception.BadRequestException;
import com.sparta.schedule.jwt.exception.NotFoundException;
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
            throw new BadRequestException("이미 사용 중인 유저명입니다.");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("이미 사용 중인 이메일입니다.");
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
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    // 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // 유저명 및 이메일 중복 검사
        if (!user.getUsername().equals(requestDto.getUsername()) && userRepository.existsByUsername(requestDto.getUsername())) {
            throw new BadRequestException("이미 사용 중인 유저명입니다.");
        }
        if (!user.getEmail().equals(requestDto.getEmail()) && userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("이미 사용 중인 이메일입니다.");
        }

        user.updateUsername(requestDto.getUsername());
        user.updateEmail(requestDto.getEmail());

        return new UserResponseDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("유저를 찾을 수 없습니다.");
        }
        userRepository.deleteById(id);
    }
}
