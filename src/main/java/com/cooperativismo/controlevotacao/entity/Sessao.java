package com.cooperativismo.controlevotacao.entity;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NULL;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.annotations.CreationTimestamp;

import com.cooperativismo.controlevotacao.util.LocalDateTimeAdapter;
import com.cooperativismo.controlevotacao.validations.OnInsertSessao;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_sessao", schema = "public")
public class Sessao implements Serializable {
	private static final long serialVersionUID = 9094032391410242897L;
	
	@Id
	@Column(name = "id_sessao", nullable = false)
	@Null(message = NULL, groups = OnInsertSessao.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqSessaoId")
	@SequenceGenerator(name = "sqSessaoId", sequenceName = "public.seq_sessao_id", allocationSize = 1)
	private Long id;
	
	@Valid
	@ManyToOne
	@NotNull(message = NOT_NULL, groups = OnInsertSessao.class)
	@JoinColumn(name = "id_pauta", referencedColumnName = "id_pauta", nullable = false)
	private Pauta pauta;

	@CreationTimestamp
	@Column(name = "dh_inicio", updatable = false)
	@JsonAdapter(value = LocalDateTimeAdapter.class)
	@Null(message = NULL, groups = OnInsertSessao.class)
	private LocalDateTime dhInicio;

	@JsonAdapter(value = LocalDateTimeAdapter.class)
	@NotNull(message = NOT_NULL, groups = OnInsertSessao.class)
	@Column(name = "dh_fim", nullable = false, updatable = false)
	private LocalDateTime dhFim;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
