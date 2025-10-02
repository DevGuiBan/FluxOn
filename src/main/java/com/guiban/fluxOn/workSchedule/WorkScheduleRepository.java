package com.guiban.fluxOn.workSchedule;

import com.guiban.fluxOn.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, UUID> {
    WorkSchedule findByUserId(UUID userId);
    List<WorkSchedule> findByUserAndDayOfWeekAndTurn(User user, DayOfWeek dayOfWeek, Turn turn);
}
