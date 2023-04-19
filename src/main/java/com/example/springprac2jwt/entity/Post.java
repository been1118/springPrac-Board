package com.example.springprac2jwt.entity;

import com.example.springprac2jwt.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String post;


    public Post(PostRequestDto requestDto, User user) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.post = requestDto.getPost();
    }

    public void update(PostRequestDto postRequestDto) {
        this.post = postRequestDto.getPost();
    }
}
