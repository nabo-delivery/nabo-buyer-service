package com.nabo.buyer.config;

import com.nabo.buyer.repository.UserRepository;
import com.nabo.buyer.repository.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("userDetailsService")
@Slf4j
public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails userDetails = null;

        try {
            Optional<User> user = userRepository.findByEmail(email);
            user.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist with the email " + email));

            userDetails = user.map(SpringSecurityUserDetails::new).get();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userDetails;
    }
}

