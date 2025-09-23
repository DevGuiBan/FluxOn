package com.guiban.fluxOn.timeClock;

import com.guiban.fluxOn.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
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

    private Date date;
    private Time clockIn;
    private Time clockOut;
    private String justification;

    public TimeClock (User user,AttendanceStatus attendanceStatus, Date date, Time clockIn, Time clockOut, String justification) {
        this.user = user;
        this.attendanceStatus = attendanceStatus;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.justification = justification;
    }
}
