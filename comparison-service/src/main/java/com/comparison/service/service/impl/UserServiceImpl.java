package com.comparison.service.service.impl;

import com.comparison.service.model.User;
import com.comparison.service.model.UserDetailsCustom;
import com.comparison.service.repository.UserRepository;
import com.comparison.service.service.UserService;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserDetailsCustom(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new UserDetailsCustom(user);
    }

	@Override
	public User addUser(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
}
