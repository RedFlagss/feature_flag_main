package org.redflag.services;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.UiClientDto;
import org.redflag.dto.UpdateUiClientRequest;
import org.redflag.entities.Role;
import org.redflag.entities.UiClient;
import org.redflag.repositories.RoleRepository;
import org.redflag.repositories.UiClientRepository;
import org.redflag.services.mappers.UiClientToUiClientDtoMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class UiClientService {

    private final UiClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UiClientDto> getAllByDepartment(UUID uuidDepartament) {
        // 1. Проверка на null (защита от программных ошибок)
        if (uuidDepartament == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Department UUID cannot be null");
        }

        List<UiClient> clients = clientRepository.findByUuidDepartament(uuidDepartament);

        // 2. Проверка на пустой результат
        if (clients.isEmpty()) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND,
                    "No clients found for department: " + uuidDepartament);
        }

        return clients.stream()
                .map(UiClientToUiClientDtoMapper::mapToUiClientDto)
                .toList();
    }

    public UiClientDto getByLogin(String login) {
        return clientRepository.findByLogin(login)
                .map(UiClientToUiClientDtoMapper::mapToUiClientDto)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void updateUiClient(String login, UpdateUiClientRequest request) {
        UiClient client = clientRepository.findByLogin(login)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 1. Сначала всегда проверяем старый пароль
        if (!passwordEncoder.matches(request.oldPassword(), client.getPassword())) {
            throw new HttpStatusException(HttpStatus.FORBIDDEN, "Invalid old password");
        }

        // 2. Обновляем логин, если он пришел
        if (request.newLogin() != null && !request.newLogin().isBlank()) {
            // Проверяем, не совпадает ли новый логин с текущим
            if (!client.getLogin().equals(request.newLogin())) {
                // Проверяем уникальность в БД
                if (clientRepository.findByLogin(request.newLogin()).isPresent()) {
                    throw new HttpStatusException(HttpStatus.CONFLICT, "Login already taken");
                }
                client.setLogin(request.newLogin());
            }
        }

        // 3. Обновляем пароль, если он пришел
        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            client.setPassword(passwordEncoder.encode(request.newPassword()));
        }

        clientRepository.update(client);
    }

    @Transactional
    public void addRoles(Long userId, Set<String> roleNames) {
        UiClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Ищем только те роли, которых у пользователя еще нет
        Set<Role> rolesToAdd = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Role not found: " + name)))
                .collect(Collectors.toSet());

        client.getRoles().addAll(rolesToAdd);
        clientRepository.update(client);
    }

    @Transactional
    public void removeRoles(Long userId, Set<String> roleNames) {
        UiClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Удаляем из сета те роли, чьи имена совпали с пришедшими в запросе
        client.getRoles().removeIf(role -> roleNames.contains(role.getName()));

        clientRepository.update(client);
    }

    @Transactional
    public void updateDepartment(Long userId, UUID departmentUuid) {
        UiClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "User not found"));

        client.setUuidDepartament(departmentUuid);
        clientRepository.update(client);
    }
}