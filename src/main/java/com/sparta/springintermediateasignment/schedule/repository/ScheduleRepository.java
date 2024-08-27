package com.sparta.springintermediateasignment.schedule.repository;

import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * ScheduleId가 유효한지 검사
     *
     * @return ScheduleId가 존재하면 엔티티 반환
     * @throws IllegalArgumentException
     */
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
