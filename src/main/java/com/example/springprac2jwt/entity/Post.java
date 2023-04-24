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


    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;  //fk

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount;



    public Post(String title, String post, User user) {
        this.title = title;
        this.post = post;
        this.user = user;
    }

    public Post(PostRequestDto boardDTO){
        this.title = boardDTO.getTitle();
        this.post = boardDTO.getPost();
    }

    public void update(PostRequestDto boardDTO) {
        this.title = boardDTO.getTitle();
        this.post = boardDTO.getPost();
    }

    public void addComment(List<Comment> commentList){
        this.commentList = commentList;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addLike() {
        likeCount += 1;
    }

    public void delLike() {
        likeCount -= 1;
    }
}
