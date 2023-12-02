package app.controller;

import app.dal.entity.Withdrawal;
import app.dto.WithdrawalDto;
import app.dto.WithdrawalRequest;
import app.service.WithdrawalService;
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
@RequestMapping("/withdrawals")
@AllArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @GetMapping
    public String getWithdrawals(Model model) {
        List<Withdrawal> withdrawalsOfCurrentUSer = withdrawalService.getWithdrawalsOfCurrentUSer();
        List<WithdrawalDto> withdrawalDtos = withdrawalsOfCurrentUSer.stream().map(withdrawal -> {
            final WithdrawalDto withdrawalDto = new WithdrawalDto();
            withdrawalDto.setWithdrawal(withdrawal.getAmount());
            withdrawalDto.setDate(withdrawal.getInstant().toString());
            return withdrawalDto;
        }).toList();

        model.addAttribute("withdrawalRequest", new WithdrawalRequest());
        model.addAttribute("withdrawals", withdrawalDtos);
        return "withdrawals_page";
    }

    @PostMapping
    public String deposeMoney(WithdrawalRequest withdrawalRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive withdrawal request : " + withdrawalRequest);
        withdrawalService.withdrawMoney(withdrawalRequest);
        //TODO gestion des erreurs.
        return "redirect:/withdrawals";
    }
}