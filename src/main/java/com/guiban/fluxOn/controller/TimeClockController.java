package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.timeClock.AttendanceStatus;
import com.guiban.fluxOn.timeClock.TimeClock;
import com.guiban.fluxOn.timeClock.TimeClockRepository;
import com.guiban.fluxOn.timeClock.dto.TimeClockRegisterInDTO;
import com.guiban.fluxOn.timeClock.dto.TimeClockResponseDTO;
import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("timeClock")
public class TimeClockController {

    @Autowired
    private TimeClockRepository timeClockRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/timeClocks")
    public ResponseEntity<?> getAllTimeClocks () {
        try {
            List<TimeClockResponseDTO> timeClockList = timeClockRepository.findAll().stream().map(TimeClockResponseDTO::new).toList();

            return ResponseEntity.ok(timeClockList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/timeClocksById/{id}")
    public ResponseEntity<?> getTimeClocksById (@PathVariable UUID id) {
        try {
            List<TimeClockResponseDTO> timeClockList = timeClockRepository.findByUserId(id).stream()
                    .map(TimeClockResponseDTO::new).toList();

            return  ResponseEntity.ok(timeClockList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/registerTimeClockIn")
    public ResponseEntity<?> registerTimeClockById (@RequestBody TimeClockRegisterInDTO data) {
        User user = userRepository.findById(data.userId()).orElse(null);

        if(user == null) return ResponseEntity.status(404).body("Usuário não encontrado");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(data.date());
            Time clockIn = Time.valueOf(data.clockIn());
            AttendanceStatus attendanceStatus = AttendanceStatus.PRESENTE;
            TimeClock timeClock = new TimeClock(
                    user,
                    attendanceStatus,
                    date,
                    clockIn
            );
            timeClockRepository.save(timeClock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Horário registrado com sucesso");
    }
}
