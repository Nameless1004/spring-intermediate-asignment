package com.sparta.springintermediateasignment;

import com.sparta.springintermediateasignment.schedule.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ScheduleTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ScheduleTest(EntityManager em) {
        this.em = em;
    }

    @Test
    @Transactional
    void test1(){
//        Schedule schedule = new Schedule();
//        schedule.setCreatedAt(LocalDateTime.now());
//        schedule.setUpdatedAt(LocalDateTime.now());
//        schedule.setTodoTitle("title test");
//        schedule.setTodoContents("contents test");
//        schedule.setUserName("name test");
//        em.persist(schedule);
    }
}
