package org.example.app.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerDtoRequest(
        Long id,
        String firstName,
        String lastName,
        String address,
        String phone) {
}
