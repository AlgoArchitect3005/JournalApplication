package com.YashGPT.journalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.YashGPT.journalApp.Service.UserService;
import com.YashGPT.journalApp.Entity.User;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userServ;

    @PostMapping("/create-admin")
    public ResponseEntity <?> createAdmin(@RequestBody User user) {
        try {
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            userServ.createAdmin(user);
            return ResponseEntity.ok(auth.getName() + " as admin user created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating admin user: " + e.getMessage());
        }
    }
}
