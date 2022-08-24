package com.cooperativismo.controlevotacao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.controlevotacao.controller.request.AssociadoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Associado;
import com.cooperativismo.controlevotacao.service.AssociadoService;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/associados")
@Tag(name = "Associado-Controller")
public class AssociadoController {
	
	private final AssociadoService associadoServer;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@Operation(description = "Inclui um associado")
	private Associado insert(@RequestBody AssociadoInsertRequest associadoReq) {
		log.info("Post - /associados - RequestBody: ({})", associadoReq.toJson());
		Associado associado = new Gson().fromJson(associadoReq.toJson(), Associado.class);
		this.associadoServer.insert(associado);
		log.info("Response: ({})", associado.toJson());
		return associado;
	}

}
