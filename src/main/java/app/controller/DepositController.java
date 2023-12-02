package app.controller;

import app.dal.entity.Deposit;
import app.dto.DepositDto;
import app.dto.DepositRequest;
import app.dto.TransferRequest;
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

    @GetMapping
    public String getDeposits(Model model) {
        List<Deposit> depositsOfCurrentUSer = depositService.getDepositsOfCurrentUSer();
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
        depositService.deposeMoney(depositRequest);
        //TODO gestion des erreurs.
        return "redirect:/deposits";
    }
}
