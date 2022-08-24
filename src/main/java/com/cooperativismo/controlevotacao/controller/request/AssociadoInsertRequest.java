package com.cooperativismo.controlevotacao.controller.request;

import java.io.Serializable;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoInsertRequest implements Serializable {
	private static final long serialVersionUID = 3352162910401571098L;
	
	private String cpf;
	private String nome;

	public String toJson() {
		return new Gson().toJson(this);
	}

}
