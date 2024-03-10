package com.nabo.buyer.controller;

import com.nabo.buyer.exception.CreateEntityException;
import com.nabo.buyer.request.model.CreateUserRequest;
import com.nabo.buyer.response.model.UserCreatedResponse;
import com.nabo.buyer.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/")
@Slf4j
@Tag(name = "Users", description = "Use this API to register a new user, onboard them to the platform and manage their details.")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest, HttpServletRequest httpServletRequest) {
        try {
            createUserRequest.setEmail(createUserRequest.getEmail().toLowerCase());
            UserCreatedResponse userCreatedResponse = userService.createUser(createUserRequest);
            URI resourceURI = URI.create(httpServletRequest.getRequestURL().append("/").append(userCreatedResponse.getUser().getId()).toString());

            return ResponseEntity
                    .created(resourceURI)
                    .contentType(APPLICATION_JSON)
                    .body("{\"token\": \"" + userCreatedResponse.getJwtToken() + "\"," +
                            "\"message\": \"" + userCreatedResponse.getMessage() + "\"}");
        } catch (CreateEntityException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .contentType(APPLICATION_JSON)
                    .body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
