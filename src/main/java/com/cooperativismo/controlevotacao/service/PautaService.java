package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.repository.PautaRepository;
import com.cooperativismo.controlevotacao.validations.OnInsertPauta;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class PautaService {
	
	private final PautaRepository pautaRepo;
	
	@Validated(OnInsertPauta.class)
	public Pauta insert(@NotNull(message = NOT_NULL, groups = OnInsertPauta.class) Pauta pauta) {
		log.info("Pauta insert - pauta: ({})", pauta.toJson());
		return this.pautaRepo.save(pauta);
	}

}
