package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {

        model.addAttribute("content", boardService.getList(pageRequestDTO));

        return "board/list";
    }

    @PostMapping
    public Long save(@RequestBody BoardSaveRequestDTO requestDto) {
        return boardService.save(requestDto);
    }
}
