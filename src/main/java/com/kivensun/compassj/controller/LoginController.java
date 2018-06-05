package com.kivensun.compassj.controller;

import com.kivensun.compassj.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public boolean loginUser(@RequestParam("username") String userName,
                             @RequestParam("pwd") String userPwd){
        User user = new User();
        user.setUserName(userName);
        user.setUserPwd(userPwd);
        return user.canLogin();
    }
}
