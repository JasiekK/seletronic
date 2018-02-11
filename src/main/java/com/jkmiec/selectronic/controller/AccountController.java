package com.jkmiec.selectronic.controller;

import com.jkmiec.selectronic.entity.Role;
import com.jkmiec.selectronic.entity.User;
import com.jkmiec.selectronic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public Principal user(Principal principal) {
        return principal;
    }

    //ONLY FOR TEST !!!
    @GetMapping(value = "/testuser")
    public User addTestUser() {
        return userService.save(
                new User("user", "user@user.pl", Arrays.asList(new Role("ROLE_USER"))));
    }

}
