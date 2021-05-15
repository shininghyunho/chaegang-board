package com.shininghyunho.board.controller;

import com.shininghyunho.board.controller.dto.UserUpdateRequestDto;
import com.shininghyunho.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User REST api 호출
 * ADMIN 계정만 접근 가능
 */
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PutMapping("/api/v1/user/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto){
        return userService.update(id,requestDto);
    }
}
