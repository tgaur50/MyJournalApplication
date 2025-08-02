package com.journal.app.JournalApplication.Controller;

import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Service.UserService;
import com.journal.app.JournalApplication.Service.WeatherService;
import com.journal.app.JournalApplication.api.responsepojo.WeatherResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User updatedUser = userService.updateUser(user, userName);
        if(updatedUser!=null){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> greetUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greet = "";
        if (weatherResponse!=null){
            greet += ", weather feels like " + weatherResponse.getCurrent().getFeelslike()
                    + " degree celcious!";
        }
        return new ResponseEntity<>("Hi " + userName + greet, HttpStatus.OK);
    }
}
