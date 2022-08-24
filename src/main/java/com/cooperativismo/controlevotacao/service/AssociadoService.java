package com.cooperativismo.controlevotacao.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.entity.Associado;
import com.cooperativismo.controlevotacao.repository.AssociadoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class AssociadoService {
	
	private final AssociadoRepository associadoRepo;
	
	public Associado insert(Associado associado) {
		log.info("Associado insert - associado: ({})", associado.toJson());
		return this.associadoRepo.save(associado);
	}

}
