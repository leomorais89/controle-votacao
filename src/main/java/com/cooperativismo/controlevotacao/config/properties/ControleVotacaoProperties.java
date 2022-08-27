package com.cooperativismo.controlevotacao.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ControleVotacaoProperties {
	
	@Value("${endpoint.authorized-vote}")
	private String authorizedVoteUrl;

}
