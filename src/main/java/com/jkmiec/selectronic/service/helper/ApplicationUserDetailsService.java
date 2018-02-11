package com.jkmiec.selectronic.service.helper;

import com.jkmiec.selectronic.entity.User;
import com.jkmiec.selectronic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findOneByUsername(username));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
