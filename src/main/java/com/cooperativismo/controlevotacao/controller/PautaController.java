package com.cooperativismo.controlevotacao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.controlevotacao.controller.request.PautaInsertRequest;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.service.PautaService;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

}
