package com.cooperativismo.controlevotacao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.*;
import com.cooperativismo.controlevotacao.validations.OnInsertVotacao;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_votacao", schema = "public")
public class Votacao implements Serializable {
	private static final long serialVersionUID = -3524172740044004278L;
	
	@Id
	@Column(name = "id_votacao", nullable = false)
	@Null(message = NULL, groups = OnInsertVotacao.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqVotacaoId")
	@SequenceGenerator(name = "sqVotacaoId", sequenceName = "public.sq_votacao_id", allocationSize = 1)
	private Long id;
	
	@Valid
	@ManyToOne
	@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class)
	@JoinColumn(name = "id_pauta", referencedColumnName = "id_pauta", nullable = false)
	private Pauta pauta;
	
	@ManyToOne
	@Null(message = NULL, groups = OnInsertVotacao.class)
	@JoinColumn(name = "id_sessao", referencedColumnName = "id_sessao", nullable = false)
	private Sessao sessao;
	
	@Valid
	@ManyToOne
	@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class)
	@JoinColumn(name = "id_associado", referencedColumnName = "id_associado", nullable = false)
	private Associado associado;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "voto", nullable = false)
	@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class)
	private SimOuNaoEnum voto; 
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
