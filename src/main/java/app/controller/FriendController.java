package app.controller;

import app.controller.dto.FriendRequest;
import app.service.FriendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/friends")
@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final UserInfo userInfo;



    @PostMapping
    public String addFriend(FriendRequest friendRequest, RedirectAttributes redirectAttributes) {
        log.info("Friend request : " + friendRequest);
        try {
            friendService.saveFriend(friendRequest, userInfo.get());
            final List<String> infos = List.of("Successfully added");
            redirectAttributes.addFlashAttribute("infos", infos);
        } catch (Exception e) {
            final List<String> errors = List.of(e.getMessage());
            redirectAttributes.addFlashAttribute("errors", errors);
        }
        return "redirect:/transfers";
    }
}
