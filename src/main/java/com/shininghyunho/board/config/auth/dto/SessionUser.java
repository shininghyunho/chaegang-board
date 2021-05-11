package com.shininghyunho.board.config.auth.dto;

import com.shininghyunho.board.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보, 필요한 속성만 저장함
 * User를 쓰지않고 따로 SessionUser를 만든 이유 : 직렬화를 넣어야하기 때문
 * 세션에 넣기 위해서는 직렬화가 필요한데 User를 직렬화 해버리면 다른 엔티티와
 * 관계를 형성할때 성능 이슈, 부수 효과가 발생. 그래서 독립적인 Dto를 따로 생성.
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;


    public SessionUser(User user){
        this.name=user.getName();
        this.email=user.getEmail();
        this.picture= user.getPicture();
    }
}
