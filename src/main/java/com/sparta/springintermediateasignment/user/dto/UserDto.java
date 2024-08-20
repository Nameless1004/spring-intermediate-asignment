package com.sparta.springintermediateasignment.user.dto;

import com.sparta.springintermediateasignment.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String email;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public static UserDto of(User user) {
        // 필드 추가하면 변경해야함
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedDate())
            .updatedAt(user.getUpdatedDate())
            .build();
    }
}
