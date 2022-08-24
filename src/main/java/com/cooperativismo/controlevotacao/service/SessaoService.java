package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.SP_TIME_ZONE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.controller.request.SessaoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Sessao;
import com.cooperativismo.controlevotacao.repository.SessaoRepository;
import com.cooperativismo.controlevotacao.validations.OnInsertSessao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class SessaoService {
	
	private static final Long MIN_DEFAULT = 1L; 
	
	private final SessaoRepository sessaoRepo;
	
	@Validated(OnInsertSessao.class)
	public Sessao buildAndInsert(@NotNull(message = NOT_NULL, groups = OnInsertSessao.class) SessaoInsertRequest sessaoReq) {
		log.info("Sessao buildAndInsert - sessaoReq: ({})", sessaoReq.toJson());
		Long duracaoMin = Optional.ofNullable(sessaoReq).map(SessaoInsertRequest::getDuracaoMin).orElse(MIN_DEFAULT);
		Sessao sessao = Sessao.builder().idPauta(sessaoReq.getIdPauta()).dhFim(LocalDateTime.now(ZoneId.of(SP_TIME_ZONE)).plusMinutes(duracaoMin)).build();
		return this.insert(sessao);
	}
	
	@Validated(OnInsertSessao.class)
	public Sessao insert(@NotNull(message = NOT_NULL, groups = OnInsertSessao.class) Sessao sessao) {
		log.info("Sessao insert - sessao: ({})", sessao.toJson());
		return this.sessaoRepo.save(sessao);
	}
	
	public Optional<Sessao> findOpenSession(@NotNull(message = NOT_NULL) Long idPauta) {
		log.info("Sessao findOpenSession - idPauta: ({})", idPauta);
		LocalDateTime agora = LocalDateTime.now(ZoneId.of(SP_TIME_ZONE));
		return this.sessaoRepo.findByIdPautaAndDhInicioAfterAndDhFimBefore(idPauta, agora, agora);
	}
	
	private void hasOpenSession(@NotNull(message = NOT_NULL) Long idPauta) {
		log.info("void hasOpenSession - idPauta: ({})", idPauta);
		
	}

}
