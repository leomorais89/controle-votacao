package com.cooperativismo.controlevotacao.controller.request;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.cooperativismo.controlevotacao.validations.OnInsertSessao;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoInsertRequest implements Serializable {
	private static final long serialVersionUID = 9094032391410242897L;
	
	@NotNull(message = NOT_NULL, groups = OnInsertSessao.class)
	private Long idPauta;
	private Long duracaoMin;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
