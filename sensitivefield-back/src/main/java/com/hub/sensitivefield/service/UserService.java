package com.hub.sensitivefield.service;

import com.hub.sensitivefield.DTO.UserDTO;
import com.hub.sensitivefield.model.User;
import com.hub.sensitivefield.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean changeUserName(int id, String name) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isEmpty()) {
            return false;
        } else {
            User user = userOptional.get();
            user.setName(name);
            userRepository.save(user);
            return true;
        }
    }

    public boolean changeUserPasswordById(int id, String newpassword, String oldpassword) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isEmpty()) {
            System.out.println("user empty");
            return false;
        } else {
            User user = userOptional.get();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (!bCryptPasswordEncoder.matches(oldpassword, user.getPassword())) {
                System.out.println("matches false");
                return false;
            }
            user.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userRepository.save(user);
            return true;
        }
    }

    public boolean changeUserPasswordByLogin(String login, String newpassword, String oldpassword) {
        Optional<User> userOptional = getUserByLogin(login);
        if (userOptional.isEmpty()) {
            System.out.println("user empty");
            return false;
        } else {
            User user = userOptional.get();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (!bCryptPasswordEncoder.matches(oldpassword, user.getPassword())) {
                System.out.println("matches false");
                System.out.println("\"" + oldpassword + "\"");
                System.out.println("\"" + user.getPassword() + "\"");
                return false;
            }
            user.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userRepository.save(user);
            return true;
        }
    }

    public boolean removeUserById(int id) {
        if (getUserById(id).isEmpty()) return false;
        userRepository.deleteById(id);
        return true;
    }

    public User convertFromDTO(UserDTO userDTO) {
        return new User(userDTO.getName(),
                userDTO.getLogin(),
                userDTO.getPassword());
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getName(), user.getLogin(), user.getPassword());
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }
}
