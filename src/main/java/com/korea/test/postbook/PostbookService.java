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

        return this.postbookRepository.findAll();
    }

    public Postbook setDefaultPostbook(){
        Postbook postbook = new Postbook();
        postbook.setTitle("새노트");
        postbookRepository.save(postbook);
        return this.postbookRepository.save(postbook);
    }

    public Postbook findPostbook(Long id) {
        return this.postbookRepository.findById(id).get();
    }

    public void addPostbook(Postbook postbook, Post post) {
        postbook.setTitle("새노트");
        postbook.getPostList().add(post);
        post.setPostbook(postbook);
        this.postbookRepository.save(postbook);
    }
    public void deletePostbook(Long id) {

        this.postbookRepository.deleteById(id);
    }

    public  void setPostbook(Postbook postbook) {
        this.postbookRepository.save(postbook);
    }

    public List<Postbook> getParentbookList() {
        return postbookRepository.findByParentId(null);
    }
}
