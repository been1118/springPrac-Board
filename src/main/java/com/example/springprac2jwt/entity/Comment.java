package com.example.springprac2jwt.entity;

import com.example.springprac2jwt.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;    //pk

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;  //fk

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;  //fk

    public Comment(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void updateComment(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }

}
