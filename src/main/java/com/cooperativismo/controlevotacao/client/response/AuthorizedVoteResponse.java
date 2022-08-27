package com.cooperativismo.controlevotacao.client.response;

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
public class AuthorizedVoteResponse implements Serializable {
	private static final long serialVersionUID = -1017507463108755582L;

	private String status;
	
	public String toJson() {
		return new Gson().toJson(this);
	}

}
