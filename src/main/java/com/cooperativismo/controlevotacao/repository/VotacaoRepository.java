package com.cooperativismo.controlevotacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativismo.controlevotacao.entity.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	boolean existsByPautaIdAndAssociadoId(Long idPauta, Long idAssociado);

}
