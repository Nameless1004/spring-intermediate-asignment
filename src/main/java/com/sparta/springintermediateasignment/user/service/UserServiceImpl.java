package com.sparta.springintermediateasignment.user.service;

import com.sparta.springintermediateasignment.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.dto.ManagerAddRequestDto;
import com.sparta.springintermediateasignment.user.dto.UserDto;
import com.sparta.springintermediateasignment.user.entity.ScheduleManager;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.ScheduleManagerRepository;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleManagerRepository scheduleManagerRepository;

    @Override
    @Transactional(readOnly = false)
    public Long save(UserDto userDto) {

        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        User user = User.of(userDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        User user = getUser(id);

        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = false)
    public UserDto update(Long id, UserDto userDto) {
        User user = getUser(id);

        // 수정
        userRepository.save(user);
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserDto::of)
            .toList();
    }

    @Override
    public UserDto findById(Long id) {
        return UserDto.of(getUser(id));
    }

    @Override
    @Transactional(readOnly = false)
    public void addManager(ManagerAddRequestDto userDto) {
        User user = getUser(userDto.getManagerId());
        Schedule schedule = scheduleRepository.findById(userDto.getScheduleId())
            .orElseThrow(() -> new InvalidIdException("일정"));

        ScheduleManager scheduleManager = ScheduleManager.builder()
            .user(user)
            .schedule(schedule)
            .build();

        scheduleManagerRepository.save(scheduleManager);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteManager(ManagerAddRequestDto userDto) {
        ScheduleManager del = scheduleManagerRepository.findByUserId(userDto.getManagerId())
            .orElseThrow(() -> new InvalidIdException("일정 담당자"));
        scheduleManagerRepository.delete(del);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
