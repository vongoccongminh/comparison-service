package com.comparison.service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comparison.service.jwt.JwtTokenProvider;
import com.comparison.service.model.User;
import com.comparison.service.model.UserDetailsCustom;
import com.comparison.service.payload.user.LoginRequest;
import com.comparison.service.payload.user.LoginResponse;
import com.comparison.service.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public String registerUser(@Validated @RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        userService.addUser(user);
        return "success";
    }

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((UserDetailsCustom) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @GetMapping("/test/user")
    public List<User> getAllUser() {
    	return userService.getAllUser();
    }
}
