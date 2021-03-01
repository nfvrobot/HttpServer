package com.chernyavsky.service;

import com.chernyavsky.dao.UserDao;
import com.chernyavsky.dto.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    public static final UserService INSTANCE = new UserService();

    public User getByIdIn(Long id) {
        return UserDao.INSTANCE.getById(id)
                .map(it -> User.builder()
                        .balance(it.getBalance())
                        .rate(it.getRate())
                        .build())
                .orElse(null);
    }
}
