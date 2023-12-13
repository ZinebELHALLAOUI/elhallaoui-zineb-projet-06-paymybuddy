package app.dal.repository;

import app.dal.entity.Friend;

import java.util.List;

public interface FriendRepository {
    Friend save(Friend friend);
    List<Friend> getFriendByUserId(int userId);
}
