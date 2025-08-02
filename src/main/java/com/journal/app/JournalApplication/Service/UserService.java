package com.journal.app.JournalApplication.Service;

import com.journal.app.JournalApplication.Entity.User;
import com.journal.app.JournalApplication.Repository.UserRepository;
import com.journal.app.JournalApplication.Repository.UserRepositoryImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    public User getUserById(ObjectId id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }
    public void deleteUser(String userName){
        User user = userRepository.findByUsername(userName);
        if (user!=null){
            userRepository.delete(user);
        }
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user, String userName){
        User oldUser = userRepository.findByUsername(userName);
        if(oldUser!=null){
            oldUser.setUsername(user.getUsername()!=null &&!user.getUsername().equals("") ?user.getUsername():oldUser.getUsername());
            oldUser.setPassword(!user.getPassword().equals("") ?passwordEncoder.encode(user.getPassword()):oldUser.getPassword());
        }
        assert oldUser != null;
        return userRepository.save(oldUser);
    }

    public User findByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    public User createAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        return userRepository.save(user);
    }

    public List<User> getAllUsersForSA(){
        return userRepositoryImpl.getAllUsersForSA();
    }
}
