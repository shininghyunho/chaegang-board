package com.shininghyunho.board.service;

import com.shininghyunho.board.controller.dto.PostsListResponseDto;
import com.shininghyunho.board.controller.dto.PostsSaveRequestDto;
import com.shininghyunho.board.domain.post.Posts;
import com.shininghyunho.board.domain.post.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
