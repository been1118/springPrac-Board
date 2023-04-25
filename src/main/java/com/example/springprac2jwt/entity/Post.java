package com.example.springprac2jwt.entity;

import com.example.springprac2jwt.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false)
    @ColumnDefault("0")
    private long likeCount;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;  //fk

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.post = postRequestDto.getPost();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.post = postRequestDto.getPost();
    }

    public void addComment(List<Comment> commentList){
        this.commentList = commentList;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void checkLikes(long likesCheck) {
        this.likeCount = likesCheck;
    }
}
