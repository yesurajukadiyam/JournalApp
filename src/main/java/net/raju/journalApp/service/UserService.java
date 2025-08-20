package net.raju.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.raju.journalApp.entity.User;
import net.raju.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch(Exception e) {
            log.info("for information",e);
            log.error("for error purpose {} :",user.getUserName(),e);
            log.warn("for waring purpose",e);
            return false;
        }
    }

    public void saveAdmin(User user) {
        user.setPassword( passwordEncoder.encode(user.getPassword()) );
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }


    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }


    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}