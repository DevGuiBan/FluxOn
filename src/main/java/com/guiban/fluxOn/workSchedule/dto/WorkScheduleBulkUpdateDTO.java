package com.guiban.fluxOn.workSchedule.dto;

import java.util.List;

public record WorkScheduleBulkUpdateDTO(
        List<WorkScheduleUpdateDTO> schedules
) {}
