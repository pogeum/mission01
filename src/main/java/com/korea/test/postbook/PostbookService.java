package com.korea.test.postbook;

import com.korea.test.post.Post;
import com.korea.test.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostbookService {
    private final PostbookRepository postbookRepository;


    public List<Postbook> getbookList() {

        //
//        if (this.postbookRepository.findAll().size() == 0) {
//            Postbook p = new Postbook();
//            p.setTitle("새노트");
//
//            postService.setPost(); // 기본 포스트
//            p.setPostList(postService.getList());
//
//            this.postbookRepository.save(p);
//        }
        return this.postbookRepository.findAll();
    }

    public void addDefaultPostbook(Post post) {
        Postbook p = new Postbook();
        p.setTitle("새노트");

        post.setPostbook(p);
        p.getPostList().add(post);
//        postService.setPost(); // 기본 포스트
//        p.setPostList(postService.getList());

        this.postbookRepository.save(p);
    }

    public Postbook findPostbook(Long id) {
        return this.postbookRepository.findById(id).get();
    }

    public void addPostbook(Postbook postbook, Post post) {
        postbook.setTitle("새노트");
        postbook.getPostList().add(post);
        post.setPostbook(postbook);
//        Post post = new Post();
//        post.setTitle("new title..");
//        post.setContent("");
//        post.setCreateDate(LocalDateTime.now());
//        postService.addPost(post);
//        postbook.setPostList(postService.getList());
        this.postbookRepository.save(postbook);
    }
    public void deletePostbook(Long id) {

        this.postbookRepository.deleteById(id);
    }






}
