package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRepository;
import com.guiban.fluxOn.workSchedule.DayOfWeek;
import com.guiban.fluxOn.workSchedule.Turn;
import com.guiban.fluxOn.workSchedule.WorkSchedule;
import com.guiban.fluxOn.workSchedule.WorkScheduleRepository;
import com.guiban.fluxOn.workSchedule.dto.WorkScheduleBulkUpdateDTO;
import com.guiban.fluxOn.workSchedule.dto.WorkScheduleRegisterDTO;
import com.guiban.fluxOn.workSchedule.dto.WorkScheduleResponseDTO;
import com.guiban.fluxOn.workSchedule.dto.WorkScheduleUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("workSchedule")
@RestController
public class WorkScheduleController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @GetMapping("/workSchedules")
    public ResponseEntity<?> getAllWorkSchedules() {
        try {
            List<WorkScheduleResponseDTO> workScheduleList = workScheduleRepository.findAll()
                    .stream()
                    .map(workSchedule -> new WorkScheduleResponseDTO(workSchedule, workSchedule.getUser()))
                    .toList();
            return ResponseEntity.ok(workScheduleList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/createWorkSchedule")
    public ResponseEntity<?> createWorkSchedule(@RequestBody WorkScheduleRegisterDTO data) {
        User user = userRepository.findById(data.userId()).orElse(null);
        boolean exists = !workScheduleRepository.findByUserAndDayOfWeekAndTurn(
                user,
                DayOfWeek.valueOf(data.dayOfWeek()),
                Turn.valueOf(data.turn())
        ).isEmpty();

        if(exists) return ResponseEntity.status(409).body("Funcionário já está designado para este turno neste dia.");

        if(user == null) return ResponseEntity.status(404).body("Usuário não encontrado.");

        try {
            Time start = Time.valueOf(data.startTime() + ":00");
            Time end = Time.valueOf(data.endTime() + ":00");
            WorkSchedule workSchedule = new WorkSchedule(
                    user,
                    DayOfWeek.valueOf(data.dayOfWeek()),
                    Turn.valueOf(data.turn()),
                    start,
                    end
            );

            workScheduleRepository.save(workSchedule);
            return ResponseEntity.ok("Horário de trabalho criado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateWorkSchedule/{id}")
    public ResponseEntity<?> updateWorkSchedule(@PathVariable UUID id, @RequestBody WorkScheduleUpdateDTO data) {
        WorkSchedule workSchedule = workScheduleRepository.findById(id).orElse(null);

        if(workSchedule == null) return ResponseEntity.status(404).body("Horário de trabalho não encontrados.");

        try {
            if(data.dayOfWeek() != null) workSchedule.setDayOfWeek(DayOfWeek.valueOf(data.dayOfWeek()));
            if(data.turn() != null) workSchedule.setTurn(Turn.valueOf(data.turn()));
            if(data.startTime() != null) workSchedule.setStartTime(data.startTime());
            if(data.endTime() != null) workSchedule.setEndTime(data.endTime());

            return ResponseEntity.ok("Horário de trabalho atualizado com sucesso");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/bulkUpdateWorkSchedule")
    public ResponseEntity<?> bulkUpdateWorkSchedule(@RequestBody WorkScheduleBulkUpdateDTO data) {
        List<String> erros = new ArrayList<>();
        List<WorkSchedule> updateSchedules = new ArrayList<>();

        for(WorkScheduleUpdateDTO dto : data.schedules()) {
            WorkSchedule workSchedule = workScheduleRepository.findById(dto.id()).orElse(null);

            if(workSchedule == null) {
                erros.add(dto.dayOfWeek());
                continue;
            }

            try {
                if(dto.dayOfWeek() != null) workSchedule.setDayOfWeek(DayOfWeek.valueOf(dto.dayOfWeek()));
                if(dto.turn() != null) workSchedule.setTurn(Turn.valueOf(dto.turn()));
                if(dto.startTime() != null) workSchedule.setStartTime(dto.startTime());
                if(dto.endTime() != null) workSchedule.setEndTime(dto.endTime());

                updateSchedules.add(workSchedule);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(!erros.isEmpty()) {
            return ResponseEntity.status(404).body("Horário(s) de trabalho não encontrado(s): " + erros);
        }
        workScheduleRepository.saveAll(updateSchedules);
        return ResponseEntity.ok("Horário(s) de trabalho atualizado(s) com sucesso");
    }
}
