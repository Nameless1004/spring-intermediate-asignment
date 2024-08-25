package com.sparta.springintermediateasignment.user.service;

import com.sparta.springintermediateasignment.common.config.PasswordEncoder;
import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.common.exceptoins.PasswordMissmatchException;
import com.sparta.springintermediateasignment.common.util.JwtUtil;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.dto.AddScheduleManagerDto;
import com.sparta.springintermediateasignment.user.dto.JwtTokenResponseDto;
import com.sparta.springintermediateasignment.user.dto.LoginRequestDto;
import com.sparta.springintermediateasignment.user.dto.SignupRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import com.sparta.springintermediateasignment.user.repository.ScheduleUserRepository;
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
    private final ScheduleRepository scheduleRepository;
    private final ScheduleUserRepository scheduleUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUser(id);

        // 수정
        userRepository.save(user);
        return userDto;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findUsers() {
        return userRepository.findAll()
            .stream()
            .map(UserDto::of)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return UserDto.of(getUser(id));
    }

    /**
     * 일정에 담당자 추가
     *
     * @param requestDto
     */
    public void addManager(AddScheduleManagerDto requestDto) {
        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
            .orElseThrow(
                () -> new InvalidIdException("일정 레포지토리", "일정", requestDto.getScheduleId()));

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long authorUserId = requestDto.getAuthorUserId();
        if (!schedule.getAuthor().getId()
            .equals(authorUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        // 해당 스케쥴 담당자 중복 체크
        Optional<ScheduleUser> find = scheduleUserRepository.findByUserIdAndScheduleId(
            requestDto.getUserId(), requestDto.getScheduleId());
        if (find.isPresent()) {
            throw new IllegalArgumentException(
                "이미 등록된 담당자입니다. 스케쥴id: " + requestDto.getScheduleId() + " / 담당자 id: "
                    + requestDto.getUserId());
        }

        User user = getUser(requestDto.getUserId());

        ScheduleUser su = ScheduleUser.createScheduleManager(schedule, user);
        scheduleUserRepository.save(su);
    }

    /**
     * 담당 매니저 삭제
     * @param requestDto
     */
    public void deleteManager(AddScheduleManagerDto requestDto) {
        ScheduleUser scheduleUser = scheduleUserRepository.findByUserIdAndScheduleId(requestDto.getUserId(), requestDto.getScheduleId())
            .orElseThrow(
                () -> new IllegalArgumentException("해당 일정에" +"[ "+ requestDto.getScheduleId() +" ]" + "해당하는 해당 유저"+ "[ "+ requestDto.getUserId() +" ]" + "가 없습니다."));

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long authorUserId = requestDto.getAuthorUserId();
        Schedule schedule = scheduleUser.getSchedule();
        if (!schedule.getAuthor().getId()
            .equals(authorUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        scheduleUser.removeScheduleUser();
        scheduleUserRepository.delete(scheduleUser);
    }

    /**
     * 회원 가입
     *
     * @param requestDto
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

    /**
     * 유저 유효성 검사
     *
     * @param id
     * @return 해당 아이디의 유저가 존재하면 엔티티 반환
     */
    private User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }

}
