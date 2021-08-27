package com.shininghyunho.board.service.posts;

import com.shininghyunho.board.controller.dto.PostsListResponseDto;
import com.shininghyunho.board.controller.dto.PostsResponseDto;
import com.shininghyunho.board.controller.dto.PostsSaveRequestDto;
import com.shininghyunho.board.controller.dto.PostsUpdateRequestDto;
import com.shininghyunho.board.domain.post.Posts;
import com.shininghyunho.board.domain.post.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto,String author){
        // requestDto + email 로 post 를 만들어서 넘겨줘야함
        return postsRepository.save(
                Posts.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(author)
                .build()
        ).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto,String author){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id에 해당하는 게시글이 없습니다. id :"+id));
        // 작성자가 같을때만 update
        if (Objects.equals(entity.getAuthor(), author)){
            entity.update(requestDto.getTitle(),requestDto.getContent());
        }
        // TODO : 작성자와 로그인한 이메일이 다를떄 어떻게 반응하지
        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("id에 해당하는 게시글이 없습니다. id :"+id));
        postsRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id에 해당하는 게시글이 없습니다. id :"+id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public void addViews(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id에 해당하는 게시글이 없습니다. id : "+id));
        entity.addViews();
    }
}
