package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;
import static com.cooperativismo.controlevotacao.util.AbstractConstantes.SP_TIME_ZONE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.controller.response.ResultadoVotacao;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.enuns.PautaStatusEnum;
import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.repository.PautaRepository;
import com.cooperativismo.controlevotacao.validations.OnFinalizePauta;
import com.cooperativismo.controlevotacao.validations.OnInsertPauta;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class PautaService {
	
	private final PautaRepository pautaRepo;
	private final SessaoService sessaoServer;
	
	@Validated(OnInsertPauta.class)
	public Pauta insert(@NotNull(message = NOT_NULL, groups = OnInsertPauta.class) @Valid Pauta pauta) {
		log.info("Pauta insert - pauta: ({})", pauta.toJson());
		pauta.setStatus(PautaStatusEnum.ABERTA);
		return this.pautaRepo.save(pauta);
	}
	
	public Page<Pauta> findAll(Pageable pageable) {
		log.info("List<Pauta> findAll - pageable: ({})", pageable);
		return this.pautaRepo.findAll(pageable);
	}
	
	public List<Pauta> findByWithOpenSession() {
		LocalDateTime agora = LocalDateTime.now(ZoneId.of(SP_TIME_ZONE));
		return this.pautaRepo.findBySessaoListDhInicioAfterAndSessaoListDhFimBefore(agora);
	}
	
	public Pauta findBuId(@NotNull(message = NOT_NULL) Long idPauta) throws BusinessException {
		log.info("Pauta findById - idPauta: ({})", idPauta);
		return this.pautaRepo.findById(idPauta).orElseThrow(() -> new BusinessException("Não foi encontrado pauta com esse id"));
	}
	
	public boolean existsByIdAndStatus(@NotNull(message = NOT_NULL) Long idPauta, @NotNull(message = NOT_NULL) PautaStatusEnum status) {
		log.info("boolean existsByIdAndStatus - idPauta: ({}) - status: ({})", idPauta, status);
		return this.pautaRepo.existsByIdAndStatus(idPauta, status);
	}
	
	public ResultadoVotacao calculateResult(@NotNull(message = NOT_NULL) Long idPauta) throws BusinessException {
		log.info("ResultadoVotacao calculateResult - idPauta: ({})", idPauta);
		if (this.sessaoServer.findOpenSessionByIdPauta(idPauta).isPresent()) throw new BusinessException("Ainda existe uma sessão aberta, tente mais tarde");
		
		Pauta pauta = this.findBuId(idPauta);
		if (pauta.getStatus().equals(PautaStatusEnum.FINALIZADA)) throw new BusinessException("Essa pauta já se encontra finalizada");
		
		Map<Integer, SimOuNaoEnum> result = new HashMap<>();
		int qtdSim = pauta.getVotoList().stream().filter(voto -> voto.getVoto().equals(SimOuNaoEnum.SIM)).collect(Collectors.toList()).size();
		int qtdNao = pauta.getVotoList().stream().filter(voto -> voto.getVoto().equals(SimOuNaoEnum.NAO)).collect(Collectors.toList()).size();
		
		if (qtdSim == qtdNao) {
			throw new BusinessException("Essa pauta encontra-se com empate na votação, não pode ser finalizada no momento");
		} else {
			this.finalizePauta(pauta);
			result.put(qtdSim, SimOuNaoEnum.SIM);
			result.put(qtdNao, SimOuNaoEnum.NAO);
			
			int max = Math.max(qtdSim, qtdNao);
			return ResultadoVotacao.builder().idPauta(idPauta).nomePauta(pauta.getNome()).qtdSim(qtdSim).qtdNao(qtdNao).resultado(result.get(max)).build();
		}
	}
	
	@Validated(OnFinalizePauta.class)
	private Pauta finalizePauta(@NotNull(message = NOT_NULL) @Valid Pauta pauta) throws BusinessException {
		log.info("Pauta finalizePauta - pauta: ({})", pauta.toJson());
		pauta.setStatus(PautaStatusEnum.FINALIZADA);
		return this.pautaRepo.save(pauta);
	}

}
