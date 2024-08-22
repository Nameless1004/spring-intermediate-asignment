package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.schedule.dto.DateDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    // 날씨 캐싱용
    Map<String, String> weatherByDate = new HashMap<>();

    /**
     * 일정 등록
     */
    @Transactional(readOnly = false)
    public Long saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        // 작성 유저 정보 가져오기
        User user = userRepository.findById(scheduleRequestDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));

        // 없으면 불러와서 캐싱
        if (weatherByDate.isEmpty()) {
            String url = "https://f-api.github.io/f-api/weather.json";
            DateDto[] responseData = restTemplate.getForObject(url, DateDto[].class);
            if (responseData == null) {
                throw new IllegalStateException("weather data is null");
            }
            for (DateDto dateDto : responseData) {
                weatherByDate.put(dateDto.getDate(), dateDto.getWeather());
            }
        }

        String date = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("MM-dd"));

        Schedule schedule = Schedule.createSchedule(user, scheduleRequestDto.getTodoTitle(),
            scheduleRequestDto.getTodoContents(), weatherByDate.get(date));
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    /**
     * 일정 단건 조회
     */
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = getSchedule(id);

        return ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUserId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .commentList(schedule.getCommentList())
            .managerList(schedule.getManagerList())
            .build();
    }

    /**
     * 일정다건 조회
     */
    public List<ScheduleAllResponseDto> getAllSchedule(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        List<ScheduleAllResponseDto> result = new ArrayList<>();

        for (Schedule schedule : schedules) {
            ScheduleAllResponseDto responseDto = ScheduleAllResponseDto.builder()
                .scheduleId(schedule.getId())
                .userId(schedule.getUserId())
                .scheduleTitle(schedule.getTodoTitle())
                .scheduleContents(schedule.getTodoContents())
                .createdAt(schedule.getCreatedDate())
                .updatedAt(schedule.getUpdatedDate())
                .commentList(schedule.getCommentList())
                .build();

            result.add(responseDto);
        }

        return result;
    }


    /**
     * 일정 업데이트
     */
    @Transactional(readOnly = false)
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto scheduleRequestDto) {
        Schedule schedule = getSchedule(id);

        schedule.setTodoTitle(scheduleRequestDto.getTitle());
        schedule.setTodoContents(scheduleRequestDto.getContents());

        return ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUserId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .commentList(schedule.getCommentList())
            .build();
    }

    /**
     * 일정 삭제 일정 삭제 시 해당 schedule_manager테이블에서 해당 일정, 해당 일정에 달린 댓글 제거
     */
    @Transactional(readOnly = false)
    public void delete(Long id) {
        // 같은 영속성 컨텍스트 내에서 user에 schedule list를 건드릴 일이 없으면 유저의 일정 리스트에서 일정을 삭제하지 않아도
        // Commit이 될 때 알아서 유저의 일정 리스트에서 삭제된다.
        Schedule schedule = getSchedule(id);
        scheduleRepository.delete(schedule);
    }


    /**
     * ScheduleId가 유효한지 검사
     *
     * @return ScheduleId가 존재하면 엔티티 반환
     * @throws IllegalArgumentException
     */
    private Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
