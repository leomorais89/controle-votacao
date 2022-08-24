package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.controller.response.ResultadoVotacao;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.repository.PautaRepository;
import com.cooperativismo.controlevotacao.validations.OnInsertPauta;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class PautaService {
	
	private final PautaRepository pautaRepo;
	private final SessaoService sessaoServer;
	private final VotacaoService votacaoServer;
	
	@Validated(OnInsertPauta.class)
	public Pauta insert(@NotNull(message = NOT_NULL, groups = OnInsertPauta.class) @Valid Pauta pauta) {
		log.info("Pauta insert - pauta: ({})", pauta.toJson());
		return this.pautaRepo.save(pauta);
	}
	
	public Pauta findBuId(@NotNull(message = NOT_NULL) Long idPauta) throws BusinessException {
		log.info("Pauta findById - idPauta: ({})", idPauta);
		return this.pautaRepo.findById(idPauta).orElseThrow(() -> new BusinessException("Não foi encontrado pauta com esse id"));
	}
	
	public ResultadoVotacao calculateResult(@NotNull(message = NOT_NULL) Long idPauta) throws BusinessException {
		log.info("ResultadoVotacao calculateResult - idPauta: ({})", idPauta);
		if (this.sessaoServer.findOpenSessionByIdPauta(idPauta).isPresent()) throw new BusinessException("Ainda existe uma sessão aberta, tente mais tarde");
		
		Pauta pauta = this.findBuId(idPauta);
		
		Map<Integer, SimOuNaoEnum> result = new HashMap<>();
//		long qtdSim = this.votacaoServer.countByPautaIdAndVoto(idPauta, SimOuNaoEnum.SIM);
		int qtdSim = pauta.getVotoList().stream().filter(voto -> voto.getVoto().equals(SimOuNaoEnum.SIM)).collect(Collectors.toList()).size();
//		long qtdNao = this.votacaoServer.countByPautaIdAndVoto(idPauta, SimOuNaoEnum.NAO);
		int qtdNao = pauta.getVotoList().stream().filter(voto -> voto.getVoto().equals(SimOuNaoEnum.NAO)).collect(Collectors.toList()).size();
		result.put(qtdSim, SimOuNaoEnum.SIM);
		result.put(qtdNao, SimOuNaoEnum.NAO);
		
		int max = Math.max(qtdSim, qtdNao);
		return ResultadoVotacao.builder().idPauta(idPauta).nomePauta(pauta.getNome()).qtdSim(qtdSim).qtdNao(qtdNao).resultado(result.get(max)).build();
	}

}
