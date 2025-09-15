package com.guiban.fluxOn.user.userSpecs;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.responsibility.Responsibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "user_specs")
@Entity(name = "user_specs")
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserSpecs {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "responsibility_id", referencedColumnName = "id")
    private Responsibility responsibility;

    private String number;
    private String cpf;
    private String rg;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private PaymentMethodUser paymentMethod; // ex: pix, cash, Bank account
    private String paymentMethodDetails; // ex: pix key, card number
    private String bank; // ex: bank name
    private String agency; // ex: bank agency
    private String account; // ex: bank account number

}
