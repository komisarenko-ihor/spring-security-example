package com.example.springsecurityexample.security.google;

import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.UserRepository;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleCredentialRepository implements ICredentialRepository {

    private final UserRepository userRepository;

    @Override
    public String getSecretKey(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getGoogle2FaSecret();
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setGoogle2FaSecret(secretKey);
        user.setUseGoogle2Fa(true);
        userRepository.save(user);
    }
}
