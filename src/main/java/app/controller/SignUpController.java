package app.controller;

import app.controller.dto.SignUpRequest;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sign-up")
@AllArgsConstructor
@Slf4j
public class SignUpController {

    private UserService userService;

    @GetMapping
    public String getSignUp(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup_page";
    }

    @PostMapping
    public String signup(@Validated SignUpRequest signUpRequest, RedirectAttributes redirectAttributes, Model model) {
        log.info("Signup request : " + signUpRequest);
        try {
            userService.signup(signUpRequest);
            final List<String> infos = List.of("Successfully sign up, please sign in");
            redirectAttributes.addFlashAttribute("infos", infos);
        } catch (Exception e) {
            final List<String> errors = List.of(e.getMessage());
            model.addAttribute("errors", errors);
            return "signup_page";
        }
        return "redirect:/login";
    }
}
