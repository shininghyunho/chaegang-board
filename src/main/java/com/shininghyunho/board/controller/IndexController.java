package com.shininghyunho.board.controller;

import com.shininghyunho.board.config.auth.LoginUser;
import com.shininghyunho.board.config.auth.dto.SessionUser;
import com.shininghyunho.board.controller.dto.PostsResponseDto;
import com.shininghyunho.board.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/*
* 페이지 표시를 위한 컨트롤러
* */
// TODO : 업데이트가 아닌 post view 페이지를 추가해줘야함
@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    // LoginUser 어노테이션을 통해 세션에서 "user"를 가져옴
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        // posts라는 애트리뷰트 추가해서 넘겨줌
        model.addAttribute("posts",postsService.findAllDesc());

        if (user!=null){
            model.addAttribute("loginUserName",user.getName());
        }
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
