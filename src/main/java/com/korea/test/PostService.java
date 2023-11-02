package com.korea.test;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

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

    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }
}
