package app.service;

import app.dal.entity.Friend;
import app.dto.FriendRequest;

import java.util.Set;

public interface FriendService {
    Set<Friend> getFriendOfCurrentUser();

    void saveFriend(FriendRequest friendRequest);
}
