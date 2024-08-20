package com.sparta.springintermediateasignment.user.repository;

import com.sparta.springintermediateasignment.user.entity.ScheduleManager;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleManagerRepository extends JpaRepository<ScheduleManager, Long> {
    Optional<ScheduleManager> findByUserId(Long userId);
}

