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

    @OneToMany(mappedBy = "postbook",cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @ManyToOne
    private Postbook parent;

    @OneToMany(mappedBy = "parent")
    private List<Postbook> childList;



}
