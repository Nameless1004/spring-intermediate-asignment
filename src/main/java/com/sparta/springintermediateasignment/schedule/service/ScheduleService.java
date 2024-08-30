package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.common.exceptoins.InvalidIdException;
import com.sparta.springintermediateasignment.manager.repository.ScheduleUserRepository;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import com.sparta.springintermediateasignment.weather.WeatherService;
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
    private final WeatherService weatherService;

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

}
