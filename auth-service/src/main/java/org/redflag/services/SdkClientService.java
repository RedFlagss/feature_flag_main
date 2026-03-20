package org.redflag.services;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.SdkClientResponse;
import org.redflag.entities.SdkClient;
import org.redflag.exception.ConflictCustomException;
import org.redflag.exception.ResourceNotFoundCustomException;
import org.redflag.repositories.SdkClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor
public class SdkClientService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final SdkClientRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SdkClientResponse create(UUID login) {
        if (repository.findByLogin(login).isPresent()) {
            throw new ConflictCustomException("Login already taken");
        }

        String password = generateRandomPassword();
        SdkClient client = new SdkClient();
        client.setLogin(login);
        client.setPassword(passwordEncoder.encode(password));

        repository.save(client);

        return new SdkClientResponse(client.getId(), client.getLogin(), password);
    }

    @Transactional
    public SdkClientResponse updateLogin(Long id, UUID newLogin) {
        SdkClient client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCustomException("SDK Client not found"));

        if (!client.getLogin().equals(newLogin) && repository.findByLogin(newLogin).isPresent()) {
            throw new ConflictCustomException("Login already taken");
        }

        client.setLogin(newLogin);
        repository.update(client);
        return new SdkClientResponse(client.getId(), client.getLogin(), null);
    }

    @Transactional
    public SdkClientResponse regeneratePassword(Long id) {
        SdkClient client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCustomException("SDK Client not found"));

        String newPassword = generateRandomPassword();
        client.setPassword(passwordEncoder.encode(newPassword));
        repository.update(client);

        return new SdkClientResponse(client.getId(), client.getLogin(), newPassword);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
