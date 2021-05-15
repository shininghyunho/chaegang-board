package com.shininghyunho.board.service.posts;

import com.shininghyunho.board.controller.dto.UserUpdateRequestDto;
import com.shininghyunho.board.domain.user.User;
import com.shininghyunho.board.domain.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 유저 권한 guest -> user 업데이트
    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto){
        User entity = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id에 해당하는 유저가 없습니다. id : "+id));
        entity.update(requestDto.getRole());
        return id;
    }
}
