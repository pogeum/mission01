package com.korea.test.post;

import com.korea.test.postbook.Postbook;
import com.korea.test.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post setDefaultPost(Postbook postbook) {
        Post post = new Post();
        post.setTitle("제목");
        post.setContent("내용");
        post.setCreateDate(LocalDateTime.now());
//        post.setAuthor(user);
        post.setPostbook(postbook);
        //postbook.setPostList(postbook.getPostList());
        return this.postRepository.save(post);
    }

    public void setPost(Post post, SiteUser user) {
        post.setAuthor(user);
        this.postRepository.save(post);
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


    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }

    public List<Post> searchBykeyword(String keyword) {
        List<Post> postList = new ArrayList<>() ;

        for( Post post : this.postRepository.findAll() ) {
            if (post.getTitle().equals(keyword) || post.getContent().equals(keyword)) {
                postList.add(post);
            }
        }
        return postList;
    }

}
