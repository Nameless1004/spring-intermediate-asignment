package com.sparta.springintermediateasignment.user.service;

import com.sparta.springintermediateasignment.common.config.PasswordEncoder;
import com.sparta.springintermediateasignment.common.exceptoins.PasswordMissmatchException;
import com.sparta.springintermediateasignment.common.util.JwtUtil;
import com.sparta.springintermediateasignment.user.dto.JwtTokenResponseDto;
import com.sparta.springintermediateasignment.user.dto.LoginRequestDto;
import com.sparta.springintermediateasignment.user.dto.SignupRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    /**
     * 유저 삭제
     *
     * @param id 유저 아이디
     */
    public void deleteUser(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);

        // 유저 삭제 시 유저가 작성한 일정이 있으면 삭제 안됨
        // 유저 삭제 시 담당하고 있는 일정이 있으면 삭제 안됨
        userRepository.delete(user);
    }

    /**
     * 유저 업데이트
     *
     * @param id      유저 아이디
     * @param userDto 수정 정보
     * @return 수정한 유저 정보 DTO
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findByIdOrElseThrow(id);
        user.update(userDto);
        return userDto;
    }

    /**
     * 유저 목록 조회
     *
     * @return 유저 목록
     */
    @Transactional(readOnly = true)
    public List<UserDto> findUsers() {
        return userRepository.findAll()
            .stream()
            .map(UserDto::of)
            .toList();
    }

    /**
     * 유저 id로 조회
     *
     * @param id 유저 아이디
     * @return 조회 유저 정보
     */
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return UserDto.of(userRepository.findByIdOrElseThrow(id));
    }

    /**
     * 회원 가입
     *
     * @param requestDto 회원가입 DTO
     * @return jwt토큰 반환
     */
    public JwtTokenResponseDto join(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 이메일 중복 확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 권한 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        // 사용자 등록 후 jwt 생성 반환
        User user = User.builder()
            .name(requestDto.getUsername())
            .email(requestDto.getEmail())
            .password(password)
            .role(role)
            .build();
        String token = jwtUtil.createToken(user.getEmail(), role);
        userRepository.save(user);

        return new JwtTokenResponseDto(token);
    }

    /**
     * 로그인
     *
     * @param requestDto 로그인 DTO
     * @return jwt 토큰 반환
     */
    @Transactional(readOnly = true)
    public JwtTokenResponseDto login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        // 회원있는지 확인
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new PasswordMissmatchException();
        }

        // 사용자 권한 확인
        UserRole role = user.getRole()
            .equals(UserRole.USER) ? UserRole.USER : UserRole.ADMIN;

        // 사용자 등록 후 jwt 생성 반환
        String token = jwtUtil.createToken(user.getEmail(), role);
        return new JwtTokenResponseDto(token);
    }

}
