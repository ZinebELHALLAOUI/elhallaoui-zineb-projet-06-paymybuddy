package app.dal.entity;

import lombok.Data;

@Data
public class Friend {
    private Integer id;
    private User user;
    private User friend;
}
