package com.korea.test.postbook;

import com.korea.test.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Postbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    //(mappedBy = "post", cascade = CascadeType.REMOVE)

    @OneToMany(mappedBy = "postbook")
    private List<Post> postList = new ArrayList<>();

}