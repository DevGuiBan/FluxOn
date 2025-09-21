package com.guiban.fluxOn.userSpecs;

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

    public UserSpecs (User user, Responsibility responsibility, String number, String cpf, String rg, BigDecimal salary, PaymentMethodUser paymentMethod, String paymentMethodDetails, String bank, String agency, String account) {
        this.user = user;
        this.responsibility = responsibility;
        this.number = number;
        this.cpf = cpf;
        this.rg = rg;
        this.salary = salary;
        this.paymentMethod = paymentMethod;
        this.paymentMethodDetails = paymentMethodDetails;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
    }

}
