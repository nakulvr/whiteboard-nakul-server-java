package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserService {
    private List<User> users = new ArrayList<User>();
    @PostMapping("/api/user")
    public List<User> createUser(@RequestBody User user) {
        users.add(user);
        return users;
    }
    @PostMapping("/api/register")
    public User register(@RequestBody User user,
                         HttpSession session) {
        session.setAttribute("currentUser", user);
        users.add(user);
        return user;
    }
    @GetMapping("/api/profile")
    public User profile(HttpSession session) {
        User currentUser = (User)
                session.getAttribute("currentUser");
        return currentUser;
    }
    @PostMapping("/api/logout")
    public void logout
            (HttpSession session) {
        session.invalidate();
    }
    @PostMapping("/api/login")
    public User login(	@RequestBody User credentials,
                          HttpSession session) {
        for (User user : users) {
            if( user.getUsername().equals(credentials.getUsername())
                    && user.getPassword().equals(credentials.getPassword())) {
                session.setAttribute("currentUser", user);
                return user;
            }
        }
        return null;
    }
}
