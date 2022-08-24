package com.cooperativismo.controlevotacao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.controlevotacao.controller.request.SessaoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Sessao;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.service.SessaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sessoes")
@Tag(name = "Sessão-Controller")
public class SessaoController {
	
	private final SessaoService sessaoServer;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(description = "Inclui uma sessão")
	private Sessao insert(@RequestBody SessaoInsertRequest sessaoReq) throws BusinessException {
		log.info("Post - /sessoes - RequestBody: ({})", sessaoReq.toJson());
		Sessao sessao = this.sessaoServer.buildAndInsert(sessaoReq);
		log.info("Response: ({})", sessao.toJson());
		return sessao;
	}

}
