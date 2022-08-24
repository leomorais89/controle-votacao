package com.cooperativismo.controlevotacao.controller.response;

import java.io.Serializable;

import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoVotacao implements Serializable {
	private static final long serialVersionUID = -4296526650206913341L;
	
	private Long idPauta;
	private String nomePauta;
	private Integer qtdSim;
	private Integer qtdNao;
	private SimOuNaoEnum resultado;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
