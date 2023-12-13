package app.service.implemantation;

import app.dal.entity.Friend;
import app.dal.entity.User;
import app.dal.repository.FriendRepository;
import app.dal.repository.UserRepository;
import app.dto.FriendRequest;
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
    public Set<Friend> getFriendOfCurrentUser() {
        final User currentUser = userRepository.getCurrentUser();
        return new HashSet<>(friendRepository.getFriendByUserId(currentUser.getId()));
    }

    @Override
    public void saveFriend(FriendRequest friendRequest) {
        Optional<User> userByEmail = userRepository.findUserByEmail(friendRequest.getEmail());
        if (userByEmail.isPresent()) {
            User currentUser = userRepository.getCurrentUser();
            Friend friend = new Friend();
            friend.setUser(currentUser);
            friend.setFriend(userByEmail.get());
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("Friend " + friendRequest.getEmail() + " does not exist");
        }
    }
}
