package org.redflag.dto;

import java.util.List;

public record UserSecurityDto(
        Long id,
        String login,
        List<String> roles) {
}
