
package com.minibank.api.repository;

import com.minibank.api.domain.Conta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ContaRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private ContaRepository contaRepository;

    @Test
    void deveSalvarEBuscarContaPorEmail () {

        Conta conta = new Conta("Richard", "testContainers@gmail.com");

        Conta contaSalva = contaRepository.saveAndFlush(conta);

        Optional <Conta> resultado = contaRepository.findByEmailIgnoreCase("TESTCONTAINERS@GMAIL.COM");

        assertTrue(resultado.isPresent());

        assertEquals("Richard", resultado.orElseThrow().getTitular() );

        assertEquals(
                contaSalva.getId(),
                resultado.orElseThrow().getId()
        );

    }

    @Test
    void  deveIdentificarEmailJaCadastrado () {

        Conta conta = new Conta("teste", "teste1@gmail.com");

        contaRepository.saveAndFlush(conta);

        boolean resultado = contaRepository.existsByEmailIgnoreCase("teste1@gmail.com");

        assertTrue(resultado);


    }

    @Test
     void deveRetornarFalseQuandoEmailNaoExiste () {

        boolean resultado = contaRepository.existsByEmailIgnoreCase("1234@gmail.com");

        assertFalse(resultado);
    }

    @Test
    void deveRejeitarEmailDuplicado () {

        Conta conta = new Conta("Richad", "richard@gmail.com");

        contaRepository.saveAndFlush(conta);

        Conta conta2 = new Conta("Richard", "RICHARD@GMAIL.COM");

        assertThrows(DataIntegrityViolationException.class,
                () -> contaRepository.saveAndFlush(conta2));
    }

}
