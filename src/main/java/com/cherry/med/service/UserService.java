package com.cherry.med.service;

import com.cherry.med.domain.user.User;
import com.cherry.med.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public User register(User userEntity) throws IllegalArgumentException {
        if (repository.findByLogin(userEntity.getLogin()) != null) {
            throw new IllegalArgumentException("User login already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        User newUser = new User(userEntity.getLogin(), encryptedPassword, userEntity.getRole());

        return repository.save(newUser);
    }

    public UserDetails findByLogin(String login) {
        return repository.findByLogin(login);
    }
}