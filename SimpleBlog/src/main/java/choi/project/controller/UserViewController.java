package choi.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("loginError", error);
        System.out.println(error);
        return "blogview/login"; // login.html
    }

    @GetMapping("/signup")
    public String signup() {
        return "blogview/signup";
    }
}
