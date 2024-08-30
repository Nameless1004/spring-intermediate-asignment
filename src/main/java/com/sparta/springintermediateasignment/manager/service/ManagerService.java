package com.sparta.springintermediateasignment.manager.service;

import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.manager.repository.ScheduleUserRepository;
import com.sparta.springintermediateasignment.schedule.dto.AddScheduleManagerDto;
import com.sparta.springintermediateasignment.schedule.dto.RemoveSchedueManagerDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerService {

    private final ScheduleUserRepository scheduleUserRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정에 담당자 추가
     *
     * @param requestDto 일정 작성자 ID, 일정 ID, 담당할 유저 ID
     */
    public void registManager(AddScheduleManagerDto requestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(requestDto.getScheduleId());

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long authorUserId = requestDto.getAuthorUserId();
        Long scheduleAuthorUserId = schedule.getAuthor()
            .getId();
        if (!scheduleAuthorUserId.equals(authorUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        Long managerId = requestDto.getManagerId();

        if (scheduleUserRepository.existsByScheduleIdAndUserId(requestDto.getScheduleId(),
            managerId)) {
            throw new IllegalArgumentException("이미 등록된 담당자입니다.");
        }

        User user = userRepository.findByIdOrElseThrow(managerId);

        ScheduleUser su = ScheduleUser.createScheduleManager(schedule, user);
        scheduleUserRepository.save(su);
    }

    /**
     * 담당 매니저 삭제
     *
     * @param requestDto 일정 작성자 ID, 일정 ID, 담당할 유저 ID
     */
    public void unregistManager(RemoveSchedueManagerDto requestDto) {
        Long scheduleId = requestDto.getSchedueId();
        Long authorId = requestDto.getAuthorId();
        Long managerId = requestDto.getUserId();

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(authorId);
        userRepository.findByIdOrElseThrow(managerId);

        ScheduleUser scheduleUser = scheduleUserRepository.findByUserIdAndScheduleId(managerId,
                scheduleId)
            .orElseThrow(() -> new InvalidIdException("담당 유저 레포지토리", "담당 유저", managerId));

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long scheduleAuthorUserId = schedule.getAuthor()
            .getId();

        if (!scheduleAuthorUserId.equals(authorId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        scheduleUser.removeScheduleUser();
        scheduleUserRepository.delete(scheduleUser);
    }
}
