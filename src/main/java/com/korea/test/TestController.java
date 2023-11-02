package com.korea.test;

import com.korea.test.post.Post;
import com.korea.test.post.PostService;
import com.korea.test.postbook.Postbook;
import com.korea.test.postbook.PostbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TestController {


    private final PostService postService;
    private final PostbookService postbookService;


    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Postbook> postbookList = postbookService.getbookList();

        if(postbookList.size() == 0) {
            Post post = postService.addDefaultPost();
            postbookService.addDefaultPostbook(post);

            return "redirect:/";
        }

        List<Post> postList = postbookList.get(0).getPostList();

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("postbookList", postbookList);
        model.addAttribute("targetPostbook", postbookList.get(0)); // -> 선택된노트북만 배경바꾸는거에 적용하기
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost", postList.get(0));

        return "main";
    }

    @GetMapping("/detail/{bookid}")
    public String detail(Model model, @PathVariable("bookid") Long id) {
        Postbook postbook = postbookService.findPostbook(id);

        List<Post> postList = postbook.getPostList();
        List<Postbook> postbookList = postbookService.getbookList();


        model.addAttribute("postbookList", postbookList);
        model.addAttribute("targetPost",postList.get(0));
        model.addAttribute("postList", postList);
        model.addAttribute("targetPostbook",postbook);
        return "main";
    }

    @GetMapping("/detail/{bookid}/{postid}")
    public String detail(Model model, @PathVariable("bookid") Long bid, @PathVariable("postid") Long pid){
        List<Postbook> postbookList = postbookService.getbookList();

        Postbook postbook = postbookService.findPostbook(bid);
        List<Post> postList = postbook.getPostList();
        Post post = postService.findPost(pid);

        model.addAttribute("postbookList", postbookList);
        model.addAttribute("targetPostbook",postbook);
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost",post);

        return "main";
    }

    @PostMapping("/addpostbook")
    public String addpostbook() {
        Postbook postbook = new Postbook();
        Post post = postService.addDefaultPost();
        postbookService.addPostbook(postbook, post);
        return "redirect:/";
    }

    @PostMapping("/write")
    public String write(Long id) { //타겟북아이디임

        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        post.setPostbook(postbookService.findPostbook(id));
        postService.addPost(post);

        return "redirect:/detail/" + id;
    }


    @PostMapping("/update")
    public String update(Long bookid, Long postid, String title, String content) {
        Post post = postService.findPost(postid);
        if (title == null || title.isEmpty()) {

            post.setTitle("제목없음");
        }else {
            post.setTitle(title);
        }

        post.setContent(content);
        postService.addPost(post);
        return "redirect:/detail/" + bookid +"/" + postid;
    }

    @PostMapping("/delete")
    public String delete(Long bookid, Long postid) {
        postService.deletePost(postid);
        return "redirect:/detail/"+ bookid;
    }

}
