package com.msd.api.controller;

import com.msd.api.dto.request.LoginRequest;
import com.msd.api.dto.response.LoginResponse;
import com.msd.api.exception.CustomException;
import com.msd.api.service.UserService;
import com.msd.api.util.CustomErrorCodes;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth Controller")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login api for PM service", response = LoginResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Content-Type", value = "application/json", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "Bearer Generated access token",
                    paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403,
                    message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest ledgerServiceLoginRequest) {
        if (logger.isDebugEnabled()) {
            logger.info("Login endpoint called");
        }
        authenticate(ledgerServiceLoginRequest.getUsername(), ledgerServiceLoginRequest.getPassword());
        return ResponseEntity.ok(userService.loginUser(ledgerServiceLoginRequest));
    }


    /**
     * Ledger User Authenticate
     * @param username
     * @param password
     */
    private void authenticate(String username, String password) {
        if (logger.isDebugEnabled()) {
            logger.info("Authenticate User {} {}", username , password);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new CustomException(CustomErrorCodes.USER_DISABLED);
        } catch (BadCredentialsException e) {
            throw new CustomException(CustomErrorCodes.INVALID_CREDENTIALS);

        }
    }
}
