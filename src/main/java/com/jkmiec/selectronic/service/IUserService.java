package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.entity.User;

public interface IUserService {
    User findByUsername(String username);
    User save(User user);
}
