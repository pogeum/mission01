package com.korea.test.post;

import com.korea.test.postbook.Postbook;
import com.korea.test.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

    @ManyToOne
    private Postbook postbook;

    @ManyToOne
    private SiteUser author;
}
