package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.SP_TIME_ZONE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.controller.request.SessaoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.entity.Sessao;
import com.cooperativismo.controlevotacao.enuns.PautaStatusEnum;
import com.cooperativismo.controlevotacao.exception.BusinessException;
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
	private static final Long VL_INVALID = 0L;
	
	private final SessaoRepository sessaoRepo;
	private final PautaService pautaService;
	
	@Validated(OnInsertSessao.class)
	public Sessao buildAndInsert(@NotNull(message = NOT_NULL, groups = OnInsertSessao.class) @Valid SessaoInsertRequest sessaoReq) throws BusinessException {
		log.info("Sessao buildAndInsert - sessaoReq: ({})", sessaoReq.toJson());
		Long duracaoMin = Optional.ofNullable(sessaoReq).map(SessaoInsertRequest::getDuracaoMin).orElse(MIN_DEFAULT);
		Pauta pauta = Pauta.builder().id(sessaoReq.getIdPauta()).build();
		Sessao sessao = Sessao.builder().pauta(pauta).dhFim(LocalDateTime.now(ZoneId.of(SP_TIME_ZONE)).plusMinutes(duracaoMin.equals(VL_INVALID) ? MIN_DEFAULT : duracaoMin)).build();
		return this.insert(sessao);
	}
	
	@Validated(OnInsertSessao.class)
	public Sessao insert(@NotNull(message = NOT_NULL, groups = OnInsertSessao.class) @Valid Sessao sessao) throws BusinessException {
		log.info("Sessao insert - sessao: ({})", sessao.toJson());
		if (this.pautaService.existsByIdAndStatus(sessao.getPauta().getId(), PautaStatusEnum.ABERTA)) {
			this.hasOpenSession(sessao.getPauta().getId());
			return this.sessaoRepo.save(sessao);
		}
		throw new BusinessException("Não existe uma pauta com esse id no status ABERTA");
	}
	
	public Optional<Sessao> findOpenSessionByIdPauta(@NotNull(message = NOT_NULL) Long idPauta) {
		log.info("Sessao findOpenSessionByIdPauta - idPauta: ({})", idPauta);
		LocalDateTime agora = LocalDateTime.now(ZoneId.of(SP_TIME_ZONE));
		return this.sessaoRepo.findByPautaIdAndDhInicioAfterAndDhFimBefore(idPauta, agora);
	}
	
	private void hasOpenSession(@NotNull(message = NOT_NULL) Long idPauta) throws BusinessException {
		log.info("void hasOpenSession - idPauta: ({})", idPauta);
		if (this.findOpenSessionByIdPauta(idPauta).isPresent()) throw new BusinessException("Já existe uma sessão aberta para essa Pauta");
	}

}
