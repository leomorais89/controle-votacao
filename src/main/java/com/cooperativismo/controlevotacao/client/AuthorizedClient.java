package com.cooperativismo.controlevotacao.client;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.client.response.AuthorizedVoteResponse;
import com.cooperativismo.controlevotacao.client.service.RestTemplateService;
import com.cooperativismo.controlevotacao.config.properties.ControleVotacaoProperties;
import com.cooperativismo.controlevotacao.enuns.AuthorizedVoteEnum;
import com.cooperativismo.controlevotacao.exception.IntegrationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class AuthorizedClient {
	
	private RestTemplateService restTemplateServer;
	private ControleVotacaoProperties properties;
	
	public boolean isEnableToVote(@NotBlank(message = NOT_NULL) String cpf) throws IntegrationException {
		log.info("boolean isEnableToVote - cpf: ({})", cpf);
		String url = this.properties.getAuthorizedVoteUrl();
		HttpMethod method = HttpMethod.GET;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		Map<String, String> params = new HashMap<>();
		params.put("cpf", cpf);
		
		ResponseEntity<AuthorizedVoteResponse> response = this.restTemplateServer.makeRestCall(url, method, entity, params, AuthorizedVoteResponse.class);
		log.info(response.getBody().toJson());
		return response.getBody().getStatus().equals(AuthorizedVoteEnum.ABLE_TO_VOTE.name());
	}

}
