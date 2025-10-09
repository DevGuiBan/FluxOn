package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.timeClock.AttendanceStatus;
import com.guiban.fluxOn.timeClock.TimeClock;
import com.guiban.fluxOn.timeClock.TimeClockRepository;
import com.guiban.fluxOn.timeClock.dto.TimeClockRegisterInDTO;
import com.guiban.fluxOn.timeClock.dto.TimeClockResponseDTO;
import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRepository;
import com.guiban.fluxOn.workSchedule.Turn;
import com.guiban.fluxOn.workSchedule.WorkSchedule;
import com.guiban.fluxOn.workSchedule.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("timeClock")
public class TimeClockController {

    @Autowired
    private TimeClockRepository timeClockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;


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

    private Turn getTurnByClock(Time clock) {
        int hour = clock.toLocalTime().getHour();
        if(hour < 12) return Turn.MANHA;
        if(hour < 18) return Turn.TARDE;
        return Turn.NOITE;
    }

    @PostMapping("/registerTimeClockIn")
    public ResponseEntity<?> registerTimeClockById (@RequestBody TimeClockRegisterInDTO data) {
        User user = userRepository.findById(data.userId()).orElse(null);
        List<WorkSchedule> workSchedules = workScheduleRepository.findByUserId(data.userId());

        try {

            LocalDate date = LocalDate.now();
            Time clock = Time.valueOf(LocalTime.now());
            clock.toLocalTime();
            AttendanceStatus attendanceStatus = AttendanceStatus.PRESENTE;

            Turn turn = getTurnByClock(clock);
            boolean isTurnValid = workSchedules.stream()
                    .anyMatch(workSchedule -> workSchedule.getTurn() == turn);

            TimeClock timeClockAlreadyRegistered = timeClockRepository.findByUserIdAndDateAndTurn(data.userId(), date, turn);

            TimeClock timeClock = new TimeClock(
                    user,
                    attendanceStatus,
                    turn,
                    date,
                    clock
            );

            if(user == null) return ResponseEntity.status(404).body("Usuário não encontrado");
            if(workSchedules.isEmpty()) return ResponseEntity.status(404).body("Usuário sem horário de trabalho registrado.");
            if(timeClockAlreadyRegistered != null) return ResponseEntity.status(409).body("Entrada já registrada");
            if(!isTurnValid) return ResponseEntity.status(409).body("Você está registrando fora do seu turno de trabalho.");

            timeClockRepository.save(timeClock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Horário registrado com sucesso");
    }
    
    @PutMapping("/registerTimeClockOut/{id}")
    public ResponseEntity<?> registerTimeClockOutById (@PathVariable UUID id) {
        try {
            Time clockOut = Time.valueOf(LocalTime.now());
            LocalDate date = LocalDate.now();
            TimeClock timeClock = timeClockRepository.findByUserIdAndDate(id, date);
            if(timeClock == null) return ResponseEntity.status(404).body("Nenhuma entrada registrada");

            timeClock.setClockOut(clockOut);
            timeClockRepository.save(timeClock);
            return ResponseEntity.ok("Saída Registrada com sucesso");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
