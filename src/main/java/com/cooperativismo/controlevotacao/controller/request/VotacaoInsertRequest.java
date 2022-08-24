package com.cooperativismo.controlevotacao.controller.request;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.*;
import com.cooperativismo.controlevotacao.validations.OnInsertVotacao;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotacaoInsertRequest implements Serializable {
	private static final long serialVersionUID = -3524172740044004278L;
	
	@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class)
	private Long idPauta;
	
	@NotBlank(message = NOT_NULL, groups = OnInsertVotacao.class)
	private String cpfAssociado;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class)
	private SimOuNaoEnum voto; 
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
