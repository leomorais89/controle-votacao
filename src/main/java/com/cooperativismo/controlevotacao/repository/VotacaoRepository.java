package com.cooperativismo.controlevotacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Votacao;
import com.cooperativismo.controlevotacao.enuns.SimOuNaoEnum;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	Long countByPautaIdAndVoto(Long idPauta, SimOuNaoEnum voto);

}
