package com.hub.sensitivefield.controller;

import com.hub.sensitivefield.jwt.JwtProvider;
import com.hub.sensitivefield.messages.JwtResponse;
import com.hub.sensitivefield.messages.LoginForm;
import com.hub.sensitivefield.messages.ResponseMessage;
import com.hub.sensitivefield.messages.SignUpForm;
import com.hub.sensitivefield.model.Role;
import com.hub.sensitivefield.model.RoleName;
import com.hub.sensitivefield.model.User;
import com.hub.sensitivefield.service.RoleService;
import com.hub.sensitivefield.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("USER with usesname=" + loginRequest.getUsername() + " was successful login");
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.info("User login - bad data");
            return ResponseEntity.status(401).body("Wrong password or username");
        }


    }

    @PostMapping("/addUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest, @RequestParam String role) {
        if (userService.getUserByLogin(signUpRequest.getUsername()).isPresent()) {
            logger.info("Register new user was wrong, Username is already taken");
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = Collections.singleton(role);
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(tempRole -> {
            switch (tempRole) {
                case "superuser" -> {
                    Role suRole = roleService.findByName(RoleName.ROLE_SUPERUSER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(suRole);
                }
                case "admin" -> {
                    Role adRole = roleService.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adRole);
                }
                case "extendeduser" -> {
                    Role exuRole = roleService.findByName(RoleName.ROLE_EXTENDEDUSER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(exuRole);
                }
                case "user" -> {
                    Role userRole = roleService.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
                }
                default -> {
                    logger.info("Register new user was wrong, role empty or wasn't found");
                    throw new RuntimeException("Fail! -> Cause: just enter role with query param ?role, OR this role doesn't exits");
                }
            }
        });

        user.setRoles(roles);
        userService.addUser(user);
        logger.info("Register new user=" + user.getLogin() + " was successful");
        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}