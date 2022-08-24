package com.cooperativismo.controlevotacao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.controlevotacao.controller.request.VotacaoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Votacao;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.service.VotacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/votacoes")
@Tag(name = "Votacao-Controller")
public class VotacaoController {
	
	private final VotacaoService votacaoServer;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(description = "Realiza uma votação")
	private Votacao insert(@RequestBody VotacaoInsertRequest votacaoReq) throws BusinessException {
		log.info("Post - /votacoes - votacaoReq: ({})", votacaoReq.toJson());
		Votacao votacao = this.votacaoServer.buildAndInsert(votacaoReq);
		log.info("Response: ({})", votacao.toJson());
		return votacao;
	}

}
