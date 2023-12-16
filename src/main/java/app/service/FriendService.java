package app.service;

import app.controller.dto.FriendRequest;
import app.dal.entity.Friend;
import app.dal.entity.User;

import java.util.Set;

public interface FriendService {
    Set<Friend> getFriendByUser(User user);

    void saveFriend(FriendRequest friendRequest, User user);
}
