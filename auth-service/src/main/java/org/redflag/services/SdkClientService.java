package org.redflag.services;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.SdkClientResponse;
import org.redflag.entities.SdkClient;
import org.redflag.repositories.SdkClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Singleton
@RequiredArgsConstructor
public class SdkClientService {

    private final SdkClientRepository repository;
    private final PasswordEncoder passwordEncoder;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Метод генерации случайного пароля
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    @Transactional
    public SdkClientResponse create(String login) {
        if (repository.findByLogin(login).isPresent()) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Login already taken");
        }

        String rawPassword = generateRandomPassword();
        SdkClient client = new SdkClient();
        client.setLogin(login);
        client.setPassword(passwordEncoder.encode(rawPassword));

        repository.save(client);
        // Возвращаем чистый пароль только сейчас!
        return new SdkClientResponse(client.getId(), client.getLogin(), rawPassword);
    }

    @Transactional
    public SdkClientResponse updateLogin(Long id, String newLogin) {
        SdkClient client = repository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "SDK Client not found"));

        if (!client.getLogin().equals(newLogin) && repository.findByLogin(newLogin).isPresent()) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Login already taken");
        }

        client.setLogin(newLogin);
        repository.update(client);
        return new SdkClientResponse(client.getId(), client.getLogin(), null);
    }

    @Transactional
    public SdkClientResponse regeneratePassword(Long id) {
        SdkClient client = repository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "SDK Client not found"));

        String newRawPassword = generateRandomPassword();
        client.setPassword(passwordEncoder.encode(newRawPassword));
        repository.update(client);

        return new SdkClientResponse(client.getId(), client.getLogin(), newRawPassword);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
