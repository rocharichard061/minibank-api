package com.minibank.api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titular", nullable = false, length = 150)
    private String titular;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;


    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected Conta(){
    }



    public Conta(String titular, String email) {

        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException(
                    "O titular não pode ser vazio"
            );
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail não pode ser vazio"
            );
        }

        String titularNormalizado = titular.strip();

        String emailNormalizado = email.strip()
                .toLowerCase(Locale.ROOT);

        if (titularNormalizado.length() > 150) {
            throw new IllegalArgumentException(
                    "O titular não pode ultrapassar 150 caracteres"
            );
        }

        if (emailNormalizado.length() > 255) {
            throw new IllegalArgumentException(
                    "O e-mail não pode ultrapassar 255 caracteres"
            );
        }

        this.titular = titularNormalizado;
        this.email = emailNormalizado;
        this.saldo = BigDecimal.ZERO;
        this.criadoEm = Instant.now();
    }

    private BigDecimal validarValorMonetario(BigDecimal valor) {

        if(valor == null || valor.compareTo(BigDecimal.ZERO) <=0) {
            throw new IllegalArgumentException("O valor monetário deve ser maior que zero e não pode ser nulo");
        }


        try {
           return valor.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(
                    "O valor deve possuir no máximo duas casas decimais significativas",exception);
        }

    }

    //metódo de deposito
    public void depositar(BigDecimal valor) {

        BigDecimal valorValidado = validarValorMonetario(valor);


        BigDecimal novoSaldo = this.saldo.add(valorValidado);

        if (novoSaldo.precision() - novoSaldo.scale() > 17) {
            throw new IllegalArgumentException(
                    "O depósito ultrapassa o limite permitido para o saldo"
            );
        }

        this.saldo = novoSaldo;
    }


    //metódo de sacar

    public void sacar (BigDecimal valor ) {

        BigDecimal valorValidado = validarValorMonetario(valor);


        if(valorValidado.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }



        this.saldo = this.saldo.subtract(valorValidado);

    }

    public BigDecimal getSaldo() {
        return saldo;
    }
    public UUID getId () {
        return id;
    }
    public String getTitular() {
        return titular;
    }
    public String getEmail(){
        return email;
    }

    public Instant getCriadoEm(){
        return criadoEm;
    }

}
