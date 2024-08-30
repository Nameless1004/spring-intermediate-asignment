package com.sparta.springintermediateasignment.user.dto;

import com.sparta.springintermediateasignment.user.entity.User;
import com.sparta.springintermediateasignment.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    private UserRole role;
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
            .role(user.getRole())
            .createdAt(user.getCreatedDate())
            .updatedAt(user.getUpdatedDate())
            .build();
    }
}
