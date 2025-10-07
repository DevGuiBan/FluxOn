package com.guiban.fluxOn.timeClock.dto;

import java.util.UUID;

public record TimeClockRegisterInDTO(UUID userId, String clockIn, String date, String attendanceStatus) {
}
