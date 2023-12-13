package app.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    BigDecimal getSoldOfCurrentUser();
    Optional<String> getUserNameByAccountId(int accountId);
}
