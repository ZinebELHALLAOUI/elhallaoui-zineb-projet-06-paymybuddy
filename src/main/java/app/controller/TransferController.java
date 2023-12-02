package app.controller;

import app.dal.entity.Transfer;
import app.dto.TransferRequest;
import app.dto.TransferDto;
import app.service.TransferService;
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
@RequestMapping("/transfers")
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping
    public String getTransfers(Model model) {
        List<Transfer> transfersOfCurrentUSer = transferService.getTransfersOfCurrentUSer();
        List<TransferDto> transferDtos = transfersOfCurrentUSer.stream().map(transfer -> {
            final TransferDto transferDto = new TransferDto();
            transferDto.setMoney(transfer.getAmount());
            transferDto.setConnection(String.valueOf(transfer.getAccountReceiverId()));
            transferDto.setDate(transfer.getInstant().toString());
            return transferDto;
        }).toList();
        model.addAttribute("transferRequest", new TransferRequest());
        model.addAttribute("transfers", transferDtos);
        return "transfers_page";
    }

    @PostMapping
    public String sendMoney(TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive transfer request : " + transferRequest);
        transferService.sendMoney(transferRequest);
        //TODO gestion des erreurs.
        return "redirect:/transfers";
    }
}
