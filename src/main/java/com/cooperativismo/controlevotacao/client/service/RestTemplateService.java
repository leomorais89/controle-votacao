package com.cooperativismo.controlevotacao.client.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.cooperativismo.controlevotacao.exception.Erro;
import com.cooperativismo.controlevotacao.exception.IntegrationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class RestTemplateService {
	
	public static final String SERVICE_UNAVAILABLE_MSG = "Serviço indiponível - url: %s";
	
	private final RestTemplate restTemplate;
	
	public <T> ResponseEntity<T> makeRestCall(
			@NotBlank(message = NOT_NULL) String url, 
			@NotNull(message = NOT_NULL) HttpMethod method, 
			HttpEntity<?> entity, 
			Map<String, String> params, 
			Class<T> clazz
	) throws IntegrationException {
		try {
			if (CollectionUtils.isEmpty(params)) {
				log.info("ResponseEntity<T> makeRestCall - url: ({}) - method: ({}) - entity: ({}) - clazz: ({})", url, method, entity, clazz);
				return this.restTemplate.exchange(url, method, entity, clazz);
			} else {
				log.info("ResponseEntity<T> makeRestCall - url: ({}) - method: ({}) - entity: ({}) - params: ({}) - clazz: ({})", url, method, entity, params, clazz);
				return this.restTemplate.exchange(url, method, entity, clazz, params);
			}
		} catch (RestClientResponseException e) {
			throw new IntegrationException(Erro.builder().codigo(e.getRawStatusCode()).erro(e.getStatusText()).mensagem(e.getMessage()).build());
		} catch (ResourceAccessException e) {
			throw new IntegrationException(Erro
					.builder()
					.codigo(HttpStatus.SERVICE_UNAVAILABLE.value())
					.erro(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
					.mensagem(String.format(SERVICE_UNAVAILABLE_MSG, url))
					.build());
		}
	}

}
