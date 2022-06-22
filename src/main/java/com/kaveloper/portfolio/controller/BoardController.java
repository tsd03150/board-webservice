package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.config.auth.LoginMember;
import com.kaveloper.portfolio.config.auth.dto.SessionMember;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/login")
    public String login() {
        return "board/login";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model, @LoginMember SessionMember member) {

        model.addAttribute("content", boardService.getList(pageRequestDTO));

        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }

        return "board/list";
    }

    @GetMapping("/write")
    public String write(Model model, @LoginMember SessionMember member) {
        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }

        model.addAttribute("boardSaveRequestDTO", new BoardSaveRequestDTO());

        return "board/write";
    }

    @PostMapping("/write")
    public String saveBoard(@Valid BoardSaveRequestDTO boardSaveRequestDTO, BindingResult bindingResult,
                            @LoginMember SessionMember member, Model model) {

        if (bindingResult.hasErrors()) {
            // @Valid 제약을 지키지 못하는 경우
            // 다시 글작성 뷰가 나와야 하는데
            // 이 때 로그인한 작성자 이름 또한 다시 나와야 한다
            if (member != null) {
                model.addAttribute("memberName", member.getName());
            }
            return "board/write";
        }

        boardService.saveBoard(boardSaveRequestDTO, member.getMid());
        log.info("등록한 글 {}", boardSaveRequestDTO);

        return "redirect:/board/list";
    }

    @GetMapping("/detail")
    public void detail(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bid, Model model,
                       @LoginMember SessionMember member) {
        BoardListResponseDTO boardDTO = boardService.getBoard(bid);

        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }

        model.addAttribute("boardDTO", boardDTO);

    }
}
