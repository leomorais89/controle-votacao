package com.cooperativismo.controlevotacao.service;

import static com.cooperativismo.controlevotacao.util.AbstractConstantes.NOT_NULL;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cooperativismo.controlevotacao.controller.request.VotacaoInsertRequest;
import com.cooperativismo.controlevotacao.entity.Associado;
import com.cooperativismo.controlevotacao.entity.Pauta;
import com.cooperativismo.controlevotacao.entity.Sessao;
import com.cooperativismo.controlevotacao.entity.Votacao;
import com.cooperativismo.controlevotacao.exception.BusinessException;
import com.cooperativismo.controlevotacao.repository.VotacaoRepository;
import com.cooperativismo.controlevotacao.validations.OnInsertVotacao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class VotacaoService {
	
	private final VotacaoRepository votacaoRepo;
	private final SessaoService sessaoServer;
	private final AssociadoService associadoServer;
	
	@Validated(OnInsertVotacao.class)
	public Votacao buildAndInsert(@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class) @Valid VotacaoInsertRequest votacaoReq) throws BusinessException {
		log.info("Votacao buildAndInsert - votacaoReq: ({})", votacaoReq.toJson());
		Pauta pauta = Pauta.builder().id(votacaoReq.getIdPauta()).build();
		Associado associado = Associado.builder().cpf(votacaoReq.getCpfAssociado()).build();
		Votacao votacao = Votacao.builder().pauta(pauta).associado(associado).voto(votacaoReq.getVoto()).build();
		return this.insert(votacao);
	}
	
	@Validated(OnInsertVotacao.class)
	public Votacao insert(@NotNull(message = NOT_NULL, groups = OnInsertVotacao.class) @Valid Votacao votacao) throws BusinessException {
		log.info("Votacao insert - votacao: ({})", votacao.toJson());
		Sessao sessao = this.sessaoServer.findOpenSessionByIdPauta(votacao.getPauta().getId()).orElseThrow(() -> new BusinessException("Não existe sessão aberta para essa pauta"));
		votacao.setSessao(sessao);
		
		Associado associado = this.associadoServer.findByCpf(votacao.getAssociado().getCpf());
		votacao.setAssociado(associado);
		
		this.validateUniqueVoto(votacao.getPauta().getId(), associado.getId());
		return this.votacaoRepo.save(votacao);
	}
	
	private void validateUniqueVoto(@NotNull(message = NOT_NULL) Long idPauta, @NotNull(message = NOT_NULL) Long idAssociado) throws BusinessException {
		log.info("void validateUniqueVoto - idPauta: ({}) - idAssociado: ({})", idPauta, idAssociado);
		if (this.votacaoRepo.existsByPautaIdAndAssociadoId(idPauta, idAssociado)) throw new BusinessException("Associado já tem voto realizado para essa pauta"); 
	}

}
