package app.controller;

import app.dto.SignUpRequest;
import app.service.SignUpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sign-up")
@AllArgsConstructor
@Slf4j
public class SignUpController {

    private SignUpService signUpService;

    @GetMapping
    public String getSignUp(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup_page";
    }

    @PostMapping
    public String signup(@Validated SignUpRequest signUpRequest, RedirectAttributes redirectAttributes) {
        log.info("Signup request : " + signUpRequest);
        signUpService.signup(signUpRequest);
        return "redirect:/login";
    }
}
