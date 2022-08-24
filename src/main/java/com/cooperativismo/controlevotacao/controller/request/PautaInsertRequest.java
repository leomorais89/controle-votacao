package com.cooperativismo.controlevotacao.controller.request;

import java.io.Serializable;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaInsertRequest implements Serializable {
	private static final long serialVersionUID = -2843220084652203443L;
	
	private String nome;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
