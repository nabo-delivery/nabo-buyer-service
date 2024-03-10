package com.nabo.buyer.service;

import com.nabo.buyer.config.SpringSecurityUserDetailsService;
import com.nabo.buyer.exception.CreateEntityException;
import com.nabo.buyer.repository.UserRepository;
import com.nabo.buyer.repository.model.User;
import com.nabo.buyer.request.model.CreateUserRequest;
import com.nabo.buyer.response.model.UserCreatedResponse;
import com.nabo.buyer.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public SpringSecurityUserDetailsService userDetailsService;
    public JwtUtil jwtUtil;

    public UserService(@Autowired UserRepository userRepository,
                       @Autowired PasswordEncoder passwordEncoder,
                       @Autowired SpringSecurityUserDetailsService userDetailsService,
                       @Autowired JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public UserCreatedResponse createUser(CreateUserRequest createUserRequest) throws CreateEntityException {
        User userToBeCreated = new User();
        userToBeCreated.setFirstname(createUserRequest.getFirstname());
        userToBeCreated.setLastname(createUserRequest.getLastname());
        userToBeCreated.setNickname(createUserRequest.getNickname());
        userToBeCreated.setPhone(createUserRequest.getPhone());
        userToBeCreated.setPostalCode(createUserRequest.getPostalCode());
        userToBeCreated.setEmail(createUserRequest.getEmail());
        userToBeCreated.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        userToBeCreated.setLatitude(createUserRequest.getLatitude());
        userToBeCreated.setLongitude(createUserRequest.getLongitude());
        userToBeCreated.setDeliveryAddress(createUserRequest.getDeliveryAddress());
        userToBeCreated.setNeighborhoodName(createUserRequest.getNeighborhoodName());
        userToBeCreated.setPlaceId(createUserRequest.getPlaceId());
        userToBeCreated.setTestUser(isTestUser(createUserRequest));

        return createUser(userToBeCreated);
    }

    private UserCreatedResponse createUser(User user) throws CreateEntityException {
        try {
            User newlyCreatedCustomer = userRepository.saveAndFlush(user);
            log.info("{" +
                    "  \"event.meta.name\": \"user_registered\"," +
                    "  \"event.meta.isTest\": " + user.isTestUser() + "," +
                    "  \"event.details.userId\": \""+ user.getId() +"\"," +
                    "  \"event.details.deliveryZip\": \"" + user.getPostalCode() + "\"," +
                    "  \"event.details.deliveryLocation\": \"" + user.getLatitude() + "," + user.getLongitude() + "\"," +
                    "  \"event.details.registration.neighborhoodName\": \"" + user.getNeighborhoodName() + "\"" +
                    "}");

            final UserDetails userDetails = userDetailsService.loadUserByUsername(newlyCreatedCustomer.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            UserCreatedResponse userCreatedResponse = new UserCreatedResponse();
            userCreatedResponse.setUser(newlyCreatedCustomer);
            userCreatedResponse.setMessage("User registered successfully");
            userCreatedResponse.setJwtToken(jwt);
            return userCreatedResponse;
        } catch (DataIntegrityViolationException e) {
            throw new CreateEntityException(e.getMostSpecificCause().getMessage());
        }
    }

    private boolean isTestUser(CreateUserRequest createUserRequest) {
        return createUserRequest.getEmail().startsWith("nabo.test") && createUserRequest.getEmail().endsWith("@nabodelivery.com");
    }
}
