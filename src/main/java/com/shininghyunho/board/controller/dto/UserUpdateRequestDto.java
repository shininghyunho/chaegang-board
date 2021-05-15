package com.shininghyunho.board.controller.dto;

import com.shininghyunho.board.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    // role
    private Role role;

    @Builder
    public UserUpdateRequestDto(Role role){
        this.role=role;
    }
}
