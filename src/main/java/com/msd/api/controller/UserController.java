package com.msd.api.controller;

import com.msd.api.dto.request.RegisterRequest;
import com.msd.api.dto.response.LoginResponse;
import com.msd.api.dto.response.UserResponse;
import com.msd.api.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Create new user api for PM Tool", response = LoginResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {
        if (logger.isDebugEnabled()) {
            logger.info("Register endpoint called");
        }
        return ResponseEntity.ok(userService.createUser(registerRequest));
    }

    @GetMapping()
    @ApiOperation(value = "Get all users", response = UserResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<List<UserResponse>> getAll() {
        if (logger.isDebugEnabled()) {
            logger.info("Load all Users endpoint called");
        }
        return ResponseEntity.ok(userService.getAll());
    }
}
