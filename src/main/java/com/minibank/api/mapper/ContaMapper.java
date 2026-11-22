package com.minibank.api.mapper;

import com.minibank.api.domain.Conta;
import com.minibank.api.dto.ContaResponse;

public class ContaMapper {

   public  ContaResponse toResponse (Conta conta) {

       if (conta == null) {
           throw new IllegalArgumentException("A conta não pode ser nula");
       }

       return new ContaResponse(
               conta.getId(),
               conta.getTitular(),
               conta.getEmail(),
               conta.getSaldo(),
               conta.getCriadoEm()
       );
    }
}
