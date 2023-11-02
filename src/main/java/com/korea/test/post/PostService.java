package com.korea.test.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post addDefaultPost() {
        Post p = new Post();
        p.setTitle("제목");
        p.setContent("내용");
        p.setCreateDate(LocalDateTime.now());
        return this.postRepository.save(p);
    }

    public List<Post> getList() {

        if (this.postRepository.findAll().size() == 0) {
            Post p = new Post();
            p.setTitle("제목");
            p.setContent("내용");
            p.setCreateDate(LocalDateTime.now());
            this.postRepository.save(p);
        }
        return this.postRepository.findAll();
    }

    public Post findPost(Long id) {
        return this.postRepository.findById(id).get();
    }

    public void addPost(Post post) {
        this.postRepository.save(post);
    }
    public void deletePost(Long id) {

        this.postRepository.deleteById(id);
    }
}
