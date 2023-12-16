package app.controller;

import app.service.SoldCalculatorService;
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

    private final UserInfo userInfo;
    private final SoldCalculatorService soldCalculatorService;


    @GetMapping
    public String getSold(Model model) {
        BigDecimal soldOfCurrentUser = soldCalculatorService.calculate(userInfo.get().getAccount());
        model.addAttribute("sold", soldOfCurrentUser);
        return "home_page";
    }

}
