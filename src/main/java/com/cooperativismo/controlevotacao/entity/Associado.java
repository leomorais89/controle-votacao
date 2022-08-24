package com.cooperativismo.controlevotacao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "tb_associado", schema = "public")
public class Associado implements Serializable {
	private static final long serialVersionUID = 938652229518297352L;
	
	@Id 
	@Column(name = "id_associado", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAssociadoId")
	@SequenceGenerator(name = "seqAssociadoId", sequenceName = "public.seq_associado_id", allocationSize = 1)
	private Long id;
	
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
