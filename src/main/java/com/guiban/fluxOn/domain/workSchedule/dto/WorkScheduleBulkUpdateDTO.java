package com.guiban.fluxOn.domain.workSchedule.dto;

import java.util.List;

public record WorkScheduleBulkUpdateDTO(
        List<WorkScheduleUpdateDTO> schedules
) {}
