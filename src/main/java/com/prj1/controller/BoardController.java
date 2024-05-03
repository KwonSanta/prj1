package com.prj1.controller;

import com.prj1.domain.Board;
import com.prj1.mapper.BoardMapper;
import com.prj1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;
    private final BoardMapper boardMapper;

    @GetMapping("/add")
    public String add() {
        return "board/add";
    }

    @PostMapping("/add")
    public String addPost(Board board,
                          // auth 는 첫 로그인 시 입력 된 username, password 인증 시 생성된 userDetails 객체가 저장 됨
                          Authentication auth,
                          RedirectAttributes rttr) {

        service.add(board, auth);

        rttr.addAttribute("id", board.getId());

        return "redirect:/board";
    }

    // /board?id=xx
    @GetMapping("/board")
    public String view(Integer id, Model model) {

        // 게시물 조회(select)
        Board board = service.get(id);

        // 조회한 결과 모델에 넣고
        model.addAttribute("board", board);

        // jsp 로 포워드
        return "board/view";
    }

    // paging 기능 추가
    // ?page=xx
    @GetMapping("/")
    public String home(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       Model model) {

        // 게시물 목록 조회(select)
        // 모델에 넣고
        model.addAllAttributes(service.list(page));
        // view(jsp) 로 포워딩
        return "board/home";
    }

    @PostMapping("/delete")
    public String delete(Integer id,
                         Authentication auth) {
        if (service.hasAccess(id, auth)) {
            service.remove(id);
        }
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modify(Integer id, Model model) {

        // 조회, 모델에 넣고
        model.addAttribute("board", service.get(id));
        // view 로 포워드
        return "board/modify";
    }
    @PostMapping("/modify")
    public String modifyPost(Board board,
                             RedirectAttributes rttr) {

        service.modify(board);
        rttr.addAttribute("id", board.getId());
        return "redirect:/board";
    }
}
