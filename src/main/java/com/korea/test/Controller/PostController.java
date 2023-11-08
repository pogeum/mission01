package com.korea.test.Controller;

import com.korea.test.post.Post;
import com.korea.test.post.PostService;
import com.korea.test.postbook.Postbook;
import com.korea.test.postbook.PostbookService;
import com.korea.test.user.SiteUser;
import com.korea.test.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostbookService postbookService;
    private final UserService userService;
//    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/list")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Postbook> postbookList = postbookService.getbookList();
//        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(postbookList.size() == 0) {
            Postbook postbook = postbookService.setDefaultPostbook();

            SiteUser siteUser = new SiteUser();
            siteUser.setUsername("unknown");
            siteUser.setCreateDate(LocalDateTime.now());
            siteUser.setPassword("00");
            siteUser.setEmail("unknown@unknown.com");
            userService.setDefaultUser(siteUser);
            Post post = postService.setDefaultPost(postbook,siteUser);
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
//북id로 해야되나url그럼다수정해야됨 ㅜ
    //포스트북id만으로targetpost찾을수없음.,d아이디받아오면되나?
    @GetMapping("/detail/{postid}")//클릭이..포스트북일때랑 포스트일때랑 나눠서 작성?
    public String detailpost(Model model, @PathVariable Long postid) { //이게 북아이디, 포스트아이디 둘중 하나?
        Post post = postService.findPost(postid);//타겟포스트
        Postbook postbook = post.getPostbook(); //타겟 포스트북


        List<Post> postList = postbook.getPostList(); //포스트나열
        List<Postbook> postbookList = postbookService.getbookList(); //포스트북나열


        model.addAttribute("postbookList", postbookList);
        model.addAttribute("targetPost",post);
        model.addAttribute("postList", postList);
        model.addAttribute("targetPostbook",postbook);
        return "main";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addpost") //여기서에러 given id must not be null? 밑에 postbookid가 널인가
    public String write(Long postbookid,Principal principal) { //타겟북아이디임
        //폼에서 받아오는거 name 이랑, 위함수 인자로 받아오는 거랑 변수명이 같아야됨!!!
        //만약 다르게 받아올려면 @RequestParam("id") Long postbookid 이렇게 해줘야됨!!!
        //에러해결
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Post post = postService.setDefaultPost(postbookService.findPostbook(postbookid),siteUser);
        //포스트레파지토리에 저장+새로생성+포스트북연결

        return "redirect:/post/detail/" + post.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String update(Long postid, String title, String content, Principal principal) {
        Post post = postService.findPost(postid);
        if (title == null || title.isEmpty()) {

            post.setTitle("제목없음");
        }else {
            post.setTitle(title);
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        post.setContent(content);
        postService.setPost(post,siteUser);
        return "redirect:/post/detail/" + postid;
    }

    @PostMapping("/delete")
    public String delete(Long bookid, Long postid) {
        postService.deletePost(postid); //이거 디비에서 삭제까지는 되는데 화면출력이 문제?
        Postbook postbook = postbookService.findPostbook(bookid);
        Post post = postbook.getPostList().get(0);
        postid = post.getId();
        return "redirect:/post/detail/" + postid;
    }

}
