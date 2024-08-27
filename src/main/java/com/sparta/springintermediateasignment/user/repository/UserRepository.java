package com.sparta.springintermediateasignment.user.repository;

import com.sparta.springintermediateasignment.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /**
     * 유저 유효성 검사
     * @param id 유저 아이디
     * @return 해당 아이디의 유저가 존재하면 엔티티 반환
     */
    default User findByIdOrElseThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
