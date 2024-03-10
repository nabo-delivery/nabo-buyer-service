package com.nabo.buyer.service;

import com.nabo.buyer.exception.CreateEntityException;
import com.nabo.buyer.repository.UserRepository;
import com.nabo.buyer.repository.model.User;
import com.nabo.buyer.request.model.CreateUserRequest;
import com.nabo.buyer.response.model.UserCreatedResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    public void shouldThrowEmailAlreadyExistsException() throws CreateEntityException {
        when(userRepository.saveAndFlush(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("someone@email.com");
        Assertions.assertThrows(CreateEntityException.class, () -> userService.createUser(createUserRequest));
    }

    @Test
    public void shouldCreateAUser() {
        User user = new User();
        user.setEmail("someone@email.com");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("someone@email.com");
        UserCreatedResponse userCreatedResponse = userService.createUser(createUserRequest);

        Assertions.assertEquals("someone@email.com", userCreatedResponse.getUser().getEmail());
    }
}
