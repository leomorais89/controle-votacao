package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.entity.Associado;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.repository.AssociadoRepository;
import com.cooperativismo.controlevotacao.validations.OnInsertAssociado;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class AssociadoService {
	
	private final AssociadoRepository associadoRepo;
	
	@Validated(OnInsertAssociado.class)
	public Associado insert(@NotNull(message = NOT_NULL, groups = OnInsertAssociado.class) @Valid Associado associado) {
		log.info("Associado insert - associado: ({})", associado.toJson());
		return this.associadoRepo.save(associado);
	}
	
	public Associado findByCpf(@NotBlank(message = NOT_NULL) String cpf) throws BusinessException {
		log.info("Associado findByCpf - cpf: ({})", cpf);
		return this.associadoRepo.findByCpf(cpf).orElseThrow(() -> new BusinessException("Não existe associado cadastrado com esse cpf"));
	}

}
