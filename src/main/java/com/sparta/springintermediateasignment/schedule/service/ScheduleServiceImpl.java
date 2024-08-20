package com.sparta.springintermediateasignment.schedule.service;

import com.sparta.springintermediateasignment.comment.dto.CommentDto;
import com.sparta.springintermediateasignment.schedule.dto.DateDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleAllResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleManagerInfoDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleRequestDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleResponseDto;
import com.sparta.springintermediateasignment.schedule.dto.ScheduleUpdateDto;
import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import com.sparta.springintermediateasignment.schedule.repository.ScheduleRepository;
import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.repository.UserRepository;
import com.sparta.springintermediateasignment.util.JwtUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    // private final ScheduleManagerRepository scheduleUserRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final RestTemplate restTemplate = new RestTemplate();

    // 날씨 캐싱용
    Map<String, String> weatherByDate = new HashMap<>();

    // 저장
    @Override
    @Transactional(readOnly = false)
    public Long saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        // 작성 유저 정보 가져오기
        User user = userRepository.findById(scheduleRequestDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));

        Schedule schedule = new Schedule(user, scheduleRequestDto.getTodoTitle(), scheduleRequestDto.getTodoContents());

        // 없으면 불러와서 캐싱
        if(weatherByDate.isEmpty()){
            String url = "https://f-api.github.io/f-api/weather.json";
            DateDto[] responseData = restTemplate.getForObject(url, DateDto[].class);
            for(DateDto dateDto : responseData){
                weatherByDate.put(dateDto.getDate(), dateDto.getWeather());
            }
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        schedule.setWeather(weatherByDate.get(date));

        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    // 단건 조회
    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = getSchedule(id);

        return ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUser().getId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .commentList(getComments(schedule))
            .managerList(getManagerInfo(schedule))
            .build();
    }

    // 다건 조회
    @Override
    public List<ScheduleAllResponseDto> getAllSchedule(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        List<ScheduleAllResponseDto> result = new ArrayList<>();

        for(Schedule schedule : schedules) {
            ScheduleAllResponseDto responseDto = ScheduleAllResponseDto.builder()
                .scheduleId(schedule.getId())
                .userId(schedule.getUser().getId())
                .scheduleTitle(schedule.getTodoTitle())
                .scheduleContents(schedule.getTodoContents())
                .createdAt(schedule.getCreatedDate())
                .updatedAt(schedule.getUpdatedDate())
                .commentList(getComments(schedule))
                .build();

            result.add(responseDto);
        }

        return result;
    }


    @Override
    @Transactional(readOnly = false)
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto scheduleRequestDto) {
        Schedule schedule = getSchedule(id);

        schedule.setTodoTitle(scheduleRequestDto.getTitle());
        schedule.setTodoContents(scheduleRequestDto.getContents());

        scheduleRepository.save(schedule);

        return  ScheduleResponseDto.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUser().getId())
            .scheduleTitle(schedule.getTodoTitle())
            .scheduleContents(schedule.getTodoContents())
            .createdAt(schedule.getCreatedDate())
            .updatedAt(schedule.getUpdatedDate())
            .commentList(getComments(schedule))
            .build();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Schedule schedule = getSchedule(id);
        scheduleRepository.delete(schedule);
    }


    // -----------------------------------------
    private Schedule getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
        return schedule;
    }

    private List<CommentDto> getComments(Schedule schedule) {
        return schedule.getComments()
            .stream()
            .map(CommentDto::of)
            .collect(Collectors.toList());
    }

    private List<ScheduleManagerInfoDto> getManagerInfo(Schedule schedule) {
        List<ScheduleManagerInfoDto> dtos = schedule.getScheduleManagers()
            .stream()
            .map(scheduleMangaer -> new ScheduleManagerInfoDto(scheduleMangaer.getUser()))
            .toList();
        return dtos;
    }
}
