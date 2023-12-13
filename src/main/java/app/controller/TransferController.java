package app.controller;

import app.dal.entity.Friend;
import app.dal.entity.Transfer;
import app.dto.FriendDto;
import app.dto.FriendRequest;
import app.dto.TransferDto;
import app.dto.TransferRequest;
import app.service.FriendService;
import app.service.TransferService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/transfers")
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final FriendService friendService;
    private final UserService userService;

    @GetMapping
    public String getTransfers(Model model) {
        final List<Transfer> transfersOfCurrentUSer = transferService.getTransfersOfCurrentUSer();
        final List<TransferDto> transferDtos = transfersOfCurrentUSer.stream().map(transfer -> {
            final TransferDto transferDto = new TransferDto();
            transferDto.setMoney(transfer.getAmount());
            transferDto.setConnection(userService.getUserNameByAccountId(transfer.getAccountReceiverId()).orElse("Unknown connection"));
            transferDto.setDate(transfer.getInstant().toString());
            return transferDto;
        }).toList();
        model.addAttribute("transferRequest", new TransferRequest());
        model.addAttribute("transfers", transferDtos);
        final Set<Friend> friendOfCurrentUser = friendService.getFriendOfCurrentUser();
        List<FriendDto> friends = friendOfCurrentUser.stream().map(friend -> {
            FriendDto friendDto = new FriendDto();
            friendDto.setAccountId(friend.getFriend().getAccountId());
            friendDto.setName(friend.getFriend().getFirstName() + " " + friend.getFriend().getLastName());
            return friendDto;
        }).toList();
        model.addAttribute("friends", friends);
        model.addAttribute("friendRequest", new FriendRequest());
        return "transfers_page";
    }

    @PostMapping
    public String sendMoney(TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive transfer request : " + transferRequest);
        try {
            transferService.sendMoney(transferRequest);
        } catch (Exception e) {
            final List<String> errors = List.of(e.getMessage());
            redirectAttributes.addFlashAttribute("errors", errors);
        }
        return "redirect:/transfers";
    }
}
