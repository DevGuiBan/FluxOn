package com.guiban.fluxOn.timeClock;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.workSchedule.Turn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "time_clock")
@Table(name = "time_clock")
@AllArgsConstructor
@NoArgsConstructor
public class TimeClock {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Enumerated(EnumType.STRING)
    private Turn turn;

    private LocalDate date;
    private Time clock;
    private Time clockOut;

    public TimeClock (User user, AttendanceStatus attendanceStatus, Turn turn, LocalDate date, Time clock) {
        this.user = user;
        this.attendanceStatus = attendanceStatus;
        this.turn = turn;
        this.date = date;
        this.clock = clock;
    }
}
