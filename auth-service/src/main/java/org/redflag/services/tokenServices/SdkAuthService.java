package org.redflag.services.tokenServices;

import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.repositories.SdkClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class SdkAuthService {

    private final SdkClientRepository sdkClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator tokenGenerator;

    public Optional<String> authenticate(UsernamePasswordCredentials credentials) {
        return sdkClientRepository.findByLogin(credentials.getUsername())
                .filter(client -> passwordEncoder.matches(credentials.getPassword(), client.getPassword()))
                .flatMap(client -> {
                    Map<String, Object> claims = Map.of(
                            "login", client.getLogin(),
                            "type", "sdk_client"
                    );
                    return tokenGenerator.generateToken(claims);
                });
    }
}