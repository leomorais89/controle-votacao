package com.cooperativismo.controlevotacao.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends Exception {
	private static final long serialVersionUID = -7182468727991555321L;
	
	private Erro erro;
	
	public IntegrationException(Erro erro) {
		super(erro.getMensagem());
		this.erro = erro;
	}

}
