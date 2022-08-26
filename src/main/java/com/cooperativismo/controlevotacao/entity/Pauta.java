package com.cooperativismo.controlevotacao.entity;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.STRATEGY_GSON;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.cooperativismo.controlevotacao.annotation.ExcludeGson;
import com.cooperativismo.controlevotacao.enuns.PautaStatusEnum;
import com.cooperativismo.controlevotacao.validations.OnFinalizePauta;
import com.cooperativismo.controlevotacao.validations.OnInsertPauta;
import com.cooperativismo.controlevotacao.validations.OnInsertSessao;
import com.cooperativismo.controlevotacao.validations.OnInsertVotacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pauta", schema = "public")
public class Pauta implements Serializable {
	private static final long serialVersionUID = -2843220084652203443L;
	
	@Id
	@Column(name = "id_pauta", nullable = false)
	@Null(message = NULL, groups = OnInsertPauta.class)
	@NotNull(message = NOT_NULL, groups = OnFinalizePauta.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqPautaId")
	@NotNull(message = NOT_NULL, groups = { OnInsertSessao.class, OnInsertVotacao.class })
	@SequenceGenerator(name = "sqPautaId", sequenceName = "public.seq_pauta_id", allocationSize = 1)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	@NotBlank(message = NOT_NULL, groups = { OnInsertPauta.class, OnFinalizePauta.class })
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	@Null(message = NULL, groups = OnInsertPauta.class)
	private PautaStatusEnum status;
	
	@JsonIgnore
	@ExcludeGson
	@OneToMany(mappedBy = "pauta", fetch = FetchType.LAZY)
	private List<Sessao> sessaoList;
	
	@JsonIgnore
	@ExcludeGson
	@OneToMany(mappedBy = "pauta", fetch = FetchType.LAZY)
	private List<Votacao> votoList;
	
	public String toJson() {
		Gson gson = new GsonBuilder().addSerializationExclusionStrategy(STRATEGY_GSON).create();
		return gson.toJson(this);
	}

}
