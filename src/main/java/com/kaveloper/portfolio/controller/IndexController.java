package com.kaveloper.portfolio.controller;

import com.kaveloper.portfolio.config.auth.LoginMember;
import com.kaveloper.portfolio.config.auth.dto.SessionMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginMember SessionMember member) {
        if (member != null) {
            model.addAttribute("memberName", member.getName());
        }

        return "index";
    }

}
