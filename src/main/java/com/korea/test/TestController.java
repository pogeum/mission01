package com.korea.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class TestController {

    @Autowired
    private PostRepository postRepository;

    private final PostService postService;

    @RequestMapping("/test")
    @ResponseBody public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Post> postList = postService.getList();


        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost", postList.get(0));

        return "main";
    }

    @PostMapping("/write")
    public String write() {
        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());

        postRepository.save(post);

        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Post post = postRepository.findById(id).get();
        model.addAttribute("targetPost", post);
        model.addAttribute("postList", postRepository.findAll());

        return "main";
    }
    @PostMapping("/update")
    public String update(Long id, String title, String content) {
        Post post = postRepository.findById(id).get();

        if (title == null || title.isEmpty()) {

            post.setTitle("제목없음");
        }else {
            post.setTitle(title);
        }

        post.setContent(content);

        postRepository.save(post);
        return "redirect:/detail/" + id;
    }
///detail/{id}/delete
    @PostMapping("/delete")
    public String delete(Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }
//    겟 포스트 헷갈림.,pathvariable하려면..url에 {id} 이렇게 값이잇어야되고..
//    @GetMapping("/delete")

}
