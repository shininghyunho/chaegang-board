package com.shininghyunho.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile(){
        // real,oauth,real-db
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        // real1,real2 무중단 배포용
        List<String> realProfiles = Arrays.asList("real","real1","real2");
        String defaultProfile = profiles.isEmpty()? "default":profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
