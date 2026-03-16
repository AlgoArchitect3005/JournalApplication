package com.YashGPT.journalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.YashGPT.journalApp.Service.UserService;
import com.YashGPT.journalApp.Entity.User;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
   
    
    @PostMapping("/create-user")
    public void createUser(
       // This method can be used to create a new user without authentication
         @RequestBody User user) {
      try {
         userService.saveNewUser(user);
         // return new ResponseEntity<>(HttpStatus.CREATED);
      } catch (Exception e) {
         // return ResponseEntity.badRequest().body(e.getMessage());
         e.printStackTrace();
      }
   }

}
