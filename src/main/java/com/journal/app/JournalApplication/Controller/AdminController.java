package com.journal.app.JournalApplication.Controller;

import com.journal.app.JournalApplication.Cache.ApiCache;
import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
@Tag(name="Admin-APIs", description = "This is Rest API for Admin operations of Journal Application")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiCache apiCache;
    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        if (userList!= null && !userList.isEmpty()){
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User adminUser = userService.createAdminUser(user);
        if (adminUser!=null){
            return new ResponseEntity<>(adminUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("clear-cache")
    public void clearCache(){
        apiCache.init();
    }
}
