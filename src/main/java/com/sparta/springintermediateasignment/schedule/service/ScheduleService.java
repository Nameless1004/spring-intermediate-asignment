package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.schedule.dto.AddScheduleManagerDto;
import com.sparta.springintermediateasignment.schedule.dto.RemoveSchedueManagerDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.entity.ScheduleUser;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.ScheduleUserRepository;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import com.sparta.springintermediateasignment.weather.WeatherRestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleUserRepository scheduleUserRepository;
    private final UserRepository userRepository;
    private final WeatherRestService weatherService;

    /**
     * 일정 등록
     */
    public Long saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        // 작성 유저 정보 가져오기
        User user = userRepository.findById(scheduleRequestDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));

        String weather = weatherService.getWeatherByNow();

        Schedule schedule = Schedule.createSchedule(user, scheduleRequestDto.getTodoTitle(),
            scheduleRequestDto.getTodoContents(), weather);
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    /**
     * 일정 단건 조회
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new InvalidIdException("일정 레포지토리", "일정", id));

        List<ScheduleManagerInfoDto> managers = scheduleUserRepository.findUsersByScheduleId(id);

        return ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getAuthor()
                .getId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .managaers(managers)
            .build();
    }

    /**
     * 일정다건 조회
     */
    @Transactional(readOnly = true)
    public Page<ScheduleAllResponseDto> getAllSchedule(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        return schedules.map(schedule -> ScheduleAllResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getAuthor()
                .getId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .build());
    }


    /**
     * 일정 업데이트
     */
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        schedule.update(scheduleRequestDto.getTitle(), scheduleRequestDto.getContents());

        return ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getAuthor()
                .getId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .build();
    }

    /**
     * 일정 삭제 일정 삭제 시 해당 schedule_manager테이블에서 해당 일정, 해당 일정에 달린 댓글 제거
     */
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        // 작성자의 작성 일정 목록에서 해당 일정 제거
        User author = schedule.getAuthor();
        author.removeSchedule(schedule);

        // 일정 삭제 시 담당자가 있다면 삭제 안됨
        scheduleRepository.delete(schedule);
    }


    /**
     * 일정에 담당자 추가
     *
     * @param scheduleId
     * @param requestDto 일정 작성자 ID, 일정 ID, 담당할 유저 ID
     */
    public void registManager(Long scheduleId, AddScheduleManagerDto requestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long authorUserId = requestDto.getAuthorUserId();
        Long scheduleAuthorUserId = schedule.getAuthor()
            .getId();
        if (!scheduleAuthorUserId.equals(authorUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        Long managerId = requestDto.getManagerId();

        if (scheduleUserRepository.existsByScheduleIdAndUserId(scheduleId, managerId)) {
            throw new IllegalArgumentException("이미 등록된 담당자입니다.");
        }

        User user = userRepository.findByIdOrElseThrow(managerId);

        ScheduleUser su = ScheduleUser.createScheduleManager(schedule, user);
        scheduleUserRepository.save(su);
    }

    /**
     * 담당 매니저 삭제
     *
     * @param scheduleId
     * @param managerId
     * @param requestDto 일정 작성자 ID, 일정 ID, 담당할 유저 ID
     */
    public void unregistManager(Long scheduleId, Long managerId, RemoveSchedueManagerDto requestDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        userRepository.findByIdOrElseThrow(managerId);

        ScheduleUser scheduleUser = scheduleUserRepository.findByUserIdAndScheduleId(managerId,
                scheduleId)
            .orElseThrow(() -> new InvalidIdException("담당 유저 레포지토리", "담당 유저", managerId));

        // 작성한 유저와 요청하는 유저의 아이디가 다르다면 throw
        Long authorUserId = requestDto.getAuthorId();
        Long scheduleAuthorUserId = schedule.getAuthor().getId();

        if (!scheduleAuthorUserId.equals(authorUserId)) {
            throw new IllegalArgumentException("해당 일정을 작성한 유저가 아닙니다.");
        }

        scheduleUser.removeScheduleUser();
        scheduleUserRepository.delete(scheduleUser);
    }
}
