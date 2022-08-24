package com.cooperativismo.controlevotacao.entity;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NULL;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import com.cooperativismo.controlevotacao.validations.OnInsertPauta;
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
@Table(name = "tb_pauta", schema = "public")
public class Pauta implements Serializable {
	private static final long serialVersionUID = -2843220084652203443L;
	
	@Id
	@Column(name = "id_pauta", nullable = false)
	@Null(message = NULL, groups = OnInsertPauta.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqPautaId")
	@SequenceGenerator(name = "sqPautaId", sequenceName = "public.sq_pauta_id", allocationSize = 1)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	@NotBlank(message = NOT_NULL, groups = OnInsertPauta.class)
	private String nome;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
