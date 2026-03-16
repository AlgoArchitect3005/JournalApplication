package com.YashGPT.journalApp.Controller;

import com.YashGPT.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.YashGPT.journalApp.Entity.User;
import com.YashGPT.journalApp.Repository.UserRepository;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
   private UserService userServ;
   @Autowired
   private UserRepository userRepo;

   // Add endpoint to get all users
   @GetMapping("/all")
   public ResponseEntity<List<User>> getAllUsers() {
      return ResponseEntity.ok(userServ.getAllUsers());
   }

   // Add endpoint to get user by username
   @GetMapping
   public ResponseEntity<User> getUserByUsername() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      User user = userServ.getUserByUsername(username);
      if (user != null) {
         return ResponseEntity.ok(user);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   // Add endpoint to update user information
   @PutMapping 
   public ResponseEntity<?> updateUser( @RequestBody User user){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
      User userInDb = userServ.getUserByUsername(username);   
      if(userInDb != null){
         userInDb.setUsername(user.getUsername());
         userInDb.setPassword(user.getPassword());
         userServ.saveNewUser(userInDb);
      }   
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }   

//
@DeleteMapping
public ResponseEntity<?> deleteUser(){
   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   String username = authentication.getName();
   userRepo.deleteByUsername(username);
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}
