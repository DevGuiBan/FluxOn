package com.guiban.fluxOn.responsibility;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "responsibilities")
@Table(name = "responsibilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    public Responsibility (String name) {
        this.name = name;
    }

}
