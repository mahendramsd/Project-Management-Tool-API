package com.msd.api.service;


import com.msd.api.config.SecurityConfig;
import com.msd.api.domain.CustomUserDetail;
import com.msd.api.domain.User;
import com.msd.api.domain.UserType;
import com.msd.api.dto.request.LoginRequest;
import com.msd.api.dto.request.RegisterRequest;
import com.msd.api.dto.response.LoginResponse;
import com.msd.api.dto.response.UserResponse;
import com.msd.api.exception.CustomException;
import com.msd.api.repositories.UserRepository;
import com.msd.api.repositories.UserTypeRepository;
import com.msd.api.util.CustomErrorCodes;
import com.msd.api.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final SecurityConfig securityConfig;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, SecurityConfig securityConfig) {

        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.securityConfig = securityConfig;
    }


    /**
     * Find User by username for security config
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    /**
     * Find CustomUserDetail by username
     *
     * @param username
     * @return
     */
    public CustomUserDetail findByCustomUserDetail(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetail(user);
    }


    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
                new CustomException(CustomErrorCodes.USER_NOT_FOUND));
        user.setAccessToken(securityConfig.generateToken(user.getUsername()));
        userRepository.save(user);
        return new LoginResponse(user);
    }

    /**
     * Create User
     *
     * @param registerRequest
     * @return
     */
    public LoginResponse createUser(RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByUsernameOrEmail(registerRequest.getUsername(),registerRequest.getEmail());
        if (!user.isPresent()) {
            User user1 = new User();
            user1.setUsername(registerRequest.getUsername());
            user1.setEmail(registerRequest.getEmail());
            user1.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user1.setAccessToken(securityConfig.generateToken(registerRequest.getUsername()));
            UserType userType = userTypeRepository.findById(registerRequest.getUserType()).orElseThrow(()
                    -> new CustomException(CustomErrorCodes.USER_TYPE_NOT_FOUND));
            user1.setUserType(userType);
            user1.setStatus(Status.ACTIVE);
            userRepository.save(user1);
            if(logger.isDebugEnabled()) {
                logger.debug("User Created {}", user1.getUsername());
            }
            return new LoginResponse(user1);
        } else {
            throw new CustomException(CustomErrorCodes.USER_ALREADY_EXIST);
        }
    }

    /**
     *
     * Load Users By status
     * @return
     */
    public List<UserResponse> getAll() {
        List<UserResponse> userResponses = userRepository.findByStatus(Status.ACTIVE).stream().map(UserResponse::new)
                .collect(Collectors.toList());
        return userResponses;
    }
}
