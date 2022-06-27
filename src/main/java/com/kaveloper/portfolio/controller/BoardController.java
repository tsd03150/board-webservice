package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.config.auth.LoginMember;
import com.kaveloper.portfolio.config.auth.dto.SessionMember;
import com.kaveloper.portfolio.dto.BoardListResponseDTO;
import com.kaveloper.portfolio.dto.BoardSaveRequestDTO;
import com.kaveloper.portfolio.dto.ImgSaveRequestDTO;
import com.kaveloper.portfolio.dto.PageRequestDTO;
import com.kaveloper.portfolio.entity.UploadFile;
import com.kaveloper.portfolio.file.FileStore;
import com.kaveloper.portfolio.service.BoardService;
import com.kaveloper.portfolio.service.ImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;
    private final FileStore fileStore;
    private final ImgService imgService;

    @GetMapping("/login")
    public String login() {
        return "board/login";
    }

    @GetMapping("/popupImg")
    public String addImg(Model model) {
        model.addAttribute("imgSaveRequestDTO", new ImgSaveRequestDTO());
        return "board/popupImg";
    }

    @PostMapping("/popupImg")
    public void saveImg(@ModelAttribute("imgSaveRequestDTO") ImgSaveRequestDTO requestDTO) throws IOException {
        log.info("이미지를 입력합니다");
        List<UploadFile> uploadFiles = fileStore.storeFiles(requestDTO.getImageFiles());

        log.info("저장된 파일 : {}", uploadFiles);

        // 데이터베이스에 저장
        imgService.saveImg(uploadFiles);
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
    public String writeBoard(@Valid @ModelAttribute("boardSaveRequestDTO") BoardSaveRequestDTO boardSaveRequestDTO,
                             BindingResult bindingResult, @LoginMember SessionMember member, Model model) throws IOException {
        log.info("글을 작성합니다");

        if (bindingResult.hasErrors()) {
            // @Valid 제약을 지키지 못하는 경우
            // 다시 글작성 뷰가 나와야 하는데
            // 이 때 로그인한 작성자 이름 또한 다시 나와야 한다
            if (member != null) {
                model.addAttribute("memberName", member.getName());
            }

            return "board/write";
        }

        log.info("등록한 글 {}", boardSaveRequestDTO);
        boardService.saveBoard(boardSaveRequestDTO, member.getMid());

        return "redirect:/board/list";
    }

    @GetMapping("/detail")
    public void detail(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bid, Model model,
                       @LoginMember SessionMember member) {
        BoardListResponseDTO boardDTO = boardService.getBoard(bid);
        boardService.upViewCount(bid); // 게시판 글을 누르게 되면 조회수 증가

        if (member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("mid", member.getMid());
        }

        model.addAttribute("boardDTO", boardDTO);
    }

    @GetMapping("/update")
    public void update(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bid, Model model,
                       @LoginMember SessionMember member) {
        BoardListResponseDTO boardDTO = boardService.getBoard(bid);

        if (member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("mid", member.getMid());
        }

        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/update")
    public String updateBoard(@Valid @ModelAttribute("boardDTO") BoardSaveRequestDTO boardDTO, BindingResult bindingResult,
                              @ModelAttribute("requestDTO") PageRequestDTO requestDTO, @LoginMember SessionMember member,
                              Model model, RedirectAttributes redirectAttributes) {
        log.info("업데이트를 시작합니다.");
        if (bindingResult.hasErrors()) {
            // @Valid 제약을 지키지 못하는 경우
            // 다시 글작성 뷰가 나와야 하는데
            // 이 때 로그인한 작성자 이름 또한 다시 나와야 한다
            if (member != null) {
                model.addAttribute("memberName", member.getName());
            }
            return "board/update";
        }

        boardService.updateBoard(boardDTO);
        log.info("업데이트 할 글 : {}", boardDTO);

        return "redirect:/board/list";
    }
}
