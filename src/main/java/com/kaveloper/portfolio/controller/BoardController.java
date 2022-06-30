package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.config.auth.LoginMember;
import com.kaveloper.portfolio.config.auth.dto.SessionMember;
import com.kaveloper.portfolio.dto.*;
import com.kaveloper.portfolio.entity.UploadFile;
import com.kaveloper.portfolio.file.FileStore;
import com.kaveloper.portfolio.service.BoardService;
import com.kaveloper.portfolio.service.ImgService;
import com.kaveloper.portfolio.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;
    private final FileStore fileStore;
    private final ImgService imgService;
    private final ReplyService replyService;

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
    public String writeBoard(@Valid @ModelAttribute("boardSaveRequestDTO") BoardSaveRequestDTO boardSaveRequestDTO,
                             BindingResult bindingResult, @LoginMember SessionMember member, Model model) throws IOException {

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
        // 게시글 저장
        Long bid = boardService.saveBoard(boardSaveRequestDTO, member.getMid());

        // 이미지 서버 업로드
        if (boardSaveRequestDTO.getImageFiles().size() != 0) {
            List<UploadFile> uploadFiles = fileStore.storeFiles(boardSaveRequestDTO.getImageFiles(), bid);
            log.info("저장된 파일 : {}", uploadFiles);

            // 데이터베이스에 저장
            imgService.saveImg(uploadFiles);
        }

        return "redirect:/board/list";
    }

    @GetMapping("/detail")
    public void detail(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bid, Model model,
                       @LoginMember SessionMember member) {
        model.addAttribute("boardDTO", boardService.getBoard(bid));
        boardService.upViewCount(bid); // 게시판 글을 누르게 되면 조회수 증가

        if (member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("mid", member.getMid());
        }

        model.addAttribute("imageFiles", imgService.getImages(bid));
        model.addAttribute("replyDTO", replyService.getReplyList(bid));
    }

    @GetMapping("/update")
    public void update(Long bid, Model model, @LoginMember SessionMember member) {

        log.info("글을 업데이트 합니다");
        BoardListResponseDTO boardDTO = boardService.getBoard(bid);

        if (imgService.getImages(bid) != null) {
            model.addAttribute("imageFiles", imgService.getImages(bid));
        }

        if (member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("mid", member.getMid());
        }

        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/update")
    public String updateBoard(@Valid @ModelAttribute("boardDTO") BoardSaveRequestDTO boardDTO, BindingResult bindingResult,
                              @LoginMember SessionMember member, Model model) {
        log.info("업데이트를 시작합니다.");
        log.info("업데이트 할 글 : {}", boardDTO);

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

    // 게시글 본문에 이미지 보여주기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource getBoardImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    // 이미지 다운로드
    @GetMapping("/download/{storeFileName}/{uploadFileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String storeFileName, @PathVariable String uploadFileName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/popupImg/{bid}")
    public String popupImg(@PathVariable Long bid, Model model) {
        if (imgService.getImages(bid) != null) {
            model.addAttribute("imageFiles", imgService.getImages(bid));
        }

        model.addAttribute("bid", bid);

        return "board/popupImg";
    }

    @PostMapping("/popupImg/{bid}")
    public String addImg(@PathVariable Long bid, ImgSaveRequestDTO imgSaveRequestDTO, RedirectAttributes redirectAttributes) throws IOException {
        // 이미지 서버 업로드
        if (imgSaveRequestDTO.getAddImageFiles().size() != 0) {
            List<UploadFile> uploadFiles = fileStore.storeFiles(imgSaveRequestDTO.getAddImageFiles(), bid);
            log.info("저장된 파일 : {}", uploadFiles);

            // 데이터베이스에 저장
            imgService.saveImg(uploadFiles);
        }

        redirectAttributes.addAttribute("bid", bid);

        return "redirect:/board/popupImg/{bid}";
    }

    @DeleteMapping("/popupImg")
    public ResponseEntity<Long> deleteImg(@RequestBody ImgDeleteRequestDTO deleteRequestDTO) {

        imgService.deleteImgFile(deleteRequestDTO);

        return new ResponseEntity<>(deleteRequestDTO.getBid(), HttpStatus.OK);
    }


}
