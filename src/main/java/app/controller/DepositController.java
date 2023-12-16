package app.controller;

import app.dal.entity.Deposit;
import app.controller.dto.DepositDto;
import app.controller.dto.DepositRequest;
import app.service.DepositService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/deposits")
@AllArgsConstructor
public class DepositController {

    private final DepositService depositService;
    private final UserInfo userInfo;

    @GetMapping
    public String getDeposits(Model model) {
        List<Deposit> depositsOfCurrentUSer = depositService.getDepositsByUser(userInfo.get());
        List<DepositDto> depositDtos = depositsOfCurrentUSer.stream().map(deposit -> {
            final DepositDto depositDto = new DepositDto();
            depositDto.setDeposit(deposit.getAmount());
            depositDto.setDate(deposit.getInstant().toString());
            return depositDto;
        }).toList();

        model.addAttribute("depositRequest", new DepositRequest());
        model.addAttribute("deposits", depositDtos);
        return "deposits_page";
    }

    @PostMapping
    public String deposeMoney(DepositRequest depositRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive deposit request : " + depositRequest);

        try {
            depositService.deposeMoney(depositRequest, userInfo.get());
            final List<String> infos = List.of("Successfully deposed");
            redirectAttributes.addFlashAttribute("infos", infos);
        } catch (Exception e) {
            final List<String> errors = List.of(e.getMessage());
            redirectAttributes.addFlashAttribute("errors", errors);
        }
        return "redirect:/deposits";

    }
}
