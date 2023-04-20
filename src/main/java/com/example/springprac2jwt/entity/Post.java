package com.example.springprac2jwt.entity;

import com.example.springprac2jwt.dto.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;    //pk

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String post;


    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;  //fk

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();



    public Post(PostRequestDto requestDto, User user) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.post = requestDto.getPost();
    }

    public void update(PostRequestDto postRequestDto) {
        this.post = postRequestDto.getPost();
    }

    public void addComment(Comment comment){
        commentList.add(comment);
        comment.setPost(this);
    }
}
