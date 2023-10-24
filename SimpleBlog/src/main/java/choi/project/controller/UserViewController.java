package choi.project.controller;

import choi.project.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user, @RequestParam(required = false) String error, Model model) {
        if (user != null) {
            return "redirect:/articles";
        }

        model.addAttribute("loginError", error);
        return "blogview/login"; // login.html
    }

    @GetMapping("/signup")
    public String signup(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "redirect:/articles";
        }

        return "blogview/signup";
    }
}
