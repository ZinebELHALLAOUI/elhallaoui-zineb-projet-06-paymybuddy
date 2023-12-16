package app.controller;

import app.controller.dto.FriendDto;
import app.controller.dto.FriendRequest;
import app.controller.dto.TransferDto;
import app.controller.dto.TransferRequest;
import app.dal.entity.Friend;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.service.FriendService;
import app.service.TransferService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final UserInfo userInfo;

    @GetMapping
    public String getTransfers(Model model) {
        User user = userInfo.get();

        model.addAttribute("transfers", getTransferDtos(user));
        model.addAttribute("friends", getFriendDtos(user));

        model.addAttribute("transferRequest", new TransferRequest());
        model.addAttribute("friendRequest", new FriendRequest());
        return "transfers_page";
    }

    @PostMapping
    public String sendMoney(TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        log.info("Receive transfer request : " + transferRequest);
        try {
            transferService.sendMoney(transferRequest, userInfo.get());
            final List<String> infos = List.of("Successfully sending");
            redirectAttributes.addFlashAttribute("infos", infos);
        } catch (Exception e) {
            final List<String> errors = List.of(e.getMessage());
            redirectAttributes.addFlashAttribute("errors", errors);
        }

        return "redirect:/transfers";
    }

    private List<FriendDto> getFriendDtos(User user) {
        final Set<Friend> friendOfCurrentUser = friendService.getFriendByUser(user);
        return friendOfCurrentUser.stream().map(friend -> {
            FriendDto friendDto = new FriendDto();
            friendDto.setAccountId(friend.getFriend().getAccountId());
            friendDto.setName(friend.getFriend().getFirstName() + " " + friend.getFriend().getLastName());
            return friendDto;
        }).toList();
    }

    private List<TransferDto> getTransferDtos(User user) {
        final List<Transfer> transfers = transferService.getTransfersByUser(user);
        return transfers.stream().map(transfer -> {
            final TransferDto transferDto = new TransferDto();
            transferDto.setMoney(transfer.getAmount());
            transferDto.setConnection(userService.getUserNameByAccountId(transfer.getAccountReceiverId()).orElse("Unknown connection"));
            transferDto.setDate(transfer.getInstant().toString());
            return transferDto;
        }).toList();
    }
}
