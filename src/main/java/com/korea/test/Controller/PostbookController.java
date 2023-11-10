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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/postbook")
public class PostbookController {
    private final PostbookService postbookService;
    private final PostService postService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addpostbook") //여기서부터에러., 영속상태가아님? 둘다 서로 저장안해줘서그런가? 노노 레파지노리저장안해서?
    public String addpostbook(Principal principal) { //에러해결
        Postbook postbook = postbookService.setDefaultPostbook();
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Post post = postService.setDefaultPost(postbook,siteUser); //엔티티 연결은 주로 주도권가진애가 하는것?
//        post.setAuthor(user);
        postbookService.addPostbook(postbook, post);
        return "redirect:/postbook/detail/" + postbook.getId(); //하면 detail postbook id 는없어도될듯?
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifytitle")
    public String modifytitle(Long postbookid,String newtitle) {
//        System.out.println(newtitle);
//        System.out.println(postbookid);
        Postbook postbook = postbookService.findPostbook(postbookid);
        postbook.setTitle(newtitle);
        postbookService.setPostbook(postbook);

        return "redirect:/postbook/detail/" + postbookid;
    }

    @GetMapping("/detail/{postbookid}") //질문 도대체 뭘 맞춰야 ambiguous ㅇㅔ러 안나는지???? ->url에서 {}내용이랑, pathvariable Long --- 내용이랑.
    public String detailpostbook(Model model, @PathVariable Long postbookid) {
        Postbook postbook = postbookService.findPostbook(postbookid); //타겟포스트북

        List<Post> postList = postbook.getPostList(); //타겟 포스트에 연결된 포스트리스트
        List<Postbook> postbookList = postbookService.getbookList(); // 일단정렬할 포스트북리스트

        model.addAttribute("postbookList", postbookList);
        model.addAttribute("targetPostbook",postbook);
        model.addAttribute("postList", postList);
        System.out.println(postbookList.size());//3
        System.out.println(postList.size());//0
        model.addAttribute("targetPost",postList.get(0));


        return "main";
    }

    @PostMapping("/deletepostbook")
    public String delete(Long postbookid) { //이거어차피포스트북지우는데 cascaderemove안하면 자동으로 안지워지나?연결엔티티는?
        postbookService.deletePostbook(postbookid);
        List<Postbook> postbookList = postbookService.getbookList();
        Postbook postbook = postbookList.get(0);
        postbookid = postbook.getId();
        return "redirect:/postbook/detail/"+ postbookid; //수정하기
    }
}
