package com.shininghyunho.board.controller;

import com.shininghyunho.board.controller.dto.PostsResponseDto;
import com.shininghyunho.board.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
* 페이지 표시를 위한 컨트롤러
* */
@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        // posts라는 애트리뷰트 추가해서 넘겨줌
        model.addAttribute("posts",postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id,Model model){
        PostsResponseDto dto = postsService.findById(id);
        // posts라는 애트리뷰트 추가해서 넘겨줌
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
