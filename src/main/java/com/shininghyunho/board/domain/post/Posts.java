package com.shininghyunho.board.domain.post;

import com.shininghyunho.board.domain.BaseTimeEntity;
import com.shininghyunho.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(nullable = false)
    private String author; // author 는 바꿀일이 없기때문에 컬럼 표시 x 인데 불러오기 위함

    @Column(nullable = false)
    private Long views;

    @Builder
    public Posts(String title,String content,String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }

    public void addViews(){
        this.views+=1;
    }

    // 영속성 등록전 확인 (views 가 null 이 되지않게끔)
    @PrePersist
    public void prePersist(){
        this.views=(this.views==null)?0:this.views;
    }
}
