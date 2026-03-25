package com.YashGPT.journalApp.Service;

import java.util.Arrays;
// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YashGPT.journalApp.Entity.User;
import com.YashGPT.journalApp.Repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
@Autowired
private UserRepository userRepo ;
private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//Add service method to create a new user
   public void saveNewUser(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setRoles(Arrays.asList("ROLE_USER"));
      userRepo.save(user);
      
 }

  public void saveUser(User user) {
      userRepo.save(user);
      //   user.setJournals(new ArrayList<>()); // force set journals to empty list to avoid null pointer exceptions
  }

 //To get all users
 public List<User> getAllUsers() {
    return userRepo.findAll();
 }

 //To get user by id
public Optional<User> findById(ObjectId id){
   return userRepo.findById(id);
}

 //To get user by username
 public User getUserByUsername(String username) {
    return userRepo.findByUsername(username);
 }

 //To delete user
 public void deleteByUsername(String username){
   userRepo.deleteByUsername(username);
 }

//To create admin user
public void createAdmin(User user) {
   user.setPassword(passwordEncoder.encode(user.getPassword()));
   user.setRoles(Arrays.asList("ROLE_ADMIN"));
   userRepo.save(user);
} 
}