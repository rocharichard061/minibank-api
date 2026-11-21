package com.minibank.api.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContaTest {
    @Test
    void deveCriarContaComSaldoInicialZero() {
        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");

        assertEquals(0, BigDecimal.ZERO.compareTo(conta.getSaldo()));

    }

    @Test
    void deveAumentarSaldoDepoisDepositar() {

        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");

        conta.depositar(new BigDecimal("150.00"));

        assertEquals(0, new BigDecimal("150.00").compareTo(conta.getSaldo()));
    }

    @Test
    void deveRejeitarDepositoNegativo() {

        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");
        conta.depositar(new BigDecimal("200.00"));

        assertThrows(IllegalArgumentException.class, () -> conta.depositar(new BigDecimal("-50.00")));

        assertEquals(0, new BigDecimal("200.00").compareTo(conta.getSaldo()));
    }

    @Test
    void deveRejeitarSaqueComSaldoInsuficiente () {

        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");

        conta.depositar(new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class, () -> conta.sacar(new BigDecimal("150.00")));

        assertEquals(0, new BigDecimal("100.00").compareTo(conta.getSaldo()) );


    }

    @Test
    void deveDiminuirSaldoAoSacar() {

        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");

        conta.depositar(new BigDecimal("500"));

        conta.sacar(new BigDecimal("150"));

        assertEquals(0, new BigDecimal("350").compareTo(conta.getSaldo()));

    }

    @Test
    void deveRejeitarDepositoComMaisDeDuasCasasDecimais () {

        Conta conta = new Conta("Richard Rocha", "testeteste@gmail.com");

        conta.depositar(new BigDecimal("100.00"));

       IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class,
               () -> conta.depositar(new BigDecimal("10.999")));

       assertEquals("O valor deve possuir no máximo duas casas decimais significativas"
               ,exception.getMessage());
       assertEquals(0, new BigDecimal("100").compareTo(conta.getSaldo()));

    }

    @Test
    void deveRejeitarDepositoNulo() {

        Conta conta = new Conta("Richard", "testeteste@gmail.com");

        conta.depositar(new BigDecimal("100.00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conta.depositar(null));
        assertEquals("O valor monetário deve ser maior que zero e não pode ser nulo", exception.getMessage());

        assertEquals(0, new BigDecimal("100.00").compareTo(conta.getSaldo()));
    }



    @Test
    void deveRejeitarSaqueDeValorZero() {

        Conta conta = new Conta("Richard", "teste@gmail.com");

        conta.depositar(new BigDecimal("200.00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conta.sacar(BigDecimal.ZERO));

        assertEquals("O valor monetário deve ser maior que zero e não pode ser nulo",exception.getMessage());

        assertEquals(0,new BigDecimal("200.00").compareTo(conta.getSaldo()) );
    }

    @Test
    void devePermitirSacarTodoSaldoDisponivel() {

        Conta conta = new Conta("Richard", "teste@gmail.com");

        conta.depositar(new BigDecimal("300.00"));

        conta.sacar(new BigDecimal("300.00"));

        assertEquals(0, BigDecimal.ZERO.compareTo(conta.getSaldo()));
    }

    @Test
    void deveRejeitarSaqueNegativo () {

        Conta conta = new Conta("Richard", "teste@gmail.com");

        conta.depositar(new BigDecimal("200.00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conta.sacar(new BigDecimal("-50")));

        assertEquals("O valor monetário deve ser maior que zero e não pode ser nulo", exception.getMessage());

        assertEquals(0, new BigDecimal("200.00").compareTo(conta.getSaldo()));
    }
}
