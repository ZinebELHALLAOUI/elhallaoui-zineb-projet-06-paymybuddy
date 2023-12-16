package app.service.implemantation;

import app.controller.dto.FriendRequest;
import app.dal.entity.Friend;
import app.dal.entity.User;
import app.dal.repository.FriendRepository;
import app.dal.repository.UserRepository;
import app.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public Set<Friend> getFriendByUser(User user) {
        return new HashSet<>(friendRepository.getFriendByUserId(user.getId()));
    }

    @Override
    public void saveFriend(FriendRequest friendRequest, User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(friendRequest.getEmail());
        if (userByEmail.isPresent()) {
            Friend friend = new Friend();
            friend.setUser(user);
            friend.setFriend(userByEmail.get());
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("Friend " + friendRequest.getEmail() + " does not exist");
        }
    }
}
