package app.service.implemantation;

import app.dal.entity.User;

public interface UserService {

    boolean isUserExist(int userId);
    User getCurrentUser();


}
