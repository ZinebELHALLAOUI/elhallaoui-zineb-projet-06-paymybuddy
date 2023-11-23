package app.controller.transfer;

import app.controller.transfer.dto.TransferRequest;
import app.service.SendMoneyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/transfers")
@AllArgsConstructor
public class TransferController {

    private final SendMoneyService sendMoneyService;

    @GetMapping
    public String getTransfers(Model model) {
        model.addAttribute("transferRequest", new TransferRequest());
        return "transfers_page";
    }

    @PostMapping
    public String sendMoney(TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive transfer request : " + transferRequest);
        sendMoneyService.sendMoney(transferRequest);
        return "redirect:/transfers";
    }
}
