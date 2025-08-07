package com.journal.app.JournalApplication.Controller;

import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Service.CustomUserDetailsService;
import com.journal.app.JournalApplication.Service.UserService;
import com.journal.app.JournalApplication.Utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/JournalApp")
@Slf4j
@Tag(name="Public-APIs", description = "This is Rest API for open source/public operations of Journal Application")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("health-check")
    public ResponseEntity<?> healthCheck(){
        try {
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<?> createSignUp(@RequestBody User user){
        User myUser = userService.createUser(user);
        if(myUser!=null){
            return new ResponseEntity<>(myUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
             Authentication authentication = authenticationManager
                     .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                             user.getPassword()));
             UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
             String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
             return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("sentimental-users")
    public ResponseEntity<?> getAllUsersForSA(){
        List<User> sentimentalUsers = userService.getAllUsersForSA();
        if (!sentimentalUsers.isEmpty()){
            return new ResponseEntity<>(sentimentalUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
