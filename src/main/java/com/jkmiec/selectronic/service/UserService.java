package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.entity.User;
import com.jkmiec.selectronic.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }


}
