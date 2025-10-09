package com.guiban.fluxOn.workSchedule;

import com.guiban.fluxOn.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_schedule")
@Entity(name = "work_schedule")
public class WorkSchedule {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private Turn turn;

    private Time startTime;
    private Time endTime;

    public WorkSchedule(User user, DayOfWeek dayOfWeek, Turn turn, Time startTime, Time endTime) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
        this.turn = turn;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
