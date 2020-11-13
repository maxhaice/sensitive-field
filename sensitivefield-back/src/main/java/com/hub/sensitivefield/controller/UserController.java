package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.DTO.UserDTO;
import com.hub.sensitivefield.model.User;
import com.hub.sensitivefield.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    private ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Users was send");
        return ResponseEntity.ok(userService.getAllUsers()
                .stream().map(userService::convertToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/users/{id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            logger.info("User with id=" + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("User with id=" + id + " HAS BEEN SENDED");
            return ResponseEntity.ok(userService.convertToDTO(optionalUser.get()));
        }
    }

    @GetMapping("/api/users/login/{login}")
    private ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
        Optional<User> optionalUser = userService.getUserByLogin(login);
        if (optionalUser.isEmpty()) {
            logger.info("User with login=" + login + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("User with login=" + login + " HAS BEEN SENDED");
            return ResponseEntity.ok(userService.convertToDTO(optionalUser.get()));
        }
    }

    @DeleteMapping("/api/users/{id}")
    private ResponseEntity<Void> deleteUserByID(@PathVariable int id) {
        if (userService.removeUserById(id)) {
            logger.info("User with id=" + id + " HAS BEEN DELETED");
            return ResponseEntity.ok().build();
        } else {
            logger.info("User with id=" + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/api/users/{id}/changename")
    private ResponseEntity<Void> changeUserName(@RequestParam String name, @PathVariable int id) {
        if (userService.changeUserName(id, name)) {
            logger.info("User username with id=" + id + " WAS CHANGED TO " + name);
            return ResponseEntity.ok().build();
        } else {
            logger.info("User with id=" + id + " WASN'T FOUND");
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/api/users/id/{id}/changepassword")
    private ResponseEntity<Void> changeUserPassword(@RequestParam String newPassword, @RequestParam String oldPassword, @PathVariable int id) {
        if (userService.changeUserPasswordById(id, newPassword, oldPassword)) {
            logger.info("User password with id=" + id + " WAS CHANGED TO " + newPassword);
            return ResponseEntity.ok().build();
        } else {
            logger.info("User with id=" + id + " WASN'T FOUND OR OLDPASSWORD IS WRONG");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/users/login/{login}/changepassword")
    private ResponseEntity<Void> changeUserPassword(@RequestParam String newPassword, @RequestParam String oldPassword, @PathVariable String login) {
        if (userService.changeUserPasswordByLogin(login, newPassword, oldPassword)) {
            logger.info("User password with login=" + login + " WAS CHANGED TO " + newPassword);
            return ResponseEntity.ok().build();
        } else {
            logger.info("User with login=" + login + " WASN'T FOUND OR OLDPASSWORD IS WRONG");
            return ResponseEntity.badRequest().build();
        }
    }
}
