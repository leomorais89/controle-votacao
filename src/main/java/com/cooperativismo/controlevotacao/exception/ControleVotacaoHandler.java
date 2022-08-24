package com.cooperativismo.controlevotacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControleVotacaoHandler {
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public Erro negocioException(BusinessException e) {
		log.error(e.getMessage());
		return Erro.builder().codigo(HttpStatus.CONFLICT.value()).erro(HttpStatus.CONFLICT.getReasonPhrase()).mensagem(e.getMessage()).build();
	}

}
