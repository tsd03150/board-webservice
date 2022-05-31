package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDto, Model model, @LoginMember SesstionMember member) {

        model.addAttribute("result", boardService.getList(pageRequestDto));

        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }
        return "board/list";
    }

    @PostMapping
    public Long save(@RequestBody BoardSaveRequestDTO requestDto) {
        return boardService.save(requestDto);
    }



}
