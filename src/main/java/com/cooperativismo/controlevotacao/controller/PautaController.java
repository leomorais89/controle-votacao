package com.cooperativismo.controlevotacao.controller;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.STRATEGY_GSON;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.controlevotacao.controller.request.PautaInsertRequest;
import com.cooperativismo.controlevotacao.controller.response.ResultadoVotacao;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.service.PautaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/pautas")
@Tag(name = "Pauta-Controller")
public class PautaController {
	
	private final PautaService pautaServer;
	private final Gson gson = new GsonBuilder().addSerializationExclusionStrategy(STRATEGY_GSON).create();
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(description = "Inclui uma pauta")
	private Pauta insert(@RequestBody PautaInsertRequest pautaReq) {
		log.info("Post - /pautas - RequestBody: ({})", pautaReq.toJson());
		Pauta pauta = new Gson().fromJson(pautaReq.toJson(), Pauta.class);
		this.pautaServer.insert(pauta);
		log.info("Response: ({})", pauta.toJson());
		return pauta;
	}
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(description = "Retorna uma page de pautas")
	private Page<Pauta> findAll(Pageable pageable) {
		log.info("Get - /pautas - pageable: ({})", pageable);
		Page<Pauta> pautaPage = this.pautaServer.findAll(pageable);
		log.info("Response: ({})", this.gson.toJson(pautaPage));
		return pautaPage;
	}
	
	@GetMapping("/openSessions")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(description = "Retorna uma lista de pautas com sessão aberta")
	private List<Pauta> findByWithOpenSession() {
		log.info("Get - /pautas/openSessions");
		List<Pauta> pautaList = this.pautaServer.findByWithOpenSession();
		log.info("Response: ({})", this.gson.toJson(pautaList));
		return pautaList;
	}
	
	@GetMapping("/calculateResult/finalize/{idPauta}")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(description = "Calcula o resultado da votação de uma pauta e finaliza a mesma")
	private ResultadoVotacao calculateResult(@PathVariable Long idPauta) throws BusinessException {
		log.info("Get - /pautas/calculateResult/finalize/{idPauta} - idPauta: ({})", idPauta);
		ResultadoVotacao resultado = this.pautaServer.calculateResult(idPauta);
		log.info("Response: ({})", resultado.toJson());
		return resultado;
	}

}
