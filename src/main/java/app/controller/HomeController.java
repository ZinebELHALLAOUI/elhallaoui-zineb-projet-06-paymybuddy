package app.controller;

import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@Slf4j
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping
    public String getSold(Model model) {
        BigDecimal soldOfCurrentUser = userService.getSoldOfCurrentUser();
        model.addAttribute("sold", soldOfCurrentUser);
        return "home_page";
    }

}
