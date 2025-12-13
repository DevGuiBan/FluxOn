package com.guiban.fluxOn.domain.responsibility.dto;

import com.guiban.fluxOn.domain.responsibility.Responsibility;

import java.util.UUID;

public record ResponsibilityResponseDTO(UUID id, String name) {

    public ResponsibilityResponseDTO(Responsibility responsibility) {
        this(responsibility.getId(), responsibility.getName());
    }
}
