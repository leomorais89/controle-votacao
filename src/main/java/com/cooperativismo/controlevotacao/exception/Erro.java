package com.cooperativismo.controlevotacao.exception;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Erro {
	
	private Integer codigo;
	private String erro;
	private String mensagem;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
